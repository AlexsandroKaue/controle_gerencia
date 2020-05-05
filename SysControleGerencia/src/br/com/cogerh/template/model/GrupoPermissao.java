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
@Table(name="grupo_permissao")
public class GrupoPermissao extends PersistentEntityImpl
{
	@Id
	@Column(name = "grp_cod_id")
	@SequenceGenerator(name = "seq_grupo_permissao", sequenceName = "seq_grupo_permissao", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_grupo_permissao", strategy = GenerationType.SEQUENCE)
    private Integer id;

	@ManyToOne
	@JoinColumn(name = "gru_cod_id")
	private Grupo grupo;

	@ManyToOne
	@JoinColumn(name = "per_cod_id")
	private Permissao permissao;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Grupo getGrupo()
	{
		return grupo;
	}

	public void setGrupo(Grupo grupo)
	{
		this.grupo = grupo;
	}

	public Permissao getPermissao()
	{
		return permissao;
	}

	public void setPermissao(Permissao permissao)
	{
		this.permissao = permissao;
	}
}