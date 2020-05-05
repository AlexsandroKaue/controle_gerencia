package br.com.cogerh.template.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.ComputadorDAO;
import br.com.cogerh.template.model.Computador;
import br.com.cogerh.template.service.ComputadorService;

@Service
public class ComputadorServiceImpl implements ComputadorService{

	private ComputadorDAO dao;
	
	@Autowired
	public ComputadorServiceImpl(ComputadorDAO dao) 
	{
		this.dao = dao;
	}

	@Transactional
	public Computador salvarComputador(Computador computador) throws Exception {
		// TODO Auto-generated method stub
		return dao.save(computador);
	}

	@Transactional
	public Computador alterarComputador(Computador computador) throws Exception {
		// TODO Auto-generated method stub
		return dao.update(computador);
	}

	@Transactional
	public void removerComputador(Computador computador) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(computador);
	}

	@Transactional
	public Computador buscarPorId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return dao.buscarPorId(id);
	}

	@Transactional
	public List<Computador> listarComputadores(String pesquisa)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.listar(pesquisa);
	}

	

}
