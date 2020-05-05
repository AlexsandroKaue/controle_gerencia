package br.com.cogerh.template.dao;

import java.util.List;

import br.com.cogerh.template.model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario, Integer>
{
	public List<Usuario> listar(String pesquisa); 
	public Usuario buscaPor(String login, String senha);
}