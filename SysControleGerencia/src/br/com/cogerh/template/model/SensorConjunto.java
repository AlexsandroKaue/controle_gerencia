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
@Table(name="sensor_conjunto")
public class SensorConjunto  extends PersistentEntityImpl
{
	@Id
	@Column(name = "sen_conj_cod_id")
	@SequenceGenerator(name = "seq_sensor_conjunto", sequenceName = "seq_sensor_conjunto", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_sensor_conjunto", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "sen_cod_id")
	private Sensor sensor;

	@ManyToOne
	@JoinColumn(name = "conj_cod_id")
	private Conjunto conjunto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Conjunto getConjunto() {
		return conjunto;
	}

	public void setConjunto(Conjunto conjunto) {
		this.conjunto = conjunto;
	}

}
