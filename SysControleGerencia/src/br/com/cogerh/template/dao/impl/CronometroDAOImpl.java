package br.com.cogerh.template.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.CronometroDAO;
import br.com.cogerh.template.model.Cronometro;
import br.com.cogerh.template.model.Sensor;
import br.com.cogerh.template.util.FormatUtil;
import br.com.cogerh.template.util.HasValue;

@Transactional
@Repository
public class CronometroDAOImpl extends GenericDAOImpl<Cronometro, Integer> implements CronometroDAO
{

	@Override
	public List<Cronometro> listar(String pesquisa) 
	{
		return null;
	}

	@Override
	public Integer obterQuantidadeCronometros(Sensor sensor) 
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (sensor != null)
			cond.append(" and (sensor.id = :sensor) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     count(cron) ");
		sb.append(" from ");
		sb.append(" 	Cronometro cron ");
		sb.append(  cond );

		final TypedQuery<Long> query = entityManager.createQuery(sb.toString(), Long.class);

		if (sensor != null)
		{
			query.setParameter("sensor", sensor.getId());
		}

		Integer i = query.getSingleResult() != null ? query.getSingleResult().intValue() : 0;
		return i;
	}
	
	
	/*@Override
	public Integer obterQuantidadeCronometros(Sensor sensor) 
	{
		ConexaoOracle conOracle = null;
		
		try 
		{
			conOracle = new ConexaoOracle();
			Statement stmt = conOracle.getConexao().createStatement();
		
			String sql = "SELECT *"
					+ "";
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/


	@Override
	public Integer obterDuracaoMinima(Sensor sensor) 
	{
		
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (sensor != null)
			cond.append(" and (sensor.id = :sensor) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     min(cron.duracao) ");
		sb.append(" from ");
		sb.append(" 	Cronometro cron ");
		sb.append(  cond );

		final TypedQuery<Integer> query = entityManager.createQuery(sb.toString(), Integer.class);

		if (sensor != null)
		{
			query.setParameter("sensor", sensor.getId());
		}
		
		return query.getSingleResult();
	}

	@Override
	public Integer obterDuracaoMaxima(Sensor sensor) 
	{
		
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (sensor != null)
			cond.append(" and (sensor.id = :sensor) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     max(cron.duracao) ");
		sb.append(" from ");
		sb.append(" 	Cronometro cron ");
		sb.append(  cond );

		final TypedQuery<Integer> query = entityManager.createQuery(sb.toString(), Integer.class);

		if (sensor != null)
		{
			query.setParameter("sensor", sensor.getId());
		}
		
		return query.getSingleResult();
		
	}

	@Override
	public List<Cronometro> listarCronometrosBySensor(Sensor sensor) 
	{

		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (sensor != null)
			cond.append(" and (sensor.id = :sensor) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     cron ");
		sb.append(" from ");
		sb.append(" 	Cronometro cron ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	cron.id "); 

		final TypedQuery<Cronometro> query = entityManager.createQuery(sb.toString(), Cronometro.class);

		if (sensor != null)
		{
			query.setParameter("sensor", sensor.getId());
		}
		
		return query.getResultList();
	}

	@Override
	public Cronometro buscarCronometroByCodigo(Integer codigoId) 
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if(HasValue.execute(codigoId)){
			cond.append(" and (codigoInstanciaBizagi = :codigoId) ");
		}

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     cron ");
		sb.append(" from ");
		sb.append(" 	Cronometro cron ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	cron.id "); 

		final TypedQuery<Cronometro> query = entityManager.createQuery(sb.toString(), Cronometro.class);

		if (HasValue.execute(codigoId))
		{
			query.setParameter("codigoId", codigoId);
		}
		
		try
		{
			return query.getSingleResult();
			
		} catch(javax.persistence.NoResultException e)
		{
			return null;
		}
		
	}

	@Override
	public Integer obterDuracaoCronometrosByFiltros(Integer sensorId,
			Integer casee, Date dataInicial, Date dataFinal) {

		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if(HasValue.execute(sensorId) && HasValue.execute(casee) && HasValue.execute(dataInicial) && HasValue.execute(dataFinal))
		{
			cond.append(" and (cronometro.sensor.id = :sensorId and "
					+ " cronometro.casee = :casee and " 
					+ " cronometro.dataDeInicio = :dataInicial and "
					+ " cronometro.dataDeFim = :dataFinal) ");
		}

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     sum(cronometro.duracao) ");
		sb.append(" from ");
		sb.append(" 	Cronometro cronometro ");
		sb.append(  cond ); 

		final TypedQuery<Long> query = entityManager.createQuery(sb.toString(), Long.class);

		if (HasValue.execute(sensorId) && HasValue.execute(casee) && HasValue.execute(dataInicial) && HasValue.execute(dataFinal))
		{
			query.setParameter("sensorId", sensorId);
			query.setParameter("casee", casee);
			query.setParameter("dataInicial", dataInicial);
			query.setParameter("dataFinal", dataFinal);
		}
		
		Integer i = query.getSingleResult() != null ? query.getSingleResult().intValue() : 0;
		return i;
	}

	@Override
	public Integer obterDuracaoTotal(Sensor sensor)
	{

		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (sensor != null)
			cond.append(" and (sensor.id = :sensor) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     sum(cron.duracao) ");
		sb.append(" from ");
		sb.append(" 	Cronometro cron ");
		sb.append(  cond );

		final TypedQuery<Long> query = entityManager.createQuery(sb.toString(), Long.class);

		if (sensor != null)
		{
			query.setParameter("sensor", sensor.getId());
		}
		
		
		Integer i = query.getSingleResult() != null ? query.getSingleResult().intValue() : 0;
		return i;
	}
	

}
