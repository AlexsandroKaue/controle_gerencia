package br.com.cogerh.template.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.CronometroDAO;
import br.com.cogerh.template.model.Cronometro;
import br.com.cogerh.template.model.Sensor;
import br.com.cogerh.template.service.CronometroService;

@Service
public class CronometroServiceImpl implements CronometroService
{
	
	private CronometroDAO dao;

	@Autowired
	public CronometroServiceImpl(CronometroDAO dao) 
	{
		this.dao = dao;
	}

	@Transactional
	public Cronometro salvarCronometro(Cronometro cronometro) throws Exception {
		// TODO Auto-generated method stub
		return dao.save(cronometro);
	}

	@Transactional
	public Cronometro alterarCronometro(Cronometro cronometro) throws Exception {
		// TODO Auto-generated method stub
		return dao.update(cronometro);
	}

	@Transactional
	public void removerCronometro(Cronometro cronometro) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(cronometro);
	}

	@Transactional
	public Cronometro buscarPorId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return dao.buscarPorId(id);
	}

	@Transactional
	public List<Cronometro> listarCronometros(String pesquisa) throws Exception 
	{
		return dao.listar(pesquisa);
	}

	@Transactional
	public Cronometro buscarCronometroByNome(String pesquisa) throws Exception 
	{
		return null;
	}

	@Transactional
	public Integer obterQuantidadeCronometros(Sensor sensor) throws Exception 
	{
		return dao.obterQuantidadeCronometros(sensor);
	}

	@Transactional
	public Integer obterDuracaoMinima(Sensor sensor) throws Exception 
	{
		return dao.obterDuracaoMinima(sensor);
	}

	@Transactional
	public Integer obterDuracaoMaxima(Sensor sensor) throws Exception 
	{
		return dao.obterDuracaoMaxima(sensor);
	}

	@Transactional
	public Integer obterDuracaoMedia(Sensor sensor) throws Exception 
	{

		List<Cronometro> cronometroList = dao.listarCronometrosBySensor(sensor);
		
		Long tempoTotal = 0L;
		for(Cronometro cronometro : cronometroList)
		{
			tempoTotal += cronometro.getDuracao();
		}
		
		String tempoMedio = Long.toString((tempoTotal / cronometroList.size()));
		
		return null;
	}

	@Transactional
	public Integer obterDesvioPadrao(Sensor sensor) throws Exception 
	{

		List<Cronometro> cronometroList = dao.listarCronometrosBySensor(sensor);
		
		//calculo do desvio padrao 
		
		String desvioPadrao = null; //
		return null;
	}

	@Override
	public List<Cronometro> listarCronometrosBySensor(Sensor sensor)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.listarCronometrosBySensor(sensor);
	}

	@Override
	public Cronometro buscarCronometroByCodigo(Integer codigo) throws Exception {
		
		return dao.buscarCronometroByCodigo(codigo);
	}

	@Override
	public Integer obterDuracaoCronometrosByFiltros(Integer sensorId,
			Integer casee, Date dataInicial, Date dataFinal) throws Exception {
		// TODO Auto-generated method stub
		return dao.obterDuracaoCronometrosByFiltros(sensorId, casee, dataInicial, dataFinal);
	}

	@Override
	public Integer obterDuracaoTotal(Sensor sensor) throws Exception {
		// TODO Auto-generated method stub
		return dao.obterDuracaoTotal(sensor);
	}
	
}
