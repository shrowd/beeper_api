package shrowd.beeper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shrowd.beeper.dto.request.CreateCommentRequest;
import shrowd.beeper.dto.request.UpdateCommentRequest;
import shrowd.beeper.dto.response.CommentResponse;
import shrowd.beeper.entity.User;
import shrowd.beeper.mapper.CommentMapper;
import shrowd.beeper.service.CommentService;
import shrowd.beeper.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getComments(postId)
                .stream()
                .map(commentMapper::mapToResponse)
                .toList();

        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable Long postId, @Valid @RequestBody CreateCommentRequest request) {
        User currentUser = userService.getCurrentUser();

        commentService.addComment(
                postId,
                request.content(),
                currentUser
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @Valid @RequestBody UpdateCommentRequest request) {
        User currentUser = userService.getCurrentUser();

        commentService.updateComment(
                id,
                request.content(),
                currentUser
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();

        commentService.deleteComment(id, currentUser);

        return ResponseEntity.noContent().build();
    }
}


