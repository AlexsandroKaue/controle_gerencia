package br.com.cogerh.template.service;

import java.util.List;

import br.com.cogerh.template.model.Grupo;
import br.com.cogerh.template.model.Task;

public interface TaskService 
{
	public Task salvarTask(Task task) throws Exception;
	public Task alterarTask(Task task) throws Exception;
	public void removerTask(Task task) throws Exception;
	public Task buscarPorId(Integer id) throws Exception;
	public List<Task> listarTasks(String pesquisa) throws Exception;
	
	public List<Task> listar(String pesquisa);
	public Task atualizarTarefas() throws Exception;
	public List<Task> buscarTarefas();
	
	public Integer obterNumeroDeCasePorTarefa(Integer taskCodigo);
	public Integer obterTempoTarefa(Integer taskCodigo);
	
	public void criarSensoresNoBancoDoBizagi(List<Task> taskList);
}
