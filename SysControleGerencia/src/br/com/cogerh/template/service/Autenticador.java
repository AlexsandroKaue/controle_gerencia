package br.com.cogerh.template.service;

import br.com.cogerh.template.model.Usuario;

public interface Autenticador 
{
	public Usuario autentica(String login, String senha);
}