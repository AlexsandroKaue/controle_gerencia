package br.com.cogerh.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.SensorDAO;
import br.com.cogerh.template.model.Conjunto;
import br.com.cogerh.template.model.ConjuntoTask;
import br.com.cogerh.template.model.Sensor;
import br.com.cogerh.template.model.SensorConjunto;
import br.com.cogerh.template.model.SensorEK;
import br.com.cogerh.template.model.Task;
import br.com.cogerh.template.service.ConjuntoService;
import br.com.cogerh.template.service.CronometroService;
import br.com.cogerh.template.service.SensorService;
import br.com.cogerh.template.service.TaskService;

@Service
public class SensorServiceImpl implements SensorService
{
	private SensorDAO dao;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CronometroService cronometroService;
	
	@Autowired
	public SensorServiceImpl(SensorDAO dao) {
		this.dao = dao;
	}
	
	@Transactional
	public Sensor salvarSensor(Sensor sensor) throws Exception 
	{
		return dao.save(sensor);
	}

	@Transactional
	public Sensor alterarSensor(Sensor sensor) throws Exception {
		// TODO Auto-generated method stub
		return dao.update(sensor);
	}

	@Transactional
	public void removerSensor(Sensor sensor) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(sensor);
	}

	@Transactional
	public Sensor buscarPorId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return dao.buscarPorId(id);
	}

	@Transactional
	public List<Sensor> listarSensores(String pesquisa) throws Exception {
		// TODO Auto-generated method stub
		return dao.listar(pesquisa);
	}
	
	@Transactional
	public Integer obterTempoMedio(Sensor sensor) throws Exception 
	{
		
		Integer tempoTotal = cronometroService.obterDuracaoTotal(sensor);
		Integer numeroDeInstancias = cronometroService.obterQuantidadeCronometros(sensor);
		Integer tempoMedio = tempoTotal / numeroDeInstancias;
		
		return tempoMedio;
	}
	
	@Transactional
	public Integer obterContagem(Sensor sensor) throws Exception {
		// TODO Auto-generated method stub
		Integer quantidadeDeInstancias = 0;
		
		quantidadeDeInstancias = cronometroService.obterQuantidadeCronometros(sensor);

		return quantidadeDeInstancias;
	}

	@Override
	public Integer obterDuracaoMinima(Sensor sensor) throws Exception {
		// TODO Auto-generated method stub
		Integer tempoMinimo = 0;
		
		tempoMinimo = cronometroService.obterDuracaoMinima(sensor);
		
		return tempoMinimo;
	}

	@Override
	public Integer obterDuracaoMaxima(Sensor sensor) throws Exception {
		Integer tempoMaximo = 0;
		
		tempoMaximo = cronometroService.obterDuracaoMaxima(sensor);
		
		return tempoMaximo;
	}

	@Override
	public Integer obterDuracaoMedia(Sensor sensor) throws Exception 
	{
		Integer tempoTotal = cronometroService.obterDuracaoTotal(sensor);
		Integer numeroDeInstancias = cronometroService.obterQuantidadeCronometros(sensor);
		Integer tempoMedio = tempoTotal / numeroDeInstancias;
		
		return tempoMedio;
	}

	@Override
	public Integer obterDesvioPadrao(Sensor sensor) throws Exception {
		Integer desvioPadrao = 0; 
		
		return desvioPadrao;
	}

	@Override
	public Integer obterQuantidadeDeInstancias(Sensor sensor) throws Exception {
		Integer quantidadeDeInstancias = 0;
		
		quantidadeDeInstancias = cronometroService.obterQuantidadeCronometros(sensor);

		return quantidadeDeInstancias;
	}

	@Override
	public void sincronizarInformacoesDosSensores() throws Exception {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void criarSensorNoBancoDoBizagi(Sensor sensor) throws Exception 
	{
		List<Integer> ids = dao.criarSensorNoBancoDoBizagi(sensor);
		List<SensorEK> sensorEKList = new ArrayList<>();
		SensorEK sensorEK;
		for(Integer id : ids){
			sensorEK = new SensorEK();
			sensorEK.setSensor(sensor);
			sensorEK.setCodigoSensorBizagi(id);
			sensorEKList.add(sensorEK);
		}
		sensor.setSensorEKList(sensorEKList);
		
	}

	@Override
	public void obterInstanciasDoSensor(Sensor sensor) throws Exception 
	{
		dao.obterInstanciasDoSensor(sensor);
	}

	@Override
	public void ajustarDuracaoSensores(Sensor sensor) throws Exception 
	{
		dao.ajustarDuracaoSensores(sensor);
	}

	@Override
	public Integer obterDuracaoTotal(Sensor sensor) throws Exception {
		
		Integer tempoTotal = cronometroService.obterDuracaoTotal(sensor);
		
		return tempoTotal;
	}
	
	

}
