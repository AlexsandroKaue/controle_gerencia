package br.com.cogerh.template.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cronometro")
public class Cronometro extends PersistentEntityImpl
{

	@Id
	@Column(name = "cron_cod_id")
	@SequenceGenerator(name = "seq_cronometro", sequenceName = "seq_cronometro", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_cronometro", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "cron_cod_inst_biz")
	private Integer codigoInstanciaBizagi;
	
	@OneToOne
	@JoinColumn(name = "cron_sensor")
	private Sensor sensor;
	
	@Column(name = "cron_case")
	private Integer casee;
	
	@Column(name = "cron_data_inicio")
	private Date dataDeInicio;
	
	@Column(name = "cron_data_fim")
	private Date dataDeFim;
	
	@Column(name = "cron_duracao")
	private Integer duracao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Integer getCodigoInstanciaBizagi() {
		return codigoInstanciaBizagi;
	}

	public void setCodigoInstanciaBizagi(Integer codigoInstanciaBizagi) {
		this.codigoInstanciaBizagi = codigoInstanciaBizagi;
	}

	public Integer getCasee() {
		return casee;
	}

	public void setCasee(Integer casee) {
		this.casee = casee;
	}

	public Date getDataDeInicio() {
		return dataDeInicio;
	}

	public void setDataDeInicio(Date dataDeInicio) {
		this.dataDeInicio = dataDeInicio;
	}

	public Date getDataDeFim() {
		return dataDeFim;
	}

	public void setDataDeFim(Date dataDeFim) {
		this.dataDeFim = dataDeFim;
	}

}
