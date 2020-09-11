package org.vtb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vtb.entity.Comment;
import org.vtb.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    private CommentRepository repo;

    @Autowired
    public void setRepo(CommentRepository repo) {
        this.repo = repo;
    }


    public Comment saveOrUpdate(Comment commentary) {
        return repo.save(commentary);
    }
}
