package br.com.cogerh.template.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.cogerh.template.enumeration.EnumAtivo;
import br.com.cogerh.template.model.Grupo;
import br.com.cogerh.template.model.Usuario;
import br.com.cogerh.template.model.UsuarioBizagi;
import br.com.cogerh.template.service.GrupoService;
import br.com.cogerh.template.service.UsuarioBizagiService;
import br.com.cogerh.template.service.UsuarioService;
import br.com.cogerh.template.util.FacesUtil;

@Controller
@Scope("view")
public class UsuarioBean extends AbstractBean
{
	private Usuario usuario = new Usuario();

	private String pesquisa;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private FacesUtil faces;
	
	@Autowired
	private UsuarioWeb usuarioWeb;
	
	@Autowired
	private UsuarioBizagiService usuarioBizagiService;
	
	private UsuarioBizagi usuarioBizagi;
	
	private List<UsuarioBizagi> usuarioBizagiList = new ArrayList<UsuarioBizagi>();
	private List<Usuario> usuarioList = new ArrayList<Usuario>();
	private List<Grupo> grupoList = new ArrayList<Grupo>();

	@PostConstruct
	public void init()
	{
		try
		{
			this.usuarioList = usuarioService.listarUsuarios(null);
			this.usuarioBizagiList = usuarioBizagiService.buscarUsuarios();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

    public void initForm(Integer usuarioId) 
    {
    	try
		{
	        if (null == usuario || usuarioId == 0)
	        {
	        	this.viewState = ViewState.NOVO;

	            this.usuario = new Usuario();
	        }
	        else
	        {
	        	this.viewState = ViewState.EDITAR;

	            this.usuario = this.usuarioService.buscarPorId(usuarioId);
	        }

	        // Popula a lista de grupos para exibicao no select
	        this.grupoList = grupoService.listarGrupos(null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }

	public void salvar()
	{
		try
		{
			this.usuario.setDtCadastro(new Date());
			this.usuario.setAtivo(EnumAtivo.SIM);
			this.usuario.setSenha(faces.convertStringToMd5(this.usuario.getSenha()));
			
			usuarioService.salvarUsuario(this.usuario);

			this.usuario = new Usuario();

			this.adicionaMensagemDeSucesso("Usuário adicionado com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			this.adicionaMensagemDeErro("Ocorreu um erro ao tentar salvar o registro");
		}
	}

	public void listar()
	{
		try
		{
			this.usuarioList = usuarioService.listarUsuarios(this.pesquisa);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void remover(Usuario usuario)
	{
		try
		{
			usuarioService.removerUsuario(usuario);

			this.usuarioList = usuarioService.listarUsuarios(null);

			this.adicionaMensagemDeSucesso("Usuário removido com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();

			this.adicionaMensagemDeErro("Ocorreu um erro ao tentar remover o registro");
		}
	}

	public void alterar()
	{
		try
		{
			// Caso o usuario esteja ativo seta a data de inativacao para null
			// Caso o usuario esteja inativo seta a data de inativacao para a data do dia
			if (this.usuario.getAtivo().equals(EnumAtivo.SIM))
				this.usuario.setDtInativacao(null);
			else
				this.usuario.setDtInativacao(new Date());

			this.usuario = usuarioService.alterarUsuario(this.usuario);

			this.adicionaMensagemDeSucesso("Usuário atualizado com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			this.adicionaMensagemDeErro("Ocorreu um erro ao tentar atualizar o registro");
		}
	}
	
	public void editarConta() {
		this.usuario = usuarioWeb.getUsuario();
		
		ExternalContext externalContext = FacesContext.getCurrentInstance()
	                .getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/pages/usuario/novo.xhtml?usuarioId=" + usuario.getId());
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}

	public void listarUsuariosBizagi()
	{
		this.setUsuarioBizagiList(this.usuarioBizagiService.buscarUsuarios());
	}
	
	public String showFormNovo() 
	{
        return "novo.xhtml?faces-redirect=true";
    }

	public String showFormListar() 
	{
        return "lista.xhtml?faces-redirect=true";
    }

	public String showFormEditar(Integer usuarioId) 
	{
		return "novo.xhtml?faces-redirect=true&usuarioId=" + usuarioId;
	}

	public EnumAtivo[] getEnumAtivoList() 
	{
	    return EnumAtivo.values();
	}

	public Usuario getUsuario() {return usuario;}
	public void setUsuario(Usuario usuario) {this.usuario = usuario;}

	public List<Usuario> getUsuarioList() {return usuarioList;}
	public void setUsuarioList(List<Usuario> usuarioList) {this.usuarioList = usuarioList;}

	public List<Grupo> getGrupoList() {return grupoList;}
	public void setGrupoList(List<Grupo> grupoList) {this.grupoList = grupoList;}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public List<UsuarioBizagi> getUsuarioBizagiList() {
		return usuarioBizagiList;
	}

	public void setUsuarioBizagiList(List<UsuarioBizagi> usuarioBizagiList) {
		this.usuarioBizagiList = usuarioBizagiList;
	}

	public UsuarioBizagi getUsuarioBizagi() {
		return usuarioBizagi;
	}

	public void setUsuarioBizagi(UsuarioBizagi usuarioBizagi) {
		this.usuarioBizagi = usuarioBizagi;
	}
}