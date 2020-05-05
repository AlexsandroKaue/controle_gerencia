package br.com.cogerh.template.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.UsuarioDAO;
import br.com.cogerh.template.model.Usuario;
import br.com.cogerh.template.util.HasValue;

@Transactional
@Repository
public class UsuarioDAOImpl extends GenericDAOImpl<Usuario, Integer> implements UsuarioDAO
{
	public List<Usuario> listar(String pesquisa) 
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(pesquisa))
			cond.append(" and (upper(nome) like :nome or upper(login) like :login) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("		usu ");
		sb.append(" from ");
		sb.append(" 	Usuario usu ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	usu.nome "); 

		final TypedQuery<Usuario> query = entityManager.createQuery(sb.toString(), Usuario.class);

		if (HasValue.execute(pesquisa))
		{
			query.setParameter("nome", "%" + pesquisa.toUpperCase() + "%");
			query.setParameter("login", "%" + pesquisa.toUpperCase() + "%");
		}

		return query.getResultList();
	}

	public Usuario buscaPor(String login, String senha)
	{
		try 
		{
//			return entityManager.createQuery("select u from Usuario u where u.login = :login and u.senha = :senha and u.ativo =:ativo", Usuario.class).setParameter("login", login).setParameter("senha", senha).setParameter("ativo", EnumAtivo.SIM).getSingleResult();
			return entityManager.createQuery("select u from Usuario u where u.login = :login and u.senha = :senha", Usuario.class).setParameter("login", login).setParameter("senha", senha).getSingleResult();
		}
		catch (NoResultException e) 
		{
			e.printStackTrace();

			return null;
		} 
	}
}