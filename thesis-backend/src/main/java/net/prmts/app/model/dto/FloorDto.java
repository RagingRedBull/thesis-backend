package net.prmts.app.model.dto;

import org.springframework.web.multipart.MultipartFile;

import net.prmts.app.model.Floor;

public class FloorDto {
	private int id;
	private String url;
	
	public FloorDto() {
		
	}
	public FloorDto(Floor entity) {
		this.id = entity.getId();
		this.url = entity.getUrl();
	}
	public FloorDto(String url) {
		this.url = url;
	}
	
	public FloorDto(int id, String url) {
		this.id = id;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
