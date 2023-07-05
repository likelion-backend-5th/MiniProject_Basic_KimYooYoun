package com.example.market.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.market.constants.ItemStatusType;
import com.example.market.entity.SalesItemEntity;
import com.example.market.exception.ApplicationException;
import com.example.market.exception.ErrorCode;
import com.example.market.fixture.SalesItemFixture;
import com.example.market.repository.SalesItemRepository;
import jakarta.validation.ConstraintViolationException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
public class SalesItemServiceTest {
	@Mock
	private SalesItemRepository repository;

	@InjectMocks
	private SalesItemService service;

	@Test
	@DisplayName("중고물품 등록 성공 테스트")
	void SalesItemCreateSuccess(){
		when(repository.save(any())).thenReturn(mock(SalesItemEntity.class));

		Assertions.assertDoesNotThrow(() -> service.create("title", "discription", 10000, "writer", "passwood"));
	}

	@Test
	@DisplayName("중고물품 수정 성공 테스트")
	void SalesItemModifySuccess(){
		SalesItemEntity entity = SalesItemFixture.get("title", "description", 10000, "writer", "password");

		when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

		Assertions.assertDoesNotThrow(() -> service.modify(entity.getId(), "title", "description", 5000, "writer", "password"));
	}

	@Test
	@DisplayName("중고물품 수정 시 id가 존재하지 않는 실패 테스트")
	void SaliesItemModifyFailCausedByNottFoundId(){
		SalesItemEntity entity = SalesItemFixture.get("title", "description", 10000, "writer", "password");

		when(repository.findById(entity.getId())).thenReturn(Optional.empty());

		ApplicationException e = Assertions.assertThrows(ApplicationException.class,
			() -> service.modify(entity.getId(), "title", "description", 5000, "writer", "password"));
		Assertions.assertEquals(ErrorCode.SALES_ITEM_NOT_FOUND, e.getErrorCode());
	}

	@Test
	@DisplayName("중고물품 수정 시 패스워드가 알치하지 않는 실패 테스트")
	void SaliesItemModifyFailCausedByNotAuthorizePassword(){
		SalesItemEntity entity = SalesItemFixture.get("title", "description", 10000, "writer", "password");

		when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
		ApplicationException e = Assertions.assertThrows(ApplicationException.class,
			() -> service.modify(entity.getId(), "title", "description", 5000, "writer", "not"));

		Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
	}

	@Test
	@DisplayName("중고물품 삭제 성공 테스트")
	void SalesItemDeleteSuccess(){
		SalesItemEntity entity = SalesItemFixture.get("title", "description", 10000, "writer", "password");

		when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

		Assertions.assertDoesNotThrow(() -> {
			service.delete(entity.getId(), "writer", "password");
			Optional<SalesItemEntity> deletedEntity = repository.findById(entity.getId());
			Assertions.assertEquals(ItemStatusType.DELETED, deletedEntity.get().getStatus());
		});
	}

	@Test
	@DisplayName("중고물품 삭제 시 id가 존재하지 않는 실패 테스트")
	void SaliesItemDeleteFailCausedByNottFoundId(){
		SalesItemEntity entity = SalesItemFixture.get("title", "description", 10000, "writer", "password");

		when(repository.findById(entity.getId())).thenReturn(Optional.empty());

		ApplicationException e = Assertions.assertThrows(ApplicationException.class,
			() -> service.delete(entity.getId(), "writer", "password"));
		Assertions.assertEquals(ErrorCode.SALES_ITEM_NOT_FOUND, e.getErrorCode());
	}

	@Test
	@DisplayName("중고물품 삭제 시 패스워드가 알치하지 않는 실패 테스트")
	void SaliesItemDeleteFailCausedByNotAuthorizePassword(){
		SalesItemEntity entity = SalesItemFixture.get("title", "description", 10000, "writer", "password");

		when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
		ApplicationException e = Assertions.assertThrows(ApplicationException.class,
			() -> service.delete(entity.getId(), "writer", "not"));

		Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
	}

}
