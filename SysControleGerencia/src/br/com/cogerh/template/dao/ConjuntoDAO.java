package br.com.cogerh.template.dao;

import java.util.List;

import br.com.cogerh.template.model.Conjunto;

public interface ConjuntoDAO extends GenericDAO<Conjunto, Integer>
{
	public List<Conjunto> listar(String pesquisa);
	
	public Conjunto buscarConjuntoByNome(String pesquisa);
}
