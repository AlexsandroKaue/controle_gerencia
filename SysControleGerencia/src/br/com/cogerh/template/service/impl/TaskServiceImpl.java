package br.com.cogerh.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.TaskDAO;
import br.com.cogerh.template.model.Task;
import br.com.cogerh.template.service.TaskService;

@Service
public class TaskServiceImpl  implements TaskService
{

	private TaskDAO dao;
	
	/*@Autowired
	private GrupoTaskService grupoTaskService;*/
	
	@Autowired
	public TaskServiceImpl(TaskDAO dao) 
	{
		this.dao = dao;
	}

	@Transactional
	public List<Task> buscar(Integer idCase) {
		// TODO Auto-generated method stub
		return dao.listar(null);
	}

	@Override
	public Task salvarTask(Task task) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task alterarTask(Task task) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removerTask(Task task) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Task buscarPorId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> listarTasks(String pesquisa) throws Exception {
		// TODO Auto-generated method stub
		return dao.listar(pesquisa);
	}

	@Override
	public Task atualizarTarefas() throws Exception 
	{
		List<Task> taskListFromBizagiDatabase = dao.buscarTarefas();
		
		List<Task> novasTarefasList = new ArrayList<>();
		
		for(Task tskFromBizagiDatabase : taskListFromBizagiDatabase)
		{
			if(dao.buscarByCodigo(tskFromBizagiDatabase.getCodigo()) == null)
			{
				
				Task task = new Task();
				task.setCasee(null);
				task.setCodigo(tskFromBizagiDatabase.getCodigo());
				task.setEntrada(tskFromBizagiDatabase.getEntrada());
				task.setNome(tskFromBizagiDatabase.getNome());
				task.setPermanencia(null);
				task.setSaida(tskFromBizagiDatabase.getSaida());
				task.setDuracao(tskFromBizagiDatabase.getDuracao());
				
				dao.save(task);
				
				/*GrupoTask grupoTask = grupoTaskService.buscarGrupoTaskByNome(task.getNome());
				
				if(grupoTask == null)
				{
					grupoTask = new GrupoTask();
					grupoTask.setNome(task.getNome());
					List<Task> taskList = new ArrayList<>();
					taskList.add(task);
					grupoTask.setTaskList(taskList);
				} else 
				{
					grupoTask.getTaskList().add(task);
				}*/
				
				novasTarefasList.add(task);
			}
		}
		
		criarSensoresNoBancoDoBizagi(novasTarefasList);
		
		//agruparTarefasEmGrupo();
		
		return null;
	}
	
	
	public void agruparTarefasEmGrupo() 
	{
		List<Task> taskList = dao.listar(null);
		
		for(int i=0; i<taskList.size(); i++)
		{
			taskList.get(i);
		}
	}
	

	@Override
	public List<Task> listar(String pesquisa) {
		// TODO Auto-generated method stub
		return dao.listar(pesquisa);
	}

	@Override
	public List<Task> buscarTarefas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer obterNumeroDeCasePorTarefa(Integer taskCodigo) {
		return dao.obterNumeroDeCasePorTarefa(taskCodigo);
	}

	@Override
	public Integer obterTempoTarefa(Integer taskCodigo) {
		// TODO Auto-generated method stub
		return dao.obterTempoTarefa(taskCodigo);
	}
	
	public Integer obterTempoMinimo() {
		Integer retorno = null;
		
		return retorno;
	}

	@Override
	public void criarSensoresNoBancoDoBizagi(List<Task> taskList) {
		// TODO Auto-generated method stub
		dao.criarSensoresNoBancoDoBizagi(taskList);
	}
	

}
