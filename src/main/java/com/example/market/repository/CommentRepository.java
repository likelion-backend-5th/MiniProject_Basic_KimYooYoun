package com.example.market.repository;

import com.example.market.entity.CommentEntity;
import javax.xml.stream.events.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	Page<CommentEntity> findAllBySalesItemItemId(Long itemId, Pageable pageable);
}
