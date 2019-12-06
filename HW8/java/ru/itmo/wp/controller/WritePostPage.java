package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.validator.TagValidator;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;
    private final TagValidator tagValidator;

    public WritePostPage(UserService userService, TagValidator tagValidator) {
        this.userService = userService;
        this.tagValidator = tagValidator;
    }

    @InitBinder("tags")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(tagValidator);
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("inputTags", "");
        return "WritePostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("inputTags") String inputTags,
                                BindingResult tagsBindingResult,
                                @Valid @ModelAttribute("post") Post post,
                                BindingResult postBindingResult,
                                HttpSession httpSession) {
        if (postBindingResult.hasErrors() || tagsBindingResult.hasErrors()) {
            return "WritePostPage";
        }

        userService.writePost(getUser(httpSession), post, inputTags);
        putMessage(httpSession, "You published new post");

        return "redirect:/posts";
    }
}
