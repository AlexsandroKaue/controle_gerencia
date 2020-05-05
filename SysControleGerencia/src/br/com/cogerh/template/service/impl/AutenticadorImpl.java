package br.com.cogerh.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cogerh.template.dao.UsuarioDAO;
import br.com.cogerh.template.model.Usuario;
import br.com.cogerh.template.service.Autenticador;

@Service("autenticador")
public class AutenticadorImpl implements Autenticador 
{
	private UsuarioDAO dao;

	@Autowired
	public AutenticadorImpl(UsuarioDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public Usuario autentica(String login, String senha)
	{
		Usuario usuario = dao.buscaPor(login, senha);

		return usuario;
	}
}