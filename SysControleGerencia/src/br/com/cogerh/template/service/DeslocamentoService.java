package br.com.cogerh.template.service;

import java.util.Date;
import java.util.List;

import br.com.cogerh.template.model.Deslocamento;

public interface DeslocamentoService 
{
	public List<Deslocamento> buscarDeslocamentoByNumeroProcesso(String numeroProcesso) throws Exception;
	
	public List<Deslocamento> buscarDeslocamentoByCase(Integer casee) throws Exception;
	
	public List<Deslocamento> listar(String pesquisa) throws Exception;
	
	public List<Deslocamento> listarByFiltros(String pesquisa, Date dataInicial, Date dataFinal, Integer lotacao) throws Exception;
}
