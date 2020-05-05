package br.com.cogerh.template.service;

import java.util.List;

import br.com.cogerh.template.model.Conjunto;

public interface ConjuntoService 
{
	public Conjunto salvarConjunto(Conjunto conjunto) throws Exception;
	public Conjunto alterarConjunto(Conjunto conjunto) throws Exception;
	public void removerConjunto(Conjunto conjunto) throws Exception;
	public Conjunto buscarPorId(Integer id) throws Exception;
	public List<Conjunto> listarConjuntos(String pesquisa) throws Exception;
	public Conjunto buscarConjuntoByNome(String pesquisa) throws Exception;
}
