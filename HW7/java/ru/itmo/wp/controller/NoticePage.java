package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String createGet(Model model, HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            putMessage(httpSession, "Only logged users can create notices");
            return "redirect:/";
        }
        model.addAttribute("noticeForm", new Notice());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String createPost(@Valid @ModelAttribute("noticeForm") Notice notice,
                             BindingResult bindingResult,
                             HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            putMessage(httpSession, "Only logged users can create notices");
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        noticeService.save(notice);
        putMessage(httpSession, "You added a new notice!");

        return "redirect:/";
    }
}
