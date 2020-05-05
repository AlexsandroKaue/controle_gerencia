package br.com.cogerh.template.service;

import java.util.Date;
import java.util.List;

import br.com.cogerh.template.model.Sensor;
import br.com.cogerh.template.model.Task;

public interface ConsultaService 
{
	public Integer obterVersaoMaisRecente();
	
	public Integer obterInstanciasDeCronometrosDoSensor(Sensor sensor);
	
	public Long calcularTempoCronometro(Integer idCase, Integer inicio, Integer fim, Sensor sensor);
	
	public List<Integer> obterCasesByData(Date dataDeCriacaoSensor);
}
