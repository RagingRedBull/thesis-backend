package net.prmts.app.model.dto;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonGetter;

import net.prmts.app.model.DetectorUnitLog;

public class DetectorUnitLogDto {
	private int id;
	private String macAddress;
	private float temperature;
	private float humidity;
	private ZonedDateTime dateRecorded;
	
	public DetectorUnitLogDto(String macAddress, float temperature, float humidity, ZonedDateTime dateRecorded) {
		super();
		this.macAddress = macAddress;
		this.temperature = temperature;
		this.humidity = humidity;
		this.dateRecorded = dateRecorded;
	}
	
	public DetectorUnitLogDto(DetectorUnitLog log) {
		this.id = log.getId();
		this.macAddress = log.getDetectorUnit().getMacAddress();
		this.temperature = log.getTemperature();
		this.humidity = log.getHumidity();
		this.dateRecorded = log.getDateRecorded();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public ZonedDateTime getDateRecorded() {
		return dateRecorded;
	}

	public void setDateRecorded(ZonedDateTime dateRecorded) {
		this.dateRecorded = dateRecorded;
	}
	@JsonGetter(value = "dateRecorded")
	public String getDateRecordedFormatted() {
		return DateTimeFormatter.RFC_1123_DATE_TIME.format(this.dateRecorded);
	}
}
