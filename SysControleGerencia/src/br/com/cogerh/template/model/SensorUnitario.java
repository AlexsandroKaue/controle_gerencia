package br.com.cogerh.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sensor_unitario")
public class SensorUnitario 
{
	@Id
	@Column(name = "sun_cod_id")
	@SequenceGenerator(name = "seq_sensor_unitario", sequenceName = "seq_sensor_unitario", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_sensor_unitario", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "sun_cod_id_fk")
	private Sensor sensorPai;
	
	@Column(name = "sun_sen_cod_id_fk")
	private Integer sensorId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Sensor getSensorPai() {
		return sensorPai;
	}

	public void setSensorPai(Sensor sensorPai) {
		this.sensorPai = sensorPai;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}
	
	
	

}
