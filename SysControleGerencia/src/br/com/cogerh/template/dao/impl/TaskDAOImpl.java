package br.com.cogerh.template.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.TaskDAO;
import br.com.cogerh.template.enumeration.EnumProcesso;
import br.com.cogerh.template.model.Conjunto;
import br.com.cogerh.template.model.ConjuntoTask;
import br.com.cogerh.template.model.Cronometro;
import br.com.cogerh.template.model.Sensor;
import br.com.cogerh.template.model.Task;
import br.com.cogerh.template.model.UsuarioBizagi;
import br.com.cogerh.template.service.CronometroService;
import br.com.cogerh.template.util.ConexaoOracle;
import br.com.cogerh.template.util.HasValue;

@Transactional
@Repository
public class TaskDAOImpl extends GenericDAOImpl<Task, Integer> implements TaskDAO
{
	
	@Autowired
	private CronometroService cronometroService;
	
	public List<Task> listar(String pesquisa)
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(pesquisa))
			cond.append(" and (nome like :pesquisa) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     tsk ");
		sb.append(" from ");
		sb.append(" 	Task tsk ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	tsk.nome "); 

		final TypedQuery<Task> query = entityManager.createQuery(sb.toString(), Task.class);

		if (HasValue.execute(pesquisa))
		{
			query.setParameter("pesquisa", "%" + pesquisa.toUpperCase() + "%");
		}

		return query.getResultList();
		
	}
	
	
	
	
	
	
	
	
	public List<Task> buscarTarefas(Integer workflowId)
	{
		List<Task> taskList = new ArrayList<>();
		ConexaoOracle conOracle = null;
		try 
		{
			//ResultSet versaoDoFluxo = obterTodasAsVersoes(versaoFluxo);
			
			conOracle = new ConexaoOracle();
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql;
			
			sql = 	  " SELECT * " +
					  " FROM BPM_COGERH10.WORKFLOW workflow " +
					  " WHERE workflow.IDWFCLASS = "+ workflowId +
					  " ORDER BY workflow.WFCREATIONDATE DESC";
			
			ResultSet versaoDoFluxo = stmt.executeQuery(sql);
			
			while(versaoDoFluxo.next()) 
			{
				sql = " SELECT * " +
					  " FROM BPM_COGERH10.WORKFLOW workflow, " + //BPM_COGERH10.WORKFLOW contém informações das versões do fluxos.
					  " BPM_COGERH10.TASK tarefa" + 			 //BPM_COGERH10.TASK contém informações de todas as tarefas de todos os fluxos. 

			  		  " WHERE (workflow.IDWORKFLOW = " + versaoDoFluxo.getInt("IDWORKFLOW") +
			  		  " AND tarefa.IDWORKFLOW = workflow.IDWORKFLOW"+
			  		  " AND tarefa.IDTASKTYPE = 2) " +          //tipo 2: tarefa do manual realizada pelo usuário do bizagi
			  		  " ORDER BY tarefa.IDTASK"; 
		
		
				stmt = conOracle.getConexao().createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					Task task = new Task();
					task.setId(rs.getInt("IDTASK"));
					task.setNome(rs.getString("TSKDISPLAYNAME"));
					task.setCodigo(rs.getInt("IDTASK"));
					task.setDuracao(rs.getInt("TSKDURATION"));
					
					taskList.add(task);
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conOracle != null)
					conOracle.fechaConexao();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return taskList;
	}
	
	public Integer criarSensoresNoBancoDoBizagi(List<Task> taskList) 
	{
		
		ConexaoOracle conOracle = null;
		Integer sensorId = obterMaximoIdTabelaDeSensoresDoBizagi()+1;
		
		try 
		{
			conOracle = new ConexaoOracle();
			
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql;
			
			for(Task tarefa : taskList)
			{
				sensorId = obterMaximoIdTabelaDeSensoresDoBizagi()+1;
				
				sql = " INSERT INTO BPM_COGERH10.GRCUSTOMPHASE("
					+ " IDCUSTOMPHASE,"
					+ " IDUSER,"
					+ " CPHNAME,"
					+ " CPHDESCRIPTION,"
					+ " IDTASKFROM,"
					+ " IDTASKTO,"
					+ " CPHDISPLAYNAME,"
					+ " CPHSLA)"
					+ " VALUES ("
					+ sensorId + ", "
					+ 1 + ", "
					+ "'" + tarefa.getNome() + "', "
					+ "'" + tarefa.getNome() + "', "
					+ tarefa.getCodigo() + ", "
					+ tarefa.getCodigo() + ", "
					+ "'" + tarefa.getNome() + "', "
					+ tarefa.getDuracao() + ")";
				
				if(stmt.execute(sql))
				{
					System.out.println("Sensor inserido com sucesso!");
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conOracle != null)
					conOracle.fechaConexao();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sensorId;
		
	}
	
	
	public void atualizarCronometro() {
		
	}
	
	
	
	public Integer obterMaximoIdTabelaDeSensoresDoBizagi()
	{
		Integer maxId = null;
		ConexaoOracle conOracle = null;
		
		try 
		{
			conOracle = new ConexaoOracle();
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql;
			
			sql = "SELECT max(IDCUSTOMPHASE) FROM BPM_COGERH10.GRCUSTOMPHASE";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) 
			{
				maxId = rs.getInt(1);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conOracle != null)
					conOracle.fechaConexao();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return maxId;
		
	}
	
	
	
	
	
	
	
	
	
	
	private ResultSet obterTodasAsVersoes(Integer workflowId)
	{
		ConexaoOracle conOracle = null;
		ResultSet rs = null;
		
		try 
		{
			
			conOracle = new ConexaoOracle();
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql;
			
			sql = " SELECT * " +
					  " FROM BPM_COGERH10.WORKFLOW workflow " +
					  " WHERE workflow.IDWFCLASS = "+ workflowId +
					  " ORDER BY workflow.WFCREATIONDATE DESC";
			
			rs = stmt.executeQuery(sql);
			
			/*while(rs.next())
			{
				versaoMaisRecenteId = rs.getInt("IDWORKFLOW");
			}*/
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conOracle != null)
					conOracle.fechaConexao();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rs;
	}

	
	public List<UsuarioBizagi> buscarUsuarios()
	{
		String sql = null;
		ConexaoOracle conOracle = null;
		List<UsuarioBizagi> usuarioBizagiList = new ArrayList<>();
		UsuarioBizagi usuarioBizagi = null;
		
		try {
			
			conOracle = new ConexaoOracle();
			Statement stmt = conOracle.getConexao().createStatement();
			
			sql = "SELECT * FROM BPM_COGERH10.WFUSER";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			
			while(rs.next())
			{
				usuarioBizagi = new UsuarioBizagi();
				usuarioBizagi.setNome(rs.getString(2));
				usuarioBizagiList.add(usuarioBizagi);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conOracle != null)
					conOracle.fechaConexao();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return usuarioBizagiList;
		
	}

	@Override
	public List<Task> buscar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task buscarByCodigo(Integer codigo) 
	{
		final StringBuilder sb = new StringBuilder();
		final StringBuilder cond = new StringBuilder();

		if (HasValue.execute(codigo))
			cond.append(" and (codigo = :codigo) ");

		if (cond.length() > 4)
			cond.replace(0, 5, " where ");

		sb.append(" select ");
		sb.append("     tsk ");
		sb.append(" from ");
		sb.append(" 	Task tsk ");
		sb.append(  cond );
		sb.append(" order by ");
		sb.append(" 	tsk.codigo "); 

		final TypedQuery<Task> query = entityManager.createQuery(sb.toString(), Task.class);

		if (HasValue.execute(codigo))
		{
			query.setParameter("codigo", codigo);
		}

		try
		{
			return query.getSingleResult();
		} 
		catch(Exception e)
		{
			return null;
		}
		
	}
	
	public Integer obterTempoTarefa(Integer taskCodigo)
	{
		ConexaoOracle conOracle = null;
		Integer tempoTotal = 0;
		Integer tempoMedio = 0;
		
		try {
			conOracle = new ConexaoOracle();
			
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql;
			
			//sql = "SELECT * FROM BPM_COGERH10.WORKITEM W WHERE W.IDTASK = " + taskCodigo + " AND WISOLUTIONDATE is not null ";
			
			sql = "SELECT * FROM BPM_COGERH10.GRPHASEINSTANCE GR WHERE GR.IDCUSTOMPHASE = " + taskCodigo;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				tempoTotal += rs.getInt("DURATIONMINUTES");
			}
			 
			tempoMedio = obterNumeroDeCasePorTarefa(taskCodigo) > 0 ? tempoTotal / obterNumeroDeCasePorTarefa(taskCodigo) : 0;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return tempoMedio;
		
	}
	
	public Long obterTempoMedioPorTarefa()
	{
		ConexaoOracle conOracle = null;
		long tempoTotal = 0;
		long tempoMedio = 0;
		
		try {
			conOracle = new ConexaoOracle();
			
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql;
			
			sql = "SELECT * FROM BPM_COGERH10.WORKITEM order by IDCASE";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				Long diff = (rs.getTimestamp("WISOLUTIONDATE").getTime() - rs.getTimestamp("WIENTRYDATE").getTime()) / 1000;
				tempoTotal += diff;
			}
			 
			//tempoMedio = obterNumeroDeCasePorTarefa(taskCodigo) > 0 ? tempoTotal / obterNumeroDeCasePorTarefa(taskCodigo) : 0;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return tempoMedio;
		
	}
	
	public Integer obterNumeroDeCasePorTarefa(Integer taskCodigo) 
	{
		Integer retorno = null;
		ConexaoOracle conOracle = null;
		
		try {
			conOracle = new ConexaoOracle();
			
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql;
			
			//sql = "SELECT count(*) FROM BPM_COGERH10.WORKITEM W WHERE W.IDTASK = " + taskCodigo + " AND W.WISOLUTIONDATE is not null ";
			
			sql = "SELECT count(*) FROM BPM_COGERH10.GRPHASEINSTANCE GR WHERE GR.IDCUSTOMPHASE = " + taskCodigo;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				retorno = rs.getInt(1);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retorno;
		
	}
	
	public Integer obterNumeroDeInstancias(Integer caseId, Conjunto conjunto) 
	{
		
		Integer retorno = null;
		ConexaoOracle conOracle = null;
		
		try {
			conOracle = new ConexaoOracle();
			
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql="", cond="";
			
			List<ConjuntoTask> conjuntoTaskList = conjunto.getConjuntoTaskList();
			
			//sql = "SELECT count(*) FROM BPM_COGERH10.WORKITEM W WHERE W.IDTASK = " + taskCodigo + " AND W.WISOLUTIONDATE is not null ";
			
			sql = "SELECT count(*) FROM BPM_COGERH10.GRPHASEINSTANCE GR";
			
			if(!conjuntoTaskList.isEmpty())
			{
				Integer lastIndex = conjuntoTaskList.size() - 1;
				cond = " WHERE (";
				
				for(int i=0; i<conjuntoTaskList.size()-1; i++)
				{
					cond += " GR.IDCUSTOMPHASE = " + conjuntoTaskList.get(i).getTask().getCodigo() + " OR ";					
				}
				
				cond += " GR.IDCUSTOMPHASE = " + conjuntoTaskList.get(lastIndex).getTask().getCodigo() + ")";
			}
			
			sql += cond;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				retorno = rs.getInt(1);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return retorno;
	}
	
	public Integer obterInstanciasDoSensor(Sensor sensor, Integer idCase, List<Task> tarefas) 
	{
		
		ConexaoOracle conOracle = null;
		/*Integer idCase = 0;
		Integer idTask = 0;*/
		Integer inicioIntervalo = 0;
		Integer fimIntervalo = 0;
		Integer idtaskFinal = tarefas.get(tarefas.size() - 1).getCodigo();
		
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
				try {
					cronometroService.salvarCronometro(cronometro);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				inicioIntervalo = fimIntervalo + 1;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return 0;
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
	
	public static void main(String args[]) 
	{
		//new TaskDAOImpl().buscarTarefas("1.0");
		
		new TaskDAOImpl().criarSensoresNoBancoDoBizagi(new ArrayList<Task>());
		
		/*System.out.println(new TaskDAOImpl().obterTempoMedioPorTarefa(858));
		
		
		//System.out.println("IDCASE\t" + "IDTASK\t" + "WIENTRYDATE\t" + "WISOLUTIONDATE");
		System.out.format("%50s%50s%50s%50s", "IDTASK", "TSKNAME", "TSKDURATION", "WISOLUTIONDATE");
		for(Task task : taskList)
		{
			System.out.println();
			System.out.format("%50s%50s%50s", task.getId(), task.getNome(), task.getDuracao());
		}*/
		
		//new TaskDAOImpl().obterVersaoMaisRecente();
		/*List<UsuarioBizagi> usuarioList = buscarUsuarios();
		for(UsuarioBizagi usuarioBizagi : usuarioList)
		{
			System.out.println();
			System.out.format("%50s", usuarioBizagi.getNome());
		}*/
		
	}

	@Override
	public List<Task> buscarTarefas() {
		// TODO Auto-generated method stub
		List<Task> listaDeTarefas = new ArrayList<>();
		
		listaDeTarefas.addAll(buscarTarefas(EnumProcesso.Solicitacao_de_Outorga.getId()));
		return listaDeTarefas;
	}
}
