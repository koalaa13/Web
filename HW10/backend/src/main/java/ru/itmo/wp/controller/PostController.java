package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.NoSuchResourceException;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/1")
public class PostController extends ApiController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @PostMapping("posts")
    public void create(@RequestBody @Valid Post post, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        User loggedUser = getUser(httpServletRequest);
        if (loggedUser == null) {
            throw new RuntimeException("Permission denied");
        }
        postService.write(post);
    }

    @PostMapping("posts/addComment/{postId}")
    public void addComment(@PathVariable("postId") String postId,
                           @RequestBody @Valid Comment comment,
                           BindingResult bindingResult,
                           HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        User loggedUser = getUser(httpServletRequest);
        if (loggedUser == null) {
            throw new RuntimeException("Permission denied");
        }
        try {
            long id = Long.parseLong(postId);
            Optional<Post> post = postService.findById(id);
            if (post.isPresent()) {
                comment.setPost(post.get());
                commentService.add(comment);
            } else {
                throw new NoSuchResourceException("Can't add comment for nonexistent post");
            }
        } catch (NumberFormatException e) {
            throw new NoSuchResourceException("Can't add comment for nonexistent post");
        }
    }
}
