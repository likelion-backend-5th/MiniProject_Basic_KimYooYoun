package com.example.market.entity;

import com.example.market.constants.NegotiationStatusType;
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
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "negotiation")
@Getter
@Builder
@SQLDelete(sql = "UPDATE negotiation SET deleted_at = NOW() where comment_id = ?")
@Where(clause = "deleted_at is NULL")
public class NegotiationEntity extends BaseDateEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long negotiationId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private SalesItemEntity salesItem;
	@Column(name = "suggested_price", nullable = false)
	@NotNull(message = "Price is required")
	private Integer suggestedPrice;
	private NegotiationStatusType status;
	@Column(nullable = false)
	@NotBlank(message = "writer is required")
	private String writer;
	private String password;

	@Tolerate
	public NegotiationEntity(){
	}

	public static NegotiationEntity of(
				SalesItemEntity salesItemEntity,
				int suggestedPrice,
				String writer,
				String password) {
		return builder()
			.salesItem(salesItemEntity)
			.suggestedPrice(suggestedPrice)
			.writer(writer)
			.password(password)
			.build();
	}
}
