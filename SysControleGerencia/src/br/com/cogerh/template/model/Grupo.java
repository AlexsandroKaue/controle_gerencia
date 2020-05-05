package br.com.cogerh.template.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cogerh.template.enumeration.EnumStatus;

@Entity
@Table(name="grupo")
public class Grupo extends PersistentEntityImpl
 {
	@Id
	@Column(name = "gru_cod_id")
	@SequenceGenerator(name = "seq_grupo", sequenceName = "seq_grupo", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_grupo", strategy = GenerationType.SEQUENCE)
    private Integer id;

	@Column(name = "gru_nome")
	private String nome;

	@Column(name = "gru_descricao")
	private String descricao;

	@OneToMany(mappedBy="grupo", cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<GrupoPermissao> grupoPermissaoList;

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

	public List<GrupoPermissao> getGrupoPermissaoList()
	{
		return grupoPermissaoList;
	}

	public void setGrupoPermissaoList(List<GrupoPermissao> grupoPermissaoList)
	{
		this.grupoPermissaoList = grupoPermissaoList;
	}
}