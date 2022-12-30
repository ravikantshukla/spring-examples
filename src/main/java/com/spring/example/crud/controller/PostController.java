package com.spring.example.crud.controller;

import com.spring.example.crud.exception.ResourceNotFoundException;
import com.spring.example.crud.repository.PostRepository;
import com.spring.example.crud.model.Post;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class PostController {

    private PostRepository postRepository;
    public PostController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Create a new Note
    @PostMapping("/posts")
    public Post createNote(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    }

    // Get Single post
    @GetMapping("/posts/{id}")
    public Post getPostById( @PathVariable(value = "id") Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable(value = "id") Long postId, @Valid @RequestBody Post postDetails){
        Post post = postRepository.findById(postId).orElseThrow(() -> (new ResourceNotFoundException("Post", "id", postId)));
        post.setContent(postDetails.getContent());

        Post updatePost = postRepository.save(post);
        return updatePost;
    }
}
