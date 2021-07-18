package net.prmts.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.prmts.app.model.dto.FloorDto;

@Entity
@Table(name = "floors")
public class Floor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "img_url")
	private String url;
	
	public Floor() {
		//Default Constructor
	}
	
	public Floor(FloorDto dto) {
		this.url = dto.getUrl();
	}
	public Floor(String url) {
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
