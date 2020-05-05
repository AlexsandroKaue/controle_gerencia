package br.com.cogerh.template.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.ConjuntoDAO;
import br.com.cogerh.template.model.Conjunto;
import br.com.cogerh.template.util.HasValue;

@Transactional
@Repository
public class ConjuntoDAOImpl extends GenericDAOImpl<Conjunto, Integer> implements ConjuntoDAO{

	@Override
	public List<Conjunto> listar(String pesquisa) 
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(pesquisa))
			cond.append(" and (upper(nome) like :pesquisa) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     conj ");
		sb.append(" from ");
		sb.append(" 	Conjunto conj ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	conj.nome "); 

		final TypedQuery<Conjunto> query = entityManager.createQuery(sb.toString(), Conjunto.class);

		if (HasValue.execute(pesquisa))
		{
			query.setParameter("pesquisa", "%" + pesquisa.toUpperCase() + "%");
		}

		return query.getResultList();
	}

	@Override
	public Conjunto buscarConjuntoByNome(String pesquisa) {
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(pesquisa))
			cond.append(" and (upper(nome) = :nome) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     grp_tsk ");
		sb.append(" from ");
		sb.append(" 	GrupoTask grp_tsk ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	grp_tsk.nome "); 

		final TypedQuery<Conjunto> query = entityManager.createQuery(sb.toString(), Conjunto.class);

		if (HasValue.execute(pesquisa))
		{
			query.setParameter("nome", "%" + pesquisa.toUpperCase() + "%");
		}

		return query.getSingleResult();
	}

}
