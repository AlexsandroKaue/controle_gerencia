package br.com.cogerh.template.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.UsuarioDAO;
import br.com.cogerh.template.model.Usuario;
import br.com.cogerh.template.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService
{
	private UsuarioDAO dao;

	@Autowired
	public UsuarioServiceImpl(UsuarioDAO dao)
	{
		this.dao = dao;
	}

	@Transactional
	public Usuario salvarUsuario(Usuario usuario) throws Exception
	{
		return this.dao.save(usuario);
	}

	@Transactional
	public Usuario alterarUsuario(Usuario usuario) throws Exception
	{
		return this.dao.update(usuario);
	}

	@Transactional
	public void removerUsuario(Usuario usuario) throws Exception
	{
		this.dao.delete(usuario);
	}

	@Transactional
	public List<Usuario> listarUsuarios(String pesquisa) throws Exception
	{
		List<Usuario> usuarioList = dao.listar(pesquisa);

		return usuarioList;
	}

	@Transactional
	public Usuario buscarPorId(Integer id) throws Exception
	{
		Usuario usuario = dao.buscarPorId(id);

		return usuario;
	}
}