package com.example.market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@EntityListeners(value = AuditingEntityListener.class)
public class BaseDateEntity {
	@CreatedDate
	@Column(name = "register_at", nullable = false, updatable = false)
	protected LocalDateTime createdAt;
	@LastModifiedDate
	@Column(name = "updated_at", updatable = true)
	protected LocalDateTime updatedAt;
	@Column(name = "deleted_at", updatable = false)
	protected LocalDateTime deletedAt;

}
