package br.com.cogerh.template.service;

import java.util.List;

import br.com.cogerh.template.model.Computador;

public interface ComputadorService 
{
	public Computador salvarComputador(Computador computador) throws Exception;
	public Computador alterarComputador(Computador computador) throws Exception;
	public void removerComputador(Computador computador) throws Exception;
	public Computador buscarPorId(Integer id) throws Exception;
	public List<Computador> listarComputadores(String pesquisa) throws Exception;
	
}
