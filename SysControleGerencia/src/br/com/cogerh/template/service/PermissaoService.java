package br.com.cogerh.template.service;

import java.util.List;

import br.com.cogerh.template.model.Permissao;

public interface PermissaoService
{
	public Permissao salvarPermissao(Permissao grupo) throws Exception;
	public Permissao alterarPermissao(Permissao grupo) throws Exception;
	public void removerPermissao(Permissao grupo) throws Exception;
	public Permissao buscarPorId(Integer id) throws Exception;
	public List<Permissao> listarPermissoes(String pesquisa) throws Exception;
}