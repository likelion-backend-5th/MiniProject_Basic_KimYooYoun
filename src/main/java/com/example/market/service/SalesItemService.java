package com.example.market.service;

import com.example.market.constants.ItemStatusType;
import com.example.market.dto.response.SalesItemResponse;
import com.example.market.entity.SalesItemEntity;
import com.example.market.exception.ApplicationException;
import com.example.market.exception.ErrorCode;
import com.example.market.repository.SalesItemRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import static com.example.market.util.ServiceUtils.isValidPassword;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesItemService {

	private final SalesItemRepository repository;

	@Transactional
	public void create(
			String title,
			String description,
			int minPrice,
			String writer,
			String password
	) {
		repository.save(
			SalesItemEntity.of(title, description, minPrice, ItemStatusType.판매중, writer, password));
	}
	public SalesItemResponse getItem(Long SalesItemId){

		SalesItemEntity savedItem = repository.findById(SalesItemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		return SalesItemResponse.fromEntity(savedItem);
	}
	public Page<SalesItemResponse> getAllItems(int page, int limit){
		Pageable pageable = PageRequest.of(page, limit);
		Page<SalesItemEntity> result = repository.findAll(pageable);
		log.info("Result: {}", result.getContent());
		return result.map(SalesItemResponse::fromEntity);
	}
	@Transactional
	public void modify(
		Long salesItemId,
		String title,
		String description,
		int minPrice,
		String writer,
		String password
	){
		SalesItemEntity savedItem = repository.findById(salesItemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		if(!isValidPassword(password, savedItem))
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		savedItem.updateSalesItem(title, description, minPrice, writer, password);
		repository.saveAndFlush(savedItem);
	}

	@Transactional
	public void addImage(Long salesItemId, MultipartFile multipartFile, String writer, String password)
		throws IOException
	{
		SalesItemEntity savedItem = repository.findById(salesItemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		if(!isValidPassword(password, savedItem))
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		String fileName = getImagePath(salesItemId, multipartFile);
		multipartFile.transferTo(Path.of(fileName));
		savedItem.setImageUrl(String.format("/static/%d/%s", salesItemId, fileName));
		repository.saveAndFlush(savedItem);
	}
	private String getImagePath(Long id, MultipartFile file) throws IOException {
		String imageDir = String.format("media/%d/", id);
		Files.createDirectories(Path.of(imageDir));

		String[] fileName = file.getOriginalFilename().split("\\.");
		String imageFileName = "image."+ fileName[fileName.length - 1];
		return imageDir + imageFileName;
	}
	@Transactional
	public void delete(Long salesItemId, String writer, String password){
		SalesItemEntity savedItem = repository.findById(salesItemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		if(!isValidPassword(password, savedItem))
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		//TODO :: deleteAllBysalesItem, comment, negotiation 전파
		savedItem.setStatus(ItemStatusType.판매종료);
		repository.saveAndFlush(savedItem);
		repository.delete(savedItem);
	}
}
