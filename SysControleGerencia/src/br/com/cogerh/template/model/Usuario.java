package br.com.cogerh.template.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.cogerh.template.enumeration.EnumAtivo;

@Entity
@Table(name="usuario")
public class Usuario extends PersistentEntityImpl
{
	@Id
	@Column(name = "usu_cod_id")
	@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 3)
	@GeneratedValue(generator = "seq_usuario", strategy = GenerationType.SEQUENCE)
    private Integer id;

	@Column(name = "usu_nome")
	private String nome;

	@Column(name = "usu_login")
	private String login;

	@Column(name = "usu_senha")
	private String senha;

	@Column(name = "usu_email")
	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name = "usu_dt_cadastro")
	private Date dtCadastro;

	@Temporal(TemporalType.DATE)
	@Column(name = "usu_dt_inativacao")
	private Date dtInativacao;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "usu_ativo")
	private EnumAtivo ativo;
	
	@ManyToOne
	@JoinColumn(name = "gru_cod_id")
	private Grupo grupo;

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

	public String getLogin() 
	{
		return login;
	}

	public void setLogin(String login) 
	{
		this.login = login;
	}

	public String getSenha() 
	{
		return senha;
	}

	public void setSenha(String senha) 
	{
		this.senha = senha;
	}
	
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Date getDtCadastro()
	{
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro)
	{
		this.dtCadastro = dtCadastro;
	}

	public Date getDtInativacao()
	{
		return dtInativacao;
	}

	public void setDtInativacao(Date dtInativacao)
	{
		this.dtInativacao = dtInativacao;
	}

	public EnumAtivo getAtivo()
	{
		return ativo;
	}

	public void setAtivo(EnumAtivo ativo)
	{
		this.ativo = ativo;
	}

	public Grupo getGrupo()
	{
		return grupo;
	}

	public void setGrupo(Grupo grupo)
	{
		this.grupo = grupo;
	}
	
	
	
}