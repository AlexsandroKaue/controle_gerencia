package br.com.cogerh.template.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.ComputadorDAO;
import br.com.cogerh.template.model.Computador;
import br.com.cogerh.template.webServices.ComputadorWebService;

@Transactional
@Repository
public class ComputadorDAOImpl extends GenericDAOImpl<Computador, Integer> implements ComputadorDAO
{

	public List<Computador> listar(String pesquisa) {

		return new ComputadorWebService().listarComputadores();
	}
	
}
