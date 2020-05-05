package br.com.cogerh.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sla")
public class Sla extends PersistentEntityImpl
{

	@Id
	@Column(name = "sla_cod_id")
	@SequenceGenerator(name = "seq_sla", sequenceName = "seq_sla", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_sla", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "sla_dias")
	private Integer dias;
	
	@Column(name = "sla_horas")
	private Integer horas;
	
	@Column(name = "sla_minutos")
	private Integer minutos;

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public Integer getHoras() {
		return horas;
	}

	public void setHoras(Integer horas) {
		this.horas = horas;
	}

	public Integer getMinutos() {
		return minutos;
	}

	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
