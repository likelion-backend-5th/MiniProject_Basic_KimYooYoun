package com.example.market.entity;

import com.example.market.constants.NegotiationStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "negotiation")
@Getter
@Builder
@SQLDelete(sql = "UPDATE negotiation SET deleted_at = NOW() where comment_id = ?")
@Where(clause = "deleted_at is NULL")
public class NegotiationEntity extends BaseDateEntity implements PasswordCheckable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long negotiationId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private SalesItemEntity salesItem;
	@Column(name = "suggested_price", nullable = false)
	@NotNull(message = "Price is required")
	private Integer suggestedPrice;
	@Setter
	@Enumerated(EnumType.STRING)
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
				String password,
				NegotiationStatusType status) {
		return builder()
			.salesItem(salesItemEntity)
			.suggestedPrice(suggestedPrice)
			.writer(writer)
			.password(password)
			.status(status)
			.build();
	}
	public NegotiationEntity updateSuggestedPrice(String writer, String password, int suggestedPrice){
		this.writer = writer;
		this.password = password;
		this.suggestedPrice = suggestedPrice;
		return this;
	}

	public NegotiationEntity updateStatus(String requestStatus){
		NegotiationStatusType newStatusType = NegotiationStatusType.제안;// 초깃값 설정

		switch(requestStatus){
			case "수락":
				newStatusType = NegotiationStatusType.수락;
				break;
			case "거절":
				newStatusType = NegotiationStatusType.거절;
				break;
			case "확정":
				newStatusType = NegotiationStatusType.확정;
				break;
		}
		this.status = newStatusType;
		return this;
	}
}
