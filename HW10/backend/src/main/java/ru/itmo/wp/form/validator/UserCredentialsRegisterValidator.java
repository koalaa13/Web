package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.service.UserService;

@Component
public class UserCredentialsRegisterValidator implements Validator {
    final private UserService userService;

    public UserCredentialsRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterCredentials.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            UserRegisterCredentials userRegisterCredentials = (UserRegisterCredentials) target;
            if (!userService.isLoginVacant(userRegisterCredentials.getLogin())) {
                errors.reject("login.invalid-login", "Login is already in use.");
            }
        }
    }
}
