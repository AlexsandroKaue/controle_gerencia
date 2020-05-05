package br.com.cogerh.template.service;

import java.util.List;

import br.com.cogerh.template.model.Cronometro;
import br.com.cogerh.template.model.Sensor;

public interface SensorService 
{
	public Sensor salvarSensor(Sensor sensor) throws Exception;
	public Sensor alterarSensor(Sensor sensor) throws Exception;
	public void removerSensor(Sensor sensor) throws Exception;
	public Sensor buscarPorId(Integer id) throws Exception;
	public List<Sensor> listarSensores(String pesquisa) throws Exception;
	
	public Integer obterDuracaoMinima(Sensor sensor) throws Exception;
	public Integer obterDuracaoMaxima(Sensor sensor) throws Exception;
	public Integer obterDuracaoMedia(Sensor sensor) throws Exception;
	public Integer obterDesvioPadrao(Sensor sensor) throws Exception;
	public Integer obterDuracaoTotal(Sensor sensor) throws Exception;
	public Integer obterQuantidadeDeInstancias(Sensor sensor) throws Exception;
	public void obterInstanciasDoSensor(Sensor sensor) throws Exception;
	public void ajustarDuracaoSensores(Sensor sensor) throws Exception;
	
	public void sincronizarInformacoesDosSensores() throws Exception;
	
	public void criarSensorNoBancoDoBizagi(Sensor sensor) throws Exception;
	
}
