package net.prmts.app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="detector_unit")
public class DetectorUnit {
	@Id
	@Column(name = "mac_address")
	private String macAddress;
	@Column(name = "name")
	private String name;
	@OneToMany(mappedBy = "detectorUnit")
	private List<DetectorUnitLog> logs;
	/*
	 * Constructor
	 */
	public DetectorUnit() {

	}
	/*
	 * Getter And Setter
	 */
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DetectorUnitLog> getLogs() {
		return logs;
	}
	public void setLogs(List<DetectorUnitLog> logs) {
		this.logs = logs;
	}
	
	
}
