package br.com.cogerh.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.cogerh.template.enumeration.EnumAtivo;
import br.com.cogerh.template.model.Usuario;
import br.com.cogerh.template.service.Autenticador;
import br.com.cogerh.template.service.ConjuntoService;
import br.com.cogerh.template.service.TaskService;
import br.com.cogerh.template.util.FacesUtil;

@Controller
@Scope("request")
public class LoginBean extends AbstractBean
{
	private String login;
	private String senha;
	
	private UsuarioWeb usuarioWeb;
	private Autenticador autenticador;
	
	@Autowired
	private FacesUtil faces;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ConjuntoService grupoTaskService;
	
	@Autowired
	public LoginBean(Autenticador aut, UsuarioWeb usuWeb)
	{
		this.autenticador = aut;
		this.usuarioWeb = usuWeb;
	}
	
	public String logar()
	{
		Usuario usuario = autenticador.autentica(login, faces.convertStringToMd5(senha));
		
		System.out.println(faces.convertStringToMd5(senha));
		
		if (usuario != null)
		{
			if (usuario.getAtivo().equals(EnumAtivo.SIM))
			{
				usuarioWeb.loga(usuario);
				
				try {
					taskService.atualizarTarefas();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return "principal?faces-redirect=true";
			}
			else
			{
				this.adicionaMensagemDeErro("O Usuário informado está inativo");

				return null;
			}
		}

		this.adicionaMensagemDeErro("Login e/ou Senha inválidos");

		return null;
	}
	
	public String deslogar()
	{
		usuarioWeb.desloga();

		return "/login?faces-redirect=true";
	}

	public String getLogin() {return login;}
	public void setLogin(String login) {this.login = login;}

	public String getSenha() {return senha;}
	public void setSenha(String senha) {this.senha = senha;}

	public UsuarioWeb getUsuarioWeb() {return usuarioWeb;}
	public void setUsuarioWeb(UsuarioWeb usuarioWeb) {this.usuarioWeb = usuarioWeb;}

	public Autenticador getAutenticador() {return autenticador;}
	public void setAutenticador(Autenticador autenticador) {this.autenticador = autenticador;}
}