package com.example.market.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.market.entity.SalesItemEntity;
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

}
