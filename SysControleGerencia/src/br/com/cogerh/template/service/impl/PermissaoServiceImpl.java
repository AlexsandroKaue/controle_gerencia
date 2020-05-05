package br.com.cogerh.template.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.PermissaoDAO;
import br.com.cogerh.template.model.Permissao;
import br.com.cogerh.template.service.PermissaoService;

@Service
public class PermissaoServiceImpl implements PermissaoService
{
	private PermissaoDAO dao;

	@Autowired
	public PermissaoServiceImpl(PermissaoDAO dao)
	{
		this.dao = dao;
	}

	@Transactional
	public Permissao salvarPermissao(Permissao permissao) throws Exception
	{
		return this.dao.save(permissao);
	}

	@Transactional
	public Permissao alterarPermissao(Permissao permissao) throws Exception
	{
		return this.dao.update(permissao);
	}

	@Transactional
	public void removerPermissao(Permissao permissao) throws Exception
	{
		this.dao.delete(permissao);
	}

	@Transactional
	public List<Permissao> listarPermissoes(String pesquisa) throws Exception
	{
		List<Permissao> permissaoList = dao.listar(pesquisa);

		return permissaoList;
	}

	@Transactional
	public Permissao buscarPorId(Integer id) throws Exception
	{
		Permissao permissao = dao.buscarPorId(id);

		return permissao;
	}
}