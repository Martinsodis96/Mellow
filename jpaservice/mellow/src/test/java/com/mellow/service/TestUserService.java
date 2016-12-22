package com.mellow.service;

import com.mellow.model.PostModel;
import com.mellow.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class TestUserService {

    @Mock
    private PostRepository postRepository;
    private List<PostModel> postsInDb;

    @InjectMocks
    private PostService postService;

    @Before
    public void setUp() throws Exception {
        postsInDb = new ArrayList<>();
      //  postsInDb.add(new PostModel("something"));
       // postsInDb.add(new PostModel("something Else"));
    }

    @Test
    public void canGetAllPosts() {
        when(postRepository.findAll()).thenReturn(postsInDb);
        Iterable<PostModel> usersFromDatabase = postService.getAllPosts();
        verify(postRepository, times(1)).findAll();
        usersFromDatabase.forEach(post -> {
            assertTrue(postsInDb.contains(post));
        });
    }
}
