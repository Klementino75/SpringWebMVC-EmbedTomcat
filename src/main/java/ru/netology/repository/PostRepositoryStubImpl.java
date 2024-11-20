package ru.netology.repository;

import org.springframework.stereotype.Repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private final ConcurrentMap<Long, Post> list = new ConcurrentHashMap<>();
    private final AtomicLong NumberId = new AtomicLong(0);

    public List<Post> all() {
//        return (List<Post>) list.values();
        return new ArrayList<>(list.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = NumberId.incrementAndGet();
            post.setId(newId);
            list.put(newId, post);
        }
        if (list.containsKey(post.getId())) {
            list.put(post.getId(), post);
        } else {
            throw new NotFoundException("Не удалось обновить элемент.");
        }
        return post;
    }

    @Override
    public void removeById(long id) {
        if (!list.containsKey(id)) {
            throw new NotFoundException("Сообщение с id#" + id + " не найдено.");
        }
        list.remove(id);
    }
}