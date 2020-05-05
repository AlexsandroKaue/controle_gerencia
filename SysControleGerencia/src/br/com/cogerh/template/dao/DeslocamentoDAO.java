package br.com.cogerh.template.dao;

import java.util.Date;
import java.util.List;

import br.com.cogerh.template.model.Deslocamento;
import br.com.cogerh.template.model.Grupo;

/**
 * @author kaue
 *
 */

public interface DeslocamentoDAO extends GenericDAO<Deslocamento, Integer>
{
	public List<Deslocamento> buscarDeslocamentoByNumeroProcesso(String numeroProcesso);
	
	public List<Deslocamento> buscarDeslocamentoByCase(Integer casee);
	
	public List<Deslocamento> listar(String pesquisa);
	
	public List<Deslocamento> listarByFiltros(String pesquisa, Date dataInicial, Date dataFinal, Integer lotacao);
}
