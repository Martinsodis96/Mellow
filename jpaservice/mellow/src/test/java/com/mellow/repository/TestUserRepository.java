package com.mellow.repository;

import com.mellow.model.PostDao;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TestUserRepository {


    @Test
    public void canGetAllPosts() throws Exception {
        List<PostDao> postsIDb = new ArrayList<>();
        PostDao post1 = execute(postRepository -> postRepository.save(new PostDao("test1")));
        PostDao post2 = execute(postRepository -> postRepository.save(new PostDao("test2")));
        postsIDb.add(post1);
        postsIDb.add(post2);

        Iterable<PostDao> posts = executeMany(postRepository -> postRepository.findAll());
        System.out.println(posts.spliterator().estimateSize());
    }

    private PostDao execute(Function<PostRepository, PostDao> operation) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan("com.mellow.config");
            context.refresh();
            PostRepository postRepository = context.getBean(PostRepository.class);
            return operation.apply(postRepository);
        }
    }

    private Iterable<PostDao> executeMany(Function<PostRepository, Iterable<PostDao>> operation) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan("com.mellow.config");
            context.refresh();
            PostRepository postRepository = context.getBean(PostRepository.class);
            return operation.apply(postRepository);
        }
    }
}
