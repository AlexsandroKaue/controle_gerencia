package br.com.cogerh.template.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.PermissaoDAO;
import br.com.cogerh.template.model.Permissao;
import br.com.cogerh.template.util.HasValue;

@Transactional
@Repository
public class PermissaoDAOImpl extends GenericDAOImpl<Permissao, Integer> implements PermissaoDAO
{
	public List<Permissao> listar(String pesquisa) 
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(pesquisa))
			cond.append(" and (upper(nome) like :nome or upper(descricao) like :descricao) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("		per ");
		sb.append(" from ");
		sb.append(" 	Permissao per ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	per.nome "); 

		final TypedQuery<Permissao> query = entityManager.createQuery(sb.toString(), Permissao.class);

		if (HasValue.execute(pesquisa))
		{
			query.setParameter("nome", "%" + pesquisa.toUpperCase() + "%");
			query.setParameter("descricao", "%" + pesquisa.toUpperCase() + "%");
		}

		return query.getResultList();
	}
}