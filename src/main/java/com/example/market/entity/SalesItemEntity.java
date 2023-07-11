package com.example.market.entity;

import com.example.market.constraints.ItemStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "sales_item")
@Getter
@Builder
@SQLDelete(sql = "UPDATE sales_item SET deleted_at = NOW() where item_id = ?")
@Where(clause = "deleted_at is NULL")
public class SalesItemEntity extends BaseDateEntity implements PasswordCheckable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long itemId;
	@Column(nullable = false)
	@NotBlank(message = "title is required")
	private String title;
	@Column(nullable = false)
	@NotBlank(message = "description is required")
	private String description;
	@Setter
	@Column(name = "image_url")
	private String imageUrl;
	@Column(name = "min_price_wanted", nullable = false)
	@NotNull(message = "price is required")
	private Integer minPrice;
	@Setter
	@Enumerated(EnumType.STRING)
	private ItemStatusType status;
	@Column(nullable = false)
	@NotBlank(message = "writer is required")
	private String writer;
	@Column(nullable = false)
	@NotBlank(message = "password is required")
	private String password;
	@Tolerate
	public SalesItemEntity() {}
	public static SalesItemEntity of(
		String title,
		String description,
		int minPrice,
		ItemStatusType status,
		String writer,
		String password
	) {
		return builder()
			.title(title)
			.description(description)
			.minPrice(minPrice)
			.status(status)
			.writer(writer)
			.password(password)
			.build();
	}

	public SalesItemEntity updateSalesItem(String title, String description, int minPrice, String writer,
		String password){
		this.title = title;
		this.description = description;
		this.minPrice = minPrice;
		this.writer = writer;
		this.password = password;
		return this;
	}

}
