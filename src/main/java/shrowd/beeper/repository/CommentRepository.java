package shrowd.beeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shrowd.beeper.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);
}

