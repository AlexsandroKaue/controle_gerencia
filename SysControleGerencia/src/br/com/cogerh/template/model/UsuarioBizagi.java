package br.com.cogerh.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="usuario_bizagi")
public class UsuarioBizagi extends PersistentEntityImpl
{
	
	@Id
	@Column(name = "usu_biz_cod_id")
	@SequenceGenerator(name = "seq_usuario_bizagi", sequenceName = "seq_usuario_bizagi", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_usuario_bizagi", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	@Column(name = "usu_biz_nome")
	private String nome;
	
	@Column(name = "usu_biz_gerencia")
	private String gerencia;

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

	public String getGerencia() {
		return gerencia;
	}

	public void setGerencia(String gerencia) {
		this.gerencia = gerencia;
	}

	
}
