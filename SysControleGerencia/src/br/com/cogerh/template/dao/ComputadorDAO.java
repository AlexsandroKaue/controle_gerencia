package br.com.cogerh.template.dao;

import java.util.List;

import br.com.cogerh.template.model.Computador;

public interface ComputadorDAO extends GenericDAO<Computador, Integer>
{
	public List<Computador> listar(String pesquisa);
}
