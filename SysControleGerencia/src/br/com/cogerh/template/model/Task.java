package br.com.cogerh.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="task")
public class Task extends PersistentEntityImpl
{
	
	@Id
	@Column(name = "tsk_cod_id")
	@SequenceGenerator(name = "seq_task", sequenceName = "seq_task", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_task", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "tsk_codigo")
	private Integer codigo;
	
	@Column(name = "tsk_nome")
	private String nome;
	
	@Column(name = "tsk_casee")
	private Integer casee;
	
	@Column(name = "tsk_entrada")
	private String entrada;
	
	@Column(name = "tsk_saida")
	private String saida;
	
	@Column(name = "tsk_permanencia")
	private String permanencia;
	
	@Column(name = "tsk_duracao")
	private Integer duracao;
	
	@ManyToOne
	private Sensor sensor;
	
	public Task(){}
	
	public Task(Integer id, String nome)
	{
		this.id = id;
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCasee() {
		return casee;
	}

	public void setCasee(Integer casee) {
		this.casee = casee;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public String getPermanencia() {
		return permanencia;
	}

	public void setPermanencia(String permanencia) {
		this.permanencia = permanencia;
	}

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

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

}
