package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.form.validator.UserCredentialsRegisterValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController extends ApiController {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserCredentialsRegisterValidator userCredentialsRegisterValidator;

    public UserController(UserService userService, JwtService jwtService, UserCredentialsRegisterValidator userCredentialsRegisterValidator) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userCredentialsRegisterValidator = userCredentialsRegisterValidator;
    }

    @InitBinder("userRegisterCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCredentialsRegisterValidator);
    }

    @GetMapping("users/authorized")
    public User findAuthorized(User user) {
        return user;
    }

    @GetMapping("users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("users")
    public String register(@RequestBody @Valid UserRegisterCredentials userCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        userService.register(userCredentials);

        return jwtService.create(userCredentials.getLogin(), userCredentials.getPassword());
    }
}