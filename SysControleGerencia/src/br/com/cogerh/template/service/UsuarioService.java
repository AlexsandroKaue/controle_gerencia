package br.com.cogerh.template.service;

import java.util.List;

import br.com.cogerh.template.model.Usuario;

public interface UsuarioService
{
	public Usuario salvarUsuario(Usuario usuario) throws Exception;
	public Usuario alterarUsuario(Usuario usuario) throws Exception;
	public void removerUsuario(Usuario usuario) throws Exception;
	public Usuario buscarPorId(Integer id) throws Exception;
	public List<Usuario> listarUsuarios(String pesquisa) throws Exception;
}