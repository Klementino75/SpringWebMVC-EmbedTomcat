package ru.netology.repository;

import org.springframework.stereotype.Repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository implements PostRepositoryInterface {
  private final ConcurrentMap<Long, Post> list = new ConcurrentHashMap<>();
  private final AtomicLong NumberId = new AtomicLong(0);

  public Collection<Post> all() {
    return list.values().stream()
            .filter(Post::isRemoved)
            .collect(Collectors.toList());
  }

  public Optional<Post> getById(long id) {
    Post postRemoved = list.get(id);
    return Optional.ofNullable(postRemoved.isRemoved() ? null : list.get(id));
  }

  @Override
  public Post save(Post post) {
    if (post.getId() == 0 && post.isRemoved()) {
      long newId = NumberId.incrementAndGet();
      post.setId(newId);
      list.put(newId, post);
    }
    if (list.containsKey(post.getId()) && post.isRemoved()) {
      list.put(post.getId(), post);
    } else {
      throw new NotFoundException("Не удалось обновить элемент.");
    }
    return post;
  }

  @Override
  public void removeById(long id) {
    Post postRemoved = list.get(id);
    if(postRemoved != null){
      postRemoved.setRemoved(true);
    }
    throw new NotFoundException("Сообщение с id#" + id + " не найдено.");
  }
}