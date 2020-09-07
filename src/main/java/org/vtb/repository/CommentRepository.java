package org.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vtb.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}