package shrowd.beeper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shrowd.beeper.dto.request.CreatePostRequest;
import shrowd.beeper.dto.request.UpdatePostRequest;
import shrowd.beeper.dto.response.PostResponse;
import shrowd.beeper.entity.Post;
import shrowd.beeper.entity.User;
import shrowd.beeper.service.PostService;
import shrowd.beeper.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.toResponse(postService.getPost(id));
    }


    @PostMapping
    public Post createPost(@RequestBody @Valid CreatePostRequest request) {
        User currentUser = userService.getCurrentUser();
        return postService.createPost(
                request.title(),
                request.content(),
                currentUser
        );
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id,
                           @RequestBody @Valid UpdatePostRequest request) {

        User currentUser = userService.getCurrentUser();
        return postService.updatePost(
                id,
                request.title(),
                request.content(),
                currentUser
        );
    }


    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        postService.deletePost(id, currentUser);
    }
}


