package br.com.cogerh.template.dao;

import java.util.List;

import br.com.cogerh.template.model.Permissao;

public interface PermissaoDAO extends GenericDAO<Permissao, Integer>
{
	public List<Permissao> listar(String pesquisa); 
}