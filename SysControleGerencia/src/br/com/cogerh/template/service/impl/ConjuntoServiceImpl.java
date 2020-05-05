package br.com.cogerh.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.ConjuntoDAO;
import br.com.cogerh.template.model.Conjunto;
import br.com.cogerh.template.model.Sensor;
import br.com.cogerh.template.model.SensorConjunto;
import br.com.cogerh.template.service.ConjuntoService;
import br.com.cogerh.template.service.SensorService;

@Service
public class ConjuntoServiceImpl implements ConjuntoService
{
	
	private ConjuntoDAO dao;
	
	@Autowired
	private SensorService sensorService;

	@Autowired
	public ConjuntoServiceImpl(ConjuntoDAO dao) {
		this.dao = dao;
	}

	@Transactional
	public Conjunto salvarConjunto(Conjunto conjunto) throws Exception {
		// TODO Auto-generated method stubR
		return dao.save(conjunto);
	}

	@Transactional
	public Conjunto alterarConjunto(Conjunto conjunto) throws Exception {
		// TODO Auto-generated method stub
		return dao.update(conjunto);
	}

	@Transactional
	public void removerConjunto(Conjunto conjunto) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(conjunto);
	}

	@Transactional
	public Conjunto buscarPorId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return dao.buscarPorId(id);
	}

	@Transactional
	public List<Conjunto> listarConjuntos(String pesquisa) throws Exception {
		// TODO Auto-generated method stub
		return dao.listar(pesquisa);
	}

	@Transactional
	public Conjunto buscarConjuntoByNome(String pesquisa) throws Exception {
		// TODO Auto-generated method stub
		return dao.buscarConjuntoByNome(pesquisa);
	}

}
