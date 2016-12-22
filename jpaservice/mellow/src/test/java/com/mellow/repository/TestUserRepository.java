package com.mellow.repository;

import com.mellow.model.PostModel;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TestUserRepository {


    @Test
    public void canGetAllPosts() throws Exception {
        List<PostModel> postsIDb = new ArrayList<>();
        //PostModel post1 = execute(postRepository -> postRepository.save(new PostModel("test1")));
        //PostModel post2 = execute(postRepository -> postRepository.save(new PostModel("test2")));
        //postsIDb.add(post1);
        //postsIDb.add(post2);

        Iterable<PostModel> posts = executeMany(postRepository -> postRepository.findAll());
        System.out.println(posts.spliterator().estimateSize());
    }

    private PostModel execute(Function<PostRepository, PostModel> operation) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan("com.mellow.config");
            context.refresh();
            PostRepository postRepository = context.getBean(PostRepository.class);
            return operation.apply(postRepository);
        }
    }

    private Iterable<PostModel> executeMany(Function<PostRepository, Iterable<PostModel>> operation) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan("com.mellow.config");
            context.refresh();
            PostRepository postRepository = context.getBean(PostRepository.class);
            return operation.apply(postRepository);
        }
    }
}
