package br.com.cogerh.template.dao;

import java.util.List;

import br.com.cogerh.template.model.Grupo;

public interface GrupoDAO extends GenericDAO<Grupo, Integer>
{
	public List<Grupo> listar(String pesquisa); 
	
	public Grupo buscarGrupoByNome(String pesquisa);
	
}