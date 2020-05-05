package br.com.cogerh.template.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cogerh.template.enumeration.EnumProcesso;
import br.com.cogerh.template.model.Cronometro;
import br.com.cogerh.template.model.Sensor;
import br.com.cogerh.template.model.SensorConjunto;
import br.com.cogerh.template.service.ConsultaService;
import br.com.cogerh.template.service.CronometroService;
import br.com.cogerh.template.util.ConexaoOracle;

@Service
public class ConsultaServiceImpl implements ConsultaService
{
	@Autowired
	private CronometroService cronometroService;

	public Integer obterVersaoMaisRecente()
	{
		Integer versaoMaisRecenteId = null;
		ConexaoOracle conOracle = null;
		
		try 
		{
			
			conOracle = new ConexaoOracle();
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql;
			
			sql = " SELECT * " +
				  " FROM BPM_COGERH10.WORKFLOW workflow " +
				  " WHERE workflow.IDWFCLASS = "+ EnumProcesso.Solicitacao_de_Outorga.getId() +
				  " ORDER BY workflow.WFCREATIONDATE DESC";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				versaoMaisRecenteId = rs.getInt("IDWORKFLOW");
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
		
		return versaoMaisRecenteId;
	}
	
	public Integer obterInstanciasDeCronometrosDoSensor(Sensor sensor) 
	{
		
		/*ConexaoOracle conOracle = null;
		Integer inicioIntervalo = 0;
		Integer fimIntervalo = 0;
		
		List<SensorConjunto> sensorTaskList = sensor.getSensorConjuntoList();
		
		Integer idtaskFinal = sensorTaskList.get(sensorTaskList.size()-1);
		
		try {
			conOracle = new ConexaoOracle();
			
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql;
			
			List<Integer> casesIdList = obterCasesByData(new Date());
			
			for(Integer caseId : casesIdList)
			{
				sql = 	"	SELECT * "
					  + " 	FROM BPM_COGERH10.WORKITEM "
					  + "	W WHERE (W.IDCASE = " + caseId + " AND W.IDTASK = " + idtaskFinal + " AND W.WISOLUTIONDATE is not null) "
					  + "	ORDER BY W.WISOLUTIONDATE ";
				
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					fimIntervalo = rs.getInt("IDWORKITEM");
					Cronometro cronometro = new Cronometro();
					cronometro.setSensor(sensor);
					cronometro.setDuracao( calcularTempoCronometro(caseId, inicioIntervalo, fimIntervalo, sensor) );
					cronometroService.salvarCronometro(cronometro);
					inicioIntervalo = fimIntervalo + 1;
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
			try {
				if(conOracle != null)
					conOracle.fechaConexao();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0;*/
		return 0;
	}
	
	public Long calcularTempoCronometro(Integer idCase, Integer inicio, Integer fim, Sensor sensor) 
	{

		ConexaoOracle conOracle = null;
		Long timeCounter = 0L;
		String cond = "";
		/*List<SensorTask> sensorTaskList = null;
		
		try {
			
			conOracle = new ConexaoOracle();
			
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql = "";
			
			sql =  " SELECT * "
				 + " FROM BPM_COGERH10.WORKITEM"
				 + " W WHERE (W.IDWORKITEM >= " + inicio + "AND W.IDWORKITEM <= " + fim + " AND W.IDCASE = " + idCase;
			
			sensorTaskList = sensor.getSensorTaskList();
			
			if(!sensorTaskList.isEmpty())
			{
				cond = " AND (";
				
				for(int i=0; i<sensorTaskList.size()-1; i++)
				{
					cond += " W.IDTASK = " + sensorTaskList.get(i).getTask().getCodigo() + " OR ";					
				}
				
				cond += " W.IDTASK = " + sensorTaskList.get(sensorTaskList.size()-1).getTask().getCodigo() + ")";
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
		} finally {
			try {
				if(conOracle != null)
					conOracle.fechaConexao();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		return timeCounter;
	}
	
	public List<Integer> obterCasesByData(Date dataDeCriacaoSensor) 
	{
		
		ConexaoOracle conOracle = null;
		List<Integer> casesIdList = new ArrayList<>();
		
		try {
			conOracle = new ConexaoOracle();
			
			Statement stmt = conOracle.getConexao().createStatement();
			
			String sql = "";
			
			sql =  " SELECT * "
				 + " FROM BPM_COGERH10.WFCASE W"
				 + " WHERE W.CASCREATIONDATE >= ";
			
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				casesIdList.add(rs.getInt("IDCASE"));
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
		
		return casesIdList;
	}
	
}
