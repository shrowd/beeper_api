package shrowd.beeper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shrowd.beeper.dto.request.CreateCommentRequest;
import shrowd.beeper.dto.request.UpdateCommentRequest;
import shrowd.beeper.entity.Comment;
import shrowd.beeper.entity.User;
import shrowd.beeper.service.CommentService;
import shrowd.beeper.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @GetMapping
    public List<Comment> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PostMapping
    public Comment addComment(@PathVariable Long postId,
                              @RequestBody @Valid CreateCommentRequest request) {

        User currentUser = userService.getCurrentUser();
        return commentService.addComment(
                postId,
                request.content(),
                currentUser
        );
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id,
                                 @RequestBody @Valid UpdateCommentRequest request) {

        User currentUser = userService.getCurrentUser();
        return commentService.updateComment(id, request.content(), currentUser);
    }


    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        commentService.deleteComment(id, currentUser);
    }
}

