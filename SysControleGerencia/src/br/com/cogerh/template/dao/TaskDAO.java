package br.com.cogerh.template.dao;

import java.util.List;

import br.com.cogerh.template.model.Grupo;
import br.com.cogerh.template.model.Task;
import br.com.cogerh.template.model.UsuarioBizagi;

public interface TaskDAO extends GenericDAO<Task, Integer>
{
	public List<Task> listar(String pesquisa);
	public List<UsuarioBizagi> buscarUsuarios();
	public List<Task> buscarTarefas();
	public List<Task> buscar();
	
	public Task buscarByCodigo(Integer codigo);
	public Integer obterNumeroDeCasePorTarefa(Integer taskCodigo);
	public Integer obterTempoTarefa(Integer taskCodigo);
	
	public Integer criarSensoresNoBancoDoBizagi(List<Task> taskList);
	
}
