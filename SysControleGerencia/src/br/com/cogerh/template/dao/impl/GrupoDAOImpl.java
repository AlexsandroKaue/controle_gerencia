package br.com.cogerh.template.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.GrupoDAO;
import br.com.cogerh.template.model.Grupo;
import br.com.cogerh.template.util.HasValue;

@Transactional
@Repository
public class GrupoDAOImpl extends GenericDAOImpl<Grupo, Integer> implements GrupoDAO
{
	public List<Grupo> listar(String pesquisa) 
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(pesquisa))
			cond.append(" and (upper(nome) like :nome or upper(descricao) like :descricao) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     gru ");
		sb.append(" from ");
		sb.append(" 	Grupo gru ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	gru.nome "); 

		final TypedQuery<Grupo> query = entityManager.createQuery(sb.toString(), Grupo.class);

		if (HasValue.execute(pesquisa))
		{
			query.setParameter("nome", "%" + pesquisa.toUpperCase() + "%");
			query.setParameter("descricao", "%" + pesquisa.toUpperCase() + "%");
		}

		return query.getResultList();
	}
	
	
	@Override
	public Grupo buscarGrupoByNome(String pesquisa) {
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();


		if (HasValue.execute(pesquisa))
			cond.append(" and (upper(nome) like :nome) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     gru ");
		sb.append(" from ");
		sb.append(" 	Grupo gru ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	gru.nome "); 

		final TypedQuery<Grupo> query = entityManager.createQuery(sb.toString(), Grupo.class);

		if (HasValue.execute(pesquisa))
		{
			query.setParameter("nome", "%" + pesquisa.toUpperCase() + "%");
		}
		


		return query.getSingleResult();
	}
	
}