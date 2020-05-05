package br.com.cogerh.template.dao;

import java.util.Date;
import java.util.List;

import br.com.cogerh.template.model.Cronometro;
import br.com.cogerh.template.model.Sensor;

public interface CronometroDAO extends GenericDAO<Cronometro, Integer>
{
	public List<Cronometro> listar(String pesquisa);
	
	public List<Cronometro> listarCronometrosBySensor(Sensor sensor);
	
	public Cronometro buscarCronometroByCodigo(Integer codigoId);

	public Integer obterQuantidadeCronometros(Sensor sensor);

	public Integer obterDuracaoMinima(Sensor sensor);

	public Integer obterDuracaoMaxima(Sensor sensor);
	
	public Integer obterDuracaoTotal(Sensor sensor);
	
	public Integer obterDuracaoCronometrosByFiltros(Integer sensorId, Integer casee, Date dataInicial, Date dataFinal);
}
