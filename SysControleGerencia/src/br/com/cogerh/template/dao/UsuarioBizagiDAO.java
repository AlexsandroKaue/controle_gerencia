package br.com.cogerh.template.dao;

import java.util.List;

import br.com.cogerh.template.model.Usuario;
import br.com.cogerh.template.model.UsuarioBizagi;

public interface UsuarioBizagiDAO extends GenericDAO<UsuarioBizagi, Integer>
{
	public List<UsuarioBizagi> buscarUsuarios();
}
