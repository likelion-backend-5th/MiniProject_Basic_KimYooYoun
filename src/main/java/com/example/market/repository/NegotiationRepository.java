package com.example.market.repository;

import com.example.market.entity.CommentEntity;
import com.example.market.entity.NegotiationEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NegotiationRepository extends JpaRepository<NegotiationEntity, Long> {
	List<NegotiationEntity> findAllBySalesItemItemId(Long itemId);
}
