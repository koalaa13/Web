package ru.itmo.wp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.validator.NoticeCredentialsCreateValidator;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;
    private final NoticeCredentialsCreateValidator noticeCredentialsCreateValidator;

    public NoticePage(NoticeService noticeService, NoticeCredentialsCreateValidator noticeCredentialsCreateValidator) {
        this.noticeService = noticeService;
        this.noticeCredentialsCreateValidator = noticeCredentialsCreateValidator;
    }

    @InitBinder("notice")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(noticeCredentialsCreateValidator);
    }

    @GetMapping("/notice")
    public String createGet(Model model, HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            putMessage(httpSession, "Only logged users can create notices");
            return "redirect:/";
        }
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String createPost(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeCredentials,
                             BindingResult bindingResult,
                             HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        noticeService.save(noticeCredentials);
        putMessage(httpSession, "You added a new notice!");

        return "redirect:/";
    }
}
