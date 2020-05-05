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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="conjunto")
public class Conjunto extends PersistentEntityImpl
{
	@Id
	@Column(name = "conj_cod_id")
	@SequenceGenerator(name = "seq_conjunto", sequenceName = "seq_conjunto", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_conjunto", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	@Column(name = "conj_nome")
	private String nome;

	@Column(name = "conj_descricao")
	private String descricao;
	
	@OneToOne
	@JoinColumn(name = "conj_sun_id_fk")
	private Sensor sensorUnitario;

	@OneToMany(mappedBy="conjunto", cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval=true)
	private List<ConjuntoTask> conjuntoTaskList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ConjuntoTask> getConjuntoTaskList() {
		return conjuntoTaskList;
	}

	public void setConjuntoTaskList(List<ConjuntoTask> conjuntoTaskList) {
		this.conjuntoTaskList = conjuntoTaskList;
	}

	public Sensor getSensorUnitario() {
		return sensorUnitario;
	}

	public void setSensorUnitario(Sensor sensorUnitario) {
		this.sensorUnitario = sensorUnitario;
	}
}
