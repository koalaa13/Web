package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/{postId}")
    public String postGet(@PathVariable("postId") String postId, Model model) {
        try {
            long id = Long.parseLong(postId);
            model.addAttribute("post", postService.findById(id));
            model.addAttribute("comment", new Comment());
        } catch (NumberFormatException ignored) {
            // No operations.
        }
        return "PostPage";
    }

    @PostMapping("/post/{postId}")
    public String postPost(@PathVariable("postId") String postId,
                           @Valid @ModelAttribute("comment") Comment comment,
                           BindingResult bindingResult,
                           Model model,
                           HttpSession httpSession) {
        User user = getUser(httpSession);
        if (user == null) {
            return "redirect:/enter";
        }
        try {
            long id = Long.parseLong(postId);
            Post post = postService.findById(id);
            if (bindingResult.hasErrors()) {
                model.addAttribute("post", post);
                return "PostPage";
            }
            postService.writeComment(user, post, comment);
        } catch (NumberFormatException ignored) {
            // No operations.
        }
        return "redirect:/post/{postId}";
    }
}
