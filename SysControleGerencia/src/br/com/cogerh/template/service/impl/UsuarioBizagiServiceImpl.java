package br.com.cogerh.template.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.UsuarioBizagiDAO;
import br.com.cogerh.template.model.UsuarioBizagi;
import br.com.cogerh.template.service.UsuarioBizagiService;

@Service
public class UsuarioBizagiServiceImpl implements UsuarioBizagiService
{
	private UsuarioBizagiDAO dao;
	
	@Autowired
	public UsuarioBizagiServiceImpl(UsuarioBizagiDAO dao) 
	{
		this.dao = dao;
	}

	@Transactional
	public List<UsuarioBizagi> buscarUsuarios() 
	{
		return dao.buscarUsuarios();
	}

}
