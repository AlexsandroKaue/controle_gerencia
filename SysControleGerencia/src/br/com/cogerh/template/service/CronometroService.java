package br.com.cogerh.template.service;

import java.util.Date;
import java.util.List;

import br.com.cogerh.template.model.Cronometro;
import br.com.cogerh.template.model.Sensor;

public interface CronometroService 
{
	public Cronometro salvarCronometro(Cronometro cronometro) throws Exception;
	public Cronometro alterarCronometro(Cronometro cronometro) throws Exception;
	public void removerCronometro(Cronometro cronometro) throws Exception;
	public Cronometro buscarPorId(Integer id) throws Exception;
	public List<Cronometro> listarCronometros(String pesquisa) throws Exception;
	public Cronometro buscarCronometroByNome(String pesquisa) throws Exception;
	public Cronometro buscarCronometroByCodigo(Integer codigo) throws Exception;
	
	public Integer obterQuantidadeCronometros(Sensor sensor) throws Exception;
	public Integer obterDuracaoMinima(Sensor sensor) throws Exception;
	public Integer obterDuracaoMaxima(Sensor sensor) throws Exception;
	public Integer obterDuracaoMedia(Sensor sensor) throws Exception;
	public Integer obterDesvioPadrao(Sensor sensor) throws Exception;
	public Integer obterDuracaoTotal(Sensor sensor) throws Exception;
	
	public List<Cronometro> listarCronometrosBySensor(Sensor sensor) throws Exception;
	public Integer obterDuracaoCronometrosByFiltros(Integer sensorId, Integer casee, Date dataInicial, Date dataFinal) throws Exception;
}
