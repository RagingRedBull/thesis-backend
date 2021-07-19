package net.prmts.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.prmts.app.model.Floor;
import net.prmts.app.model.dto.FloorDto;
import net.prmts.app.repository.FloorRepository;

@Service
public class FloorService {
	private Logger logger = LoggerFactory.getLogger(FloorService.class);
	private String imgStorageBaseUrl;
	private FloorRepository floorRepository;
	private RestTemplate imgStorageRequest;

	public FloorService(FloorRepository floorRepository, @Value("${server.images.url}") String imgStorageUrl, RestTemplate imgStorageRequest) {
		this.floorRepository = floorRepository;
		this.imgStorageBaseUrl = imgStorageUrl;
		this.imgStorageRequest = imgStorageRequest;
	}
	public List<FloorDto> findAll(){
		return this.floorRepository.findAll().stream().map(FloorDto::new).collect(Collectors.toList());
	}
	public List<Integer> findAllIds(){
		return this.floorRepository.findAllIds();
	}
	public Optional<FloorDto> findById(Integer id) {
		Optional<Floor> entity = this.floorRepository.findById(id);
		Optional<FloorDto> dto = Optional.empty();
		if(!entity.isEmpty()) {
			dto = Optional.of(new FloorDto(entity.get()));
		}
		return dto;
	}
	public ResponseEntity<FloorDto> saveToImageServer(MultipartFile floorImgFile) {
		String imgStorageUrl = imgStorageBaseUrl + ":8080/images/map/upload";
		this.logger.info("IMG URL: " + imgStorageBaseUrl);
		// Set Header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		// Set Body
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		FileSystemResource floorImgFSR = new FileSystemResource(convert(floorImgFile));
		body.add("mapFile", floorImgFSR);
		// Prepare Request
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(body,
				headers);
		ResponseEntity<FloorDto> response = imgStorageRequest.postForEntity(imgStorageUrl, requestEntity, FloorDto.class);
		// Save URL on Image Server to DB
		this.floorRepository.save(new Floor(response.getBody()));
		// Clean up
		if(deleteFileSystemResource(floorImgFSR)) {
			this.logger.info("Temporary FSR has been deleted");
		} else {
			this.logger.info("File was not deleted");
		}
		return response;
	}

	/*
	 * Rest to Rest :>
	 * https://stackoverflow.com/questions/52152337/rest-api-call-other-rest-api-in-
	 * spring-boot-not-working-for-multipart-file
	 */
	private static File convert(MultipartFile file) {
		File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return convFile;
	}
	
	private static boolean deleteFileSystemResource(FileSystemResource fsr) {
		boolean isFileDeleted = false;
		try {
			Path tempFile = Paths.get(fsr.getURI());
			isFileDeleted = Files.deleteIfExists(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isFileDeleted;
	}
}
