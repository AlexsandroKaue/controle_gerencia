package br.com.cogerh.template.dao;

import java.util.Date;
import java.util.List;

import br.com.cogerh.template.model.Conjunto;
import br.com.cogerh.template.model.Cronometro;
import br.com.cogerh.template.model.Sensor;
import br.com.cogerh.template.model.Task;

public interface SensorDAO extends GenericDAO<Sensor, Integer>
{
	public List<Sensor> listar(String pesquisa); 
	
	public Integer obterDuracaoMinima(Sensor sensor);
	
	public Integer obterDuracaoMaxima(Sensor sensor);
	
	public Integer obterQuantidadeDeInstancias(Sensor sensor);
	
	public Integer obterDuracaoTotal(Sensor sensor);
	
	public void obterInstanciasDoSensor(Sensor sensor);
	
	public void ajustarDuracaoSensores(Sensor sensor);
	
	public List<Integer> criarSensorNoBancoDoBizagi(Sensor sensor);
	
	public Integer obterMaiorIdSensorBizagi();
	
	public Integer obterDuracaoDeSensorDoBancoDoBizagi(Integer taskIdInicio, Integer taskIdFim, Integer casee, Date dataInicial, Date dataFinal);
}
