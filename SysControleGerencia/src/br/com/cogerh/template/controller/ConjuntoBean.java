package br.com.cogerh.template.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.cogerh.template.enumeration.EnumStatus;
import br.com.cogerh.template.model.Conjunto;
import br.com.cogerh.template.model.ConjuntoTask;
import br.com.cogerh.template.model.Task;
import br.com.cogerh.template.service.ConjuntoService;
import br.com.cogerh.template.service.TaskService;
import br.com.cogerh.template.service.UsuarioService;

@Controller
@Scope("view")
public class ConjuntoBean extends AbstractBean
{
	private Conjunto conjunto = new Conjunto();
	
	private String pesquisa; 

	@Autowired
	private ConjuntoService conjuntoService;
	
	private List<Conjunto> filteredConjuntos;

	@Autowired
	private TaskService taskService;

	private List<Conjunto> conjuntoList = new ArrayList<Conjunto>();

	private DualListModel<Task> taskPickList;
	
	private EnumStatus enumConjunto;
	
	@Autowired
	private UsuarioWeb usuarioWeb;
	
	@Autowired
	private UsuarioService usuarioService;

	@PostConstruct
	public void init()
	{
		try
		{
			this.conjuntoList = conjuntoService.listarConjuntos(null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

    public void initForm(Integer conjuntoId) {
    
    	try
		{
    		
			// Lista de origem do picklist
    		List<Task> taskOrigemList = new ArrayList<Task>();
    		// Lista de destino do picklist
	    	List<Task> taskDestinoList = new ArrayList<Task>();
    		
	        if (null == conjunto || conjuntoId == 0)
	        {
	        	this.viewState = ViewState.NOVO;

	            this.conjunto = new Conjunto();

	            taskOrigemList = this.taskService.listarTasks(null);

		        taskPickList = new DualListModel<Task>(taskOrigemList, taskDestinoList);
	        }
	        else
	        {
	        	this.viewState = ViewState.EDITAR;

	            this.conjunto = this.conjuntoService.buscarPorId(conjuntoId);

	            taskOrigemList = this.taskService.listarTasks(null);

	            for (ConjuntoTask conjuntoTask : this.conjunto.getConjuntoTaskList())
	            {
	            	// Adiciona a lista de destino as permissoes que ja fazem parte do conjunto
	            	taskDestinoList.add(conjuntoTask.getTask());

	            	// Remove as permissoes que ja fazem parte do conjunto
	            	taskOrigemList.remove(conjuntoTask.getTask());
	            }

		        taskPickList = new DualListModel<Task>(taskOrigemList, taskDestinoList);
	        }
    		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    	
    }

	public void salvar()
	{
		try
		{
			for (Task task : taskPickList.getTarget())
			{
				if (null == this.conjunto.getConjuntoTaskList())
					this.conjunto.setConjuntoTaskList(new ArrayList<ConjuntoTask>());

				ConjuntoTask conjuntoTask = new ConjuntoTask();

				conjuntoTask.setTask(task);
				conjuntoTask.setConjunto(this.conjunto);

				this.conjunto.getConjuntoTaskList().add(conjuntoTask);
			}

			conjuntoService.salvarConjunto(this.conjunto);

			this.conjunto = new Conjunto();

			this.adicionaMensagemDeSucesso("Conjunto adicionado com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();

			this.adicionaMensagemDeErro("Ocorreu um erro ao tentar salvar o registro");
		}
	}

	public void listar()
	{
		try
		{
			this.conjuntoList = conjuntoService.listarConjuntos(this.pesquisa);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void remover(Conjunto conjunto)
	{
		try
		{
			conjuntoService.removerConjunto(conjunto);

			this.conjuntoList = conjuntoService.listarConjuntos(null);

			this.adicionaMensagemDeSucesso("Conjunto removido com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();

			this.adicionaMensagemDeErro("Ocorreu um erro ao tentar remover o registro");
		}
	}

	public void alterar()
	{
		try
		{
			for (Task task : taskPickList.getTarget())
			{
				boolean canAdd = true;

				if (null == this.conjunto.getConjuntoTaskList())
					this.conjunto.setConjuntoTaskList(new ArrayList<ConjuntoTask>());

				ConjuntoTask conjuntoTask = new ConjuntoTask();

				conjuntoTask.setTask(task);
				conjuntoTask.setConjunto(this.conjunto);

				// Verifica se a task em questao ja foi adicionada a lista de permissoes do conjunto
				for (ConjuntoTask gruPer : this.conjunto.getConjuntoTaskList())
				{
					if (gruPer.getTask().equals(conjuntoTask.getTask()))
					{
						canAdd = false;

						break;
					}
				}

				if (canAdd)
					this.conjunto.getConjuntoTaskList().add(conjuntoTask);
			}
			
			for (Task task : taskPickList.getSource())
			{
				boolean canRemove = false;

				ConjuntoTask conjuntoTask = new ConjuntoTask();

				/** Verifica se a task em questao foi adicionada ao pickList source
				 *  e precisa ser removida da lista de conjuntoTasks da conjunto
				 **/
				for (ConjuntoTask bacGer : this.conjunto.getConjuntoTaskList())
				{
					if (bacGer.getTask().equals(task))
					{
						canRemove = true;

						conjuntoTask = bacGer;
						
						break;
					}
				}

				if (canRemove)
					this.conjunto.getConjuntoTaskList().remove(conjuntoTask);
			}

			this.conjunto = conjuntoService.alterarConjunto(this.conjunto);
			
			usuarioWeb.setUsuario(this.usuarioService.alterarUsuario(usuarioWeb.getUsuario()));

			this.adicionaMensagemDeSucesso("Conjunto atualizado com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();

			this.adicionaMensagemDeErro("Ocorreu um erro ao tentar atualizar o registro");
		}
	}

	public String showFormNovo() 
	{
        return "novo.xhtml?faces-redirect=true&conjuntoId=0";
    }

	public String showFormListar() 
	{
        return "lista.xhtml?faces-redirect=true";
    }

	public String showFormEditar(Integer conjuntoId) 
	{
		return "novo.xhtml?faces-redirect=true&conjuntoId=" + conjuntoId;
	}

	public Conjunto getConjunto() {return conjunto;}
	public void setConjunto(Conjunto conjunto) {this.conjunto = conjunto;}

	public List<Conjunto> getConjuntoList() {return conjuntoList;}
	public void setConjuntoList(List<Conjunto> conjuntoList) {this.conjuntoList = conjuntoList;}

	public String getPesquisa() {return pesquisa;}
	public void setPesquisa(String pesquisa) {this.pesquisa = pesquisa;}

	public DualListModel<Task> getTaskPickList() {return taskPickList;}
	public void setTaskPickList(DualListModel<Task> taskPickList) {this.taskPickList = taskPickList;}

	public List<Conjunto> getFilteredConjuntos() {
		return filteredConjuntos;
	}

	public void setFilteredConjuntos(List<Conjunto> filteredConjuntos) {
		this.filteredConjuntos = filteredConjuntos;
	}

	public EnumStatus getEnumConjunto() {
		return enumConjunto;
	}

	public void setEnumConjunto(EnumStatus enumConjunto) {
		this.enumConjunto = enumConjunto;
	}

}