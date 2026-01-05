package shrowd.beeper.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import shrowd.beeper.entity.Comment;
import shrowd.beeper.entity.Post;
import shrowd.beeper.entity.User;
import shrowd.beeper.enums.Role;
import shrowd.beeper.exception.ResourceNotFoundException;
import shrowd.beeper.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    public Comment addComment(Long postId, String content, User author) {
        Post post = postService.getPost(postId);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Long postId) {
        postService.getPost(postId);
        return commentRepository.findByPostId(postId);
    }

    public Comment updateComment(Long commentId, String content, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getAuthor().getId().equals(currentUser.getId())
                && currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Not allowed");
        }

        comment.setContent(content);
        return commentRepository.save(comment);
    }


    public void deleteComment(Long commentId, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if (!comment.getAuthor().getId().equals(currentUser.getId())
                && currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Not allowed");
        }

        commentRepository.delete(comment);
    }
}
