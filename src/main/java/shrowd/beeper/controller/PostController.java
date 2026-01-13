package shrowd.beeper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shrowd.beeper.dto.request.CreatePostRequest;
import shrowd.beeper.dto.request.UpdatePostRequest;
import shrowd.beeper.dto.response.PostResponse;
import shrowd.beeper.entity.User;
import shrowd.beeper.mapper.PostMapper;
import shrowd.beeper.service.PostService;
import shrowd.beeper.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts() {
        List<PostResponse> posts = postService.getAllPosts()
                .stream()
                .map(postMapper::mapToResponse)
                .toList();

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse post = postMapper.mapToResponse(
                postService.getPost(id)
        );

        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostRequest request) {
        User currentUser = userService.getCurrentUser();

        postService.createPost(
                request.title(),
                request.content(),
                currentUser
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @Valid @RequestBody UpdatePostRequest request) {
        User currentUser = userService.getCurrentUser();

        postService.updatePost(
                id,
                request.title(),
                request.content(),
                currentUser
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();

        postService.deletePost(id, currentUser);

        return ResponseEntity.noContent().build();
    }
}



