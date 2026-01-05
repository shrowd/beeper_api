package shrowd.beeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shrowd.beeper.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
