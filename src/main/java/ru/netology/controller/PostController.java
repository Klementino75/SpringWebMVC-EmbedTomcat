package ru.netology.controller;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.netology.model.Post;
import ru.netology.model.PostDTO;
import ru.netology.service.PostService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  private final PostService service;

  @Autowired
  protected ModelMapper modelMapper;

  public PostController(PostService service) {
    this.service = service;
  }

  @GetMapping
  public Collection<PostDTO> all() {
    return service.all().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public PostDTO getById(@PathVariable long id) {
    return convertToDTO(service.getById(id));
  }

  @PostMapping
  public Post save(@RequestBody PostDTO postDTO) {
    return service.save(convertToModel(postDTO));
  }

  @DeleteMapping("/{id}")
  public void removeById(@PathVariable long id) {
    service.removeById(id);
  }

  public PostDTO convertToDTO(Post post) {
    PostDTO postDTO = modelMapper.map(post, PostDTO.class);
    postDTO.setId(post.getId());
    postDTO.setContent(post.getContent());
    return postDTO;
  }

  public Post convertToModel(PostDTO postDTO) {
    Post post = modelMapper.map(postDTO, Post.class);
    post.setId(postDTO.getId());
    post.setContent(postDTO.getContent());
    return post;
  }
}