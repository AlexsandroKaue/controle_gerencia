package br.com.cogerh.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="computador")
public class Computador extends PersistentEntityImpl
 {
	@Id
	@Column(name = "comp_cod_id")
	@SequenceGenerator(name = "seq_computador", sequenceName = "seq_computador", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_computador", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	@Column(name = "comp_ID")
	private String ID;
	
	@Column(name = "comp_device")
	private String device;

	@Column(name = "comp_nome")
	private String nome;

	@Column(name = "comp_descricao")
	private String descricao;
	
	@Column(name = "comp_grupo")
	private String grupo;
	
	@Column(name = "comp_sist_operacional")
	private String sistemaOperacional;
	
	@Column(name = "comp_vers_sist_operacional")
	private String versaoSistemaOperacional;
	
	@Column(name = "comp_processador")
	private String processador;
	
	@Column(name = "comp_memoria")
	private String memoria;
	
	@Column(name = "comp_arquitetura")
	private String arquitetura;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSistemaOperacional() {
		return sistemaOperacional;
	}

	public void setSistemaOperacional(String sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
	}

	public String getVersaoSistemaOperacional() {
		return versaoSistemaOperacional;
	}

	public void setVersaoSistemaOperacional(String versaoSistemaOperacional) {
		this.versaoSistemaOperacional = versaoSistemaOperacional;
	}

	public String getProcessador() {
		return processador;
	}

	public void setProcessador(String processador) {
		this.processador = processador;
	}

	public String getMemoria() {
		return memoria;
	}

	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}

	public String getArquitetura() {
		return arquitetura;
	}

	public void setArquitetura(String arquitetura) {
		this.arquitetura = arquitetura;
	}
}