package br.com.cogerh.template.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.DeslocamentoDAO;
import br.com.cogerh.template.model.Deslocamento;
import br.com.cogerh.template.service.DeslocamentoService;

/**
 * @author kaue
 * @date 20/08/2018
 */

@Service
public class DeslocamentoServiceImpl implements DeslocamentoService
{
	
	private DeslocamentoDAO dao;
	
	@Autowired
	public DeslocamentoServiceImpl(DeslocamentoDAO dao) 
	{
		this.dao = dao;
	}
	
	@Transactional
	public List<Deslocamento> listar(String pesquisa) throws Exception 
	{
		return dao.listar(pesquisa);
	}
	
	@Transactional
	public List<Deslocamento> buscarDeslocamentoByNumeroProcesso(String numeroProcesso) throws Exception 
	{
		return dao.buscarDeslocamentoByNumeroProcesso(numeroProcesso);
	}

	@Transactional
	public List<Deslocamento> buscarDeslocamentoByCase(Integer casee) throws Exception 
	{
		return dao.buscarDeslocamentoByCase(casee);
	}

	@Override
	public List<Deslocamento> listarByFiltros(String pesquisa, Date dataInicial, Date dataFinal,
			Integer lotacao) throws Exception {
		
		return dao.listarByFiltros(pesquisa, dataInicial, dataFinal, lotacao);
	}

	

}
