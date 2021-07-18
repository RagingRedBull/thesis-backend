package net.prmts.app.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.prmts.app.model.dto.DetectorUnitLogDto;

@Entity
@Table(name = "detector_unit_log")
public class DetectorUnitLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="detector_unit_id", nullable = false)
	private DetectorUnit detectorUnit;
	@Column(name = "temperature")
	private float temperature;
	@Column(name = "humidity")
	private float humidity;
	@Column(name = "date_recorded")
	private ZonedDateTime dateRecorded;
	/*
	 * Constrcutors
	 */
	public DetectorUnitLog() {

	}
	public DetectorUnitLog(DetectorUnitLogDto dto) {
		this.humidity = dto.getHumidity();
		this.temperature = dto.getTemperature();
	}
	/*
	 * Getters n Setters
	 */
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DetectorUnit getDetectorUnit() {
		return detectorUnit;
	}
	public void setDetectorUnit(DetectorUnit detectorUnit) {
		this.detectorUnit = detectorUnit;
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
	
}
