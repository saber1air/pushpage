package com.deliver.service;

import com.deliver.Post;
import com.deliver.dao.PostDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class PostService {
    @Autowired
    private PostDao postDao;

    public Post findById(int id) {
        return postDao.findById(id);
    }

    public Post save(Post post) {
        return postDao.save(post);
    }

    public int delete(int id) {
        return postDao.deleteById(id);
    }


}
