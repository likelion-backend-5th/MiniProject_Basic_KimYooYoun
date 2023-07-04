package com.example.market.entity;

import com.example.market.constants.ItemStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "sales_item")
@Getter
@Builder
@SQLDelete(sql = "UPDATE sales_item SET status = 'DELETE' where id = ?")
public class SalesItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id")
	private Long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Column(name = "image_url")
	private String imageUrl;
	@Column(name = "min_price_wanted", nullable = false)
	private int minPrice;
	@Enumerated(EnumType.STRING)
	private ItemStatusType status;
	@Column(nullable = false)
	private String writer;
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
}
