package com.example.market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "comments")
@Getter
@Builder
@SQLDelete(sql = "UPDATE comments SET deleted_at = NOW() where comment_id = ?")
@Where(clause = "deleted_at is NULL")
public class CommentEntity extends BaseDateEntity implements PasswordCheckable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private SalesItemEntity salesItem;
	@Column(nullable = false)
	@NotBlank(message = "writer is required")
	private String writer;
	private String password;
	@Column(nullable = false)
	@NotBlank(message = "content is required")
	private String content;
	private String reply;

	@Tolerate
	public CommentEntity() {}

	public static CommentEntity of(
			SalesItemEntity salesItemEntity,
			String writer,
			String password,
			String content) {
		return CommentEntity.builder()
			.salesItem(salesItemEntity)
			.writer(writer)
			.password(password)
			.content(content)
			.build();
	}

	public CommentEntity addReply(String reply){
		this.reply = reply;
		return this;
	}
	public CommentEntity updateComment(String writer, String password, String contents){
		this.writer = writer;
		this.password = password;
		this.content = contents;
		return this;
	}

}
