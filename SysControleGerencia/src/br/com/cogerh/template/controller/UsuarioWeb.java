package br.com.cogerh.template.controller;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.cogerh.template.model.GrupoPermissao;
import br.com.cogerh.template.model.Usuario;

@Component
@Scope("session")
public class UsuarioWeb extends AbstractBean
{
	private Usuario usuario;

	/**
	 * Verifica se o usuario possui a permissao para acessar a funcionalidade
	 * 
	 * @return
	 */
	public boolean temPermissao(String permissao)
	{
		
		for (GrupoPermissao grupoPermissao : usuario.getGrupo().getGrupoPermissaoList())
		{
			if (grupoPermissao.getPermissao().getNome().equals(permissao))
				return true;
		}
		
		return true;
	}
	
	/**
	 * Atribui o usuario logado ao usuario na sessao
	 * 
	 * @param usuario
	 */
	public void loga(Usuario usuario)
	{
		this.usuario = usuario;
	}

	/**
	 * Verifica se o usuario esta logado
	 * 
	 * @return
	 */
	public boolean isLogado()
	{
		return this.usuario != null;
	}
	
	public void desloga()
	{
		this.usuario = null;
		
	}
	
	public void sair()
	{
		desloga();
		
		ExternalContext externalContext = FacesContext.getCurrentInstance()
                .getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/login.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
		
	}
	
	public void paginaPrincipal()
	{
		
		ExternalContext externalContext = FacesContext.getCurrentInstance()
                .getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/principal.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
		
	}

	public Usuario getUsuario() {return usuario;}
	public void setUsuario(Usuario usuario) {this.usuario = usuario;}
}
