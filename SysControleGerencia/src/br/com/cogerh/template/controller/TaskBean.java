package br.com.cogerh.template.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.cogerh.template.dao.TaskDAO;
import br.com.cogerh.template.model.Task;
import br.com.cogerh.template.service.TaskService;

@Controller
@Scope("view")
public class TaskBean extends AbstractBean
{
	private List<String> opcoesList;
    private List<Task> tarefaSelecionada;
    private List<Task> tarefas;
    
    @Autowired
    private TaskService taskService;
    
    @PostConstruct
    public void init() 
    {
    	tarefas = new ArrayList<Task>();
    	
    	tarefas = taskService.buscarTarefas();
    }
 
    
	public List<String> getOpcoesList() {
		return opcoesList;
	}
	public void setOpcoesList(List<String> opcoesList) {
		this.opcoesList = opcoesList;
	}
	public List<Task> getTarefaSelecionada() {
		return tarefaSelecionada;
	}
	public void setTarefaSelecionada(List<Task> tarefaSelecionada) {
		this.tarefaSelecionada = tarefaSelecionada;
	}
	public List<Task> getTarefas() {
		return tarefas;
	}
	public void setTarefas(List<Task> tarefas) {
		this.tarefas = tarefas;
	}
	
}
