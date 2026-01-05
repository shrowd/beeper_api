package shrowd.beeper.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import shrowd.beeper.dto.response.PostResponse;
import shrowd.beeper.entity.Post;
import shrowd.beeper.entity.User;
import shrowd.beeper.enums.Role;
import shrowd.beeper.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(String title, String content, User author) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(Long id, String title, String content, User currentUser) {
        Post post = getPost(id);

        if (!post.getAuthor().getId().equals(currentUser.getId())
                && currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Not allowed");
        }

        post.setTitle(title);
        post.setContent(content);

        return postRepository.save(post);
    }


    public void deletePost(Long id, User currentUser) {
        Post post = getPost(id);

        if (!post.getAuthor().getId().equals(currentUser.getId())
                && currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Not allowed");
        }

        postRepository.delete(post);
    }

    public PostResponse toResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getEmail(),
                post.getCreatedAt()
        );
    }

}
