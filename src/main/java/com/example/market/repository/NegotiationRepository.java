package com.example.market.repository;

import com.example.market.entity.CommentEntity;
import com.example.market.entity.NegotiationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NegotiationRepository extends JpaRepository<NegotiationEntity, Long> {
	List<NegotiationEntity> findAllBySalesItem_ItemId(Long itemId);
	Page<NegotiationEntity> findAllBySalesItem_ItemId(Long itemId, Pageable pageable);
	Optional<List<NegotiationEntity>> findAllBySalesItem_ItemIdAndWriter(Long itemId, String writer);
}
