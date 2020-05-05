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
@Table(name="sensor_ek")
public class SensorEK {
	
	@Id
	@Column(name = "sek_cod_id")
	@SequenceGenerator(name = "seq_sensor_ek", sequenceName = "seq_sensor_ek", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_sensor_ek", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "sek_cod_sen_bizagi")
	private Integer codigoSensorBizagi;

	@ManyToOne
	@JoinColumn(name = "sen_cod_id_fk")
	private Sensor sensor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigoSensorBizagi() {
		return codigoSensorBizagi;
	}

	public void setCodigoSensorBizagi(Integer codigoSensorBizagi) {
		this.codigoSensorBizagi = codigoSensorBizagi;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}
