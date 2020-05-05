package br.com.cogerh.template.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.DeslocamentoDAO;
import br.com.cogerh.template.dao.GrupoDAO;
import br.com.cogerh.template.enumeration.EnumLotacao;
import br.com.cogerh.template.model.Deslocamento;
import br.com.cogerh.template.model.Grupo;
import br.com.cogerh.template.util.HasValue;

/**
 * @author kaue
 *
 */

@Transactional
@Repository
public class DeslocamentoDAOImpl extends GenericDAOImpl<Deslocamento, Integer> implements DeslocamentoDAO
{

	private static final long serialVersionUID = 1L;
	
	@Override
	public List<Deslocamento> listar(String pesquisa) 
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(pesquisa))
			cond.append(" and (numeroProcesso like :pesquisa or cast(casee as text) like :pesquisa) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     deslocamento ");
		sb.append(" from ");
		sb.append(" 	Deslocamento deslocamento ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	deslocamento.id "); 

		final TypedQuery<Deslocamento> query = entityManager.createQuery(sb.toString(), Deslocamento.class);

		if (HasValue.execute(pesquisa))
		{
			query.setParameter("pesquisa", "%" + pesquisa.toUpperCase() + "%");
		}

		return query.getResultList();
	}

	@Override
	public List<Deslocamento> buscarDeslocamentoByNumeroProcesso(String numeroProcesso) 
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(numeroProcesso))
			cond.append(" and (numeroProcesso = :numeroProcesso) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     deslocamento ");
		sb.append(" from ");
		sb.append(" 	Deslocamento deslocamento ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	deslocamento.id "); 

		final TypedQuery<Deslocamento> query = entityManager.createQuery(sb.toString(), Deslocamento.class);

		if (HasValue.execute(numeroProcesso))
		{
			query.setParameter("numeroProcesso", numeroProcesso.toUpperCase());
		}

		return query.getResultList();
	}

	@Override
	public List<Deslocamento> buscarDeslocamentoByCase(Integer casee) {
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(casee))
			cond.append(" and (casee = :casee) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     deslocamento ");
		sb.append(" from ");
		sb.append(" 	Deslocamento deslocamento ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	deslocamento.id "); 

		final TypedQuery<Deslocamento> query = entityManager.createQuery(sb.toString(), Deslocamento.class);

		if (HasValue.execute(casee))
		{
			query.setParameter("casee", casee);
		}

		return query.getResultList();
	}

	@Override
	public List<Deslocamento> listarByFiltros(String pesquisa, Date dataInicial, Date dataFinal, Integer lotacao) 
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();
		
		if (HasValue.execute(pesquisa))
			cond.append(" and (numeroProcesso like :pesquisa or cast(casee as text) like :pesquisa) ");

		if (HasValue.execute(dataInicial))
			cond.append(" and (:dataInicial <= timestampEntrada) ");
		
		if (HasValue.execute(dataFinal))
			cond.append(" and (timestampEntrada <= :dataFinal) ");
		
		if (HasValue.execute(lotacao))
			cond.append(" and (gerenciaDestino = :gerenciaDestino) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     deslocamento ");
		sb.append(" from ");
		sb.append(" 	Deslocamento deslocamento ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	deslocamento.id "); 

		final TypedQuery<Deslocamento> query = entityManager.createQuery(sb.toString(), Deslocamento.class);

		if (HasValue.execute(pesquisa))
		{
			query.setParameter("pesquisa", "%" + pesquisa + "%");
		}
		if (HasValue.execute(dataInicial))
		{
			query.setParameter("dataInicial", dataInicial);
		}
		if (HasValue.execute(dataFinal))
		{
			query.setParameter("dataFinal", dataFinal);
		}
		if (HasValue.execute(lotacao))
		{
			query.setParameter("gerenciaDestino", EnumLotacao.valueOf(lotacao));
		}

		return query.getResultList();
	}

}
