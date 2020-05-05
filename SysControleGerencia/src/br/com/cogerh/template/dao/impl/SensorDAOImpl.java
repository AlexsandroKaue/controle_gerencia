package br.com.cogerh.template.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.SensorDAO;
import br.com.cogerh.template.model.Conjunto;
import br.com.cogerh.template.model.ConjuntoTask;
import br.com.cogerh.template.model.Cronometro;
import br.com.cogerh.template.model.Grupo;
import br.com.cogerh.template.model.Sensor;
import br.com.cogerh.template.model.SensorConjunto;
import br.com.cogerh.template.model.SensorEK;
import br.com.cogerh.template.model.Task;
import br.com.cogerh.template.service.CronometroService;
import br.com.cogerh.template.util.ConexaoOracle;
import br.com.cogerh.template.util.HasValue;

@Transactional
@Repository
public class SensorDAOImpl extends GenericDAOImpl<Sensor, Integer> implements SensorDAO
{

	@Autowired
	private CronometroService cronometroService;
	
	@Override
	public List<Sensor> listar(String pesquisa) {
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(pesquisa))
			cond.append(" and (upper(nome) like :nome or upper(descricao) like :descricao) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     sen ");
		sb.append(" from ");
		sb.append(" 	Sensor sen ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	sen.nome "); 

		final TypedQuery<Sensor> query = entityManager.createQuery(sb.toString(), Sensor.class);

		if (HasValue.execute(pesquisa))
		{
			query.setParameter("nome", "%" + pesquisa.toUpperCase() + "%");
			query.setParameter("descricao", "%" + pesquisa.toUpperCase() + "%");
		}

		return query.getResultList();
	}
	
	public List<Cronometro> obterInstanciasDoSensor(Sensor sensor, Integer idCase, List<Task> tarefas) 
	{
		
		ConexaoOracle conOracle = null;
		/*Integer idCase = 0;
		Integer idTask = 0;*/
		Integer inicioIntervalo = 0;
		Integer fimIntervalo = 0;
		Integer idtaskFinal = tarefas.get(tarefas.size() - 1).getCodigo();
		List<Cronometro> cronometroList = new ArrayList<>();
		
		try {
			conOracle = new ConexaoOracle();
			
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql;
			
			sql = "SELECT * FROM BPM_COGERH10.WORKITEM W WHERE (W.IDCASE = " + idCase + " AND W.IDTASK = " + idtaskFinal + " AND W.WISOLUTIONDATE is not null) ORDER BY W.WISOLUTIONDATE";
			
			ResultSet rs = stmt.executeQuery(sql);

			int i = 0;
			if(rs.next())
			{
				fimIntervalo = rs.getInt("IDWORKITEM");
				Cronometro cronometro = new Cronometro();
				cronometro.setSensor(sensor);
				//cronometro.setDuracao(calcularTempo(idCase, inicioIntervalo, fimIntervalo, tarefas));
				cronometroList.add(cronometro);
				inicioIntervalo = fimIntervalo + 1;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return cronometroList;
	}
	
	public Long calcularTempo(Integer idCase, Integer inicio, Integer fim, List<Task> taskList) 
	{

		ConexaoOracle conOracle = null;
		Long timeCounter = 0L;
		String cond = "";
		
		try {
			conOracle = new ConexaoOracle();
			
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql = "";
			
			sql =  " SELECT * FROM BPM_COGERH10.WORKITEM W WHERE (W.IDWORKITEM >= " + inicio + "AND W.IDWORKITEM <= " + fim + " AND W.IDCASE = " + idCase;
			
			if(!taskList.isEmpty())
			{
				cond = " AND (";
				
				for(int i=0; i<taskList.size()-1; i++)
				{
					cond += " W.IDTASK = " + taskList.get(i).getCodigo() + " OR ";					
				}
				
				cond += " W.IDTASK = " + taskList.get(taskList.size()-1).getCodigo() + ")";
			}
			
			sql += cond;
			
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				timeCounter += rs.getDate("WISOLUTIONDATE").getTime();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return timeCounter;
	}

	@Override
	public Integer obterQuantidadeDeInstancias(Sensor sensor) 
	{
		Integer retorno = null;
		
		try {
			retorno = cronometroService.obterQuantidadeCronometros(sensor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retorno;
	}

	@Override
	public void obterInstanciasDoSensor(Sensor sensor) {
		ConexaoOracle conOracle = null;
		Statement stmt = null;
		try {
			conOracle = new ConexaoOracle();
			stmt = conOracle.getConexao().createStatement();
			List<SensorEK> sensorEKList = sensor.getSensorEKList();
			String sql;
			
			for(SensorEK sensorEk : sensorEKList){
				sql = "SELECT * FROM BPM_COGERH10.GRPHASEINSTANCE WHERE IDCUSTOMPHASE = " + sensorEk.getCodigoSensorBizagi();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					/* Aqui vai o código para a criação dos cronômetros. */
					if(cronometroService.buscarCronometroByCodigo(rs.getInt("IDPHASEINSTANCE")) == null) {
						Cronometro cronometro = new Cronometro();
						cronometro.setCodigoInstanciaBizagi(rs.getInt("IDPHASEINSTANCE"));
						cronometro.setCasee(rs.getInt("IDCASE"));
						cronometro.setDataDeInicio(rs.getDate("DATEFROM"));
						cronometro.setDataDeFim(rs.getDate("DATETO"));
						cronometro.setDuracao(rs.getInt("DURATIONMINUTES"));
						cronometro.setSensor(sensor);
						cronometroService.salvarCronometro(cronometro);
					}
					
				}
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conOracle != null){
				try {
					conOracle.fechaConexao();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	public void ajustarDuracaoSensores(Sensor sensor) {
		
		
		try {
			Integer tempo = 0;
			List<Cronometro> cronometroList = cronometroService.listarCronometrosBySensor(sensor);
			for(int i=0; i< cronometroList.size(); i++) {
				Cronometro cronometro = cronometroList.get(i);
				
				if(sensor.getSensorConjuntoList().size() > 1) {    							//se não unitário
					for(SensorConjunto sensorConjunto : sensor.getSensorConjuntoList()) {
						Sensor sensorUnitario = sensorConjunto.getConjunto().getSensorUnitario();
						tempo += cronometroService.obterDuracaoCronometrosByFiltros(sensorUnitario.getId(), cronometro.getCasee(), cronometro.getDataDeInicio(), cronometro.getDataDeFim());
					}
					cronometro.setDuracao(tempo);
					cronometroService.alterarCronometro(cronometro);
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Integer> criarSensorNoBancoDoBizagi(Sensor sensor) {
		// TODO Auto-generated method stub
		ConexaoOracle conOracle = null;
		Statement stmt = null;
		List<Integer> sensorBizIdList = new ArrayList<>();
		
		try {
			conOracle = new ConexaoOracle();
			stmt = conOracle.getConexao().createStatement();
			String sql;
			Integer tarefaFinalIndex = sensor.getSensorConjuntoList().size() - 1;
			Conjunto conjuntoDaTarefaInicial = sensor.getSensorConjuntoList().get(0).getConjunto();
			Conjunto conjuntoDaTarefaFinal = sensor.getSensorConjuntoList().get(tarefaFinalIndex).getConjunto();
			
			int controle = conjuntoDaTarefaInicial.getConjuntoTaskList().size();
			for(int i = 0; i < controle; i++){
				Task tarefaInicial = conjuntoDaTarefaInicial.getConjuntoTaskList().get(i).getTask();
				Task tarefaFinal = conjuntoDaTarefaFinal.getConjuntoTaskList().get(i).getTask();
				Integer sensorId = obterMaiorIdSensorBizagi() + 1;
				sql = "INSERT INTO BPM_COGERH10.GRCUSTOMPHASE( "
						+ "IDCUSTOMPHASE, "
						+ "IDUSER, "
						+ "CPHNAME, "
						+ "CPHDESCRIPTION, "
						+ "IDTASKFROM, "
						+ "IDTASKTO, "
						+ "CPHDISPLAYNAME, "
						+ "CPHSLA )"
						+ " VALUES ("
						+ sensorId + ", "
						+ 1 + ", "
						+ "'" + sensor.getNome() + "', "
						+ "'" + sensor.getDescricao() + "', "
						+ tarefaInicial.getCodigo() + ", "
						+ tarefaFinal.getCodigo() + ", "
						+ "'" + sensor.getNomeDeExibicao() + "', "
						+ 0 + ")";
				
				stmt.executeUpdate(sql);
				sensorBizIdList.add(sensorId);
				
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conOracle != null){
				try {
					conOracle.fechaConexao();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return sensorBizIdList;
		
	}

	@Override
	public Integer obterMaiorIdSensorBizagi() {
		ConexaoOracle conOracle = null;
		Statement stmt = null;
		Integer id = null;
		
		try {
			conOracle = new ConexaoOracle();
			stmt = conOracle.getConexao().createStatement();
			String sql;
			
			sql = "SELECT max(IDCUSTOMPHASE) FROM BPM_COGERH10.GRCUSTOMPHASE";
			
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				id = rs.getInt(1);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conOracle != null){
				try {
					conOracle.fechaConexao();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return id;
	}

	@Override
	public Integer obterDuracaoDeSensorDoBancoDoBizagi(Integer taskIdInicio,
			Integer taskIdFim, Integer casee, Date dataInicial, Date dataFinal) {
		// TODO Auto-generated method stub
		return null;
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
		sb.append(" order by ");
		sb.append(" 	cron.id "); 

		final TypedQuery<Integer> query = entityManager.createQuery(sb.toString(), Integer.class);

		if (sensor != null)
		{
			query.setParameter("sensor", sensor.getId());
		}
		
		return query.getSingleResult();
	}

	@Override
	public Integer obterDuracaoMinima(Sensor sensor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer obterDuracaoMaxima(Sensor sensor) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
