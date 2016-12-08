package com.mellow.repository;

import com.mellow.model.Post;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TestUserRepository {


    @Test
    public void canGetAllPosts() throws Exception {
        List<Post> postsIDb = new ArrayList<>();
        Post post1 = execute(postRepository -> postRepository.save(new Post("test1")));
        Post post2 = execute(postRepository -> postRepository.save(new Post("test2")));
        postsIDb.add(post1);
        postsIDb.add(post2);

        Iterable<Post> posts = executeMany(postRepository -> postRepository.findAll());
        System.out.println(posts.spliterator().estimateSize());
    }

    private Post execute(Function<PostRepository, Post> operation) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan("com.mellow.config");
            context.refresh();
            PostRepository postRepository = context.getBean(PostRepository.class);
            return operation.apply(postRepository);
        }
    }

    private Iterable<Post> executeMany(Function<PostRepository, Iterable<Post>> operation) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan("com.mellow.config");
            context.refresh();
            PostRepository postRepository = context.getBean(PostRepository.class);
            return operation.apply(postRepository);
        }
    }
}
