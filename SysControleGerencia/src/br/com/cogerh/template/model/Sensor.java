package br.com.cogerh.template.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sensor")
public class Sensor extends PersistentEntityImpl
{

	@Id
	@Column(name = "sen_cod_id")
	@SequenceGenerator(name = "seq_sensor", sequenceName = "seq_sensor", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_sensor", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "sen_nome")
	private String nome;
	
	@Column(name = "sen_nome_exibicao")
	private String nomeDeExibicao;
	
	@Column(name = "sen_descricao")
	private String descricao;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sen_sla_fk")
	private Sla sla;
	
	@OneToMany(mappedBy="sensor", cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval=true)
	private List<SensorConjunto> sensorConjuntoList;
	
	@OneToMany(mappedBy="sensor", cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<SensorEK> sensorEKList;

	private String tipo;
	
	public Sensor(Integer id, String nome, String tipo)
	{
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public Sensor(){}

	public String getNome() {
		return nome; 
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNomeDeExibicao() {
		return nomeDeExibicao;
	}

	public void setNomeDeExibicao(String nomeDeExibicao) {
		this.nomeDeExibicao = nomeDeExibicao;
	}

	public Sla getSla() {
		return sla;
	}

	public void setSla(Sla sla) {
		this.sla = sla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<SensorConjunto> getSensorConjuntoList() {
		return sensorConjuntoList;
	}

	public void setSensorConjuntoList(List<SensorConjunto> sensorConjuntoList) {
		this.sensorConjuntoList = sensorConjuntoList;
	}
	
	public List<SensorEK> getSensorEKList() {
		return sensorEKList;
	}

	public void setSensorEKList(List<SensorEK> sensorEKList) {
		this.sensorEKList = sensorEKList;
	}

	/*public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}*/

}
