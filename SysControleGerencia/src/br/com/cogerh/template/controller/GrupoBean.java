package br.com.cogerh.template.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.cogerh.template.enumeration.EnumAtivo;
import br.com.cogerh.template.enumeration.EnumStatus;
import br.com.cogerh.template.model.GrupoPermissao;
import br.com.cogerh.template.model.Permissao;
import br.com.cogerh.template.model.Grupo;
import br.com.cogerh.template.model.GrupoPermissao;
import br.com.cogerh.template.model.Permissao;
import br.com.cogerh.template.service.GrupoService;
import br.com.cogerh.template.service.PermissaoService;
import br.com.cogerh.template.service.UsuarioService;

@Controller
@Scope("view")
public class GrupoBean extends AbstractBean
{
	private Grupo grupo = new Grupo();
	private String pesquisa; 

	@Autowired
	private GrupoService grupoService;
	
	private List<Grupo> filteredGrupos;

	@Autowired
	private PermissaoService permissaoService;

	private List<Grupo> grupoList = new ArrayList<Grupo>();

	private DualListModel<Permissao> permissaoPickList;
	
	private EnumStatus enumGrupo;
	
	@Autowired
	private UsuarioWeb usuarioWeb;
	
	@Autowired
	private UsuarioService usuarioService;

	@PostConstruct
	public void init()
	{
		try
		{
			this.grupoList = grupoService.listarGrupos(null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

    public void initForm(Integer grupoId) {
    
    	try
		{
    		
			// Lista de origem do picklist
    		List<Permissao> permissaoOrigemList = new ArrayList<Permissao>();
    		// Lista de destino do picklist
	    	List<Permissao> permissaoDestinoList = new ArrayList<Permissao>();
    		
	        if (null == grupo || grupoId == 0)
	        {
	        	this.viewState = ViewState.NOVO;

	            this.grupo = new Grupo();

	            permissaoOrigemList = this.permissaoService.listarPermissoes(null);

		        permissaoPickList = new DualListModel<Permissao>(permissaoOrigemList, permissaoDestinoList);
	        }
	        else
	        {
	        	this.viewState = ViewState.EDITAR;

	            this.grupo = this.grupoService.buscarPorId(grupoId);

	            permissaoOrigemList = this.permissaoService.listarPermissoes(null);

	            for (GrupoPermissao grupoPermissao : this.grupo.getGrupoPermissaoList())
	            {
	            	// Adiciona a lista de destino as permissoes que ja fazem parte do grupo
	            	permissaoDestinoList.add(grupoPermissao.getPermissao());

	            	// Remove as permissoes que ja fazem parte do grupo
	            	permissaoOrigemList.remove(grupoPermissao.getPermissao());
	            }

		        permissaoPickList = new DualListModel<Permissao>(permissaoOrigemList, permissaoDestinoList);
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
			for (Permissao permissao : permissaoPickList.getTarget())
			{
				if (null == this.grupo.getGrupoPermissaoList())
					this.grupo.setGrupoPermissaoList(new ArrayList<GrupoPermissao>());

				GrupoPermissao grupoPermissao = new GrupoPermissao();

				grupoPermissao.setPermissao(permissao);
				grupoPermissao.setGrupo(this.grupo);

				this.grupo.getGrupoPermissaoList().add(grupoPermissao);
			}

			grupoService.salvarGrupo(this.grupo);

			this.grupo = new Grupo();

			this.adicionaMensagemDeSucesso("Grupo adicionado com sucesso");
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
			this.grupoList = grupoService.listarGrupos(this.pesquisa);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void remover(Grupo grupo)
	{
		try
		{
			grupoService.removerGrupo(grupo);

			this.grupoList = grupoService.listarGrupos(null);

			this.adicionaMensagemDeSucesso("Grupo removido com sucesso");
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
			for (Permissao permissao : permissaoPickList.getTarget())
			{
				boolean canAdd = true;

				if (null == this.grupo.getGrupoPermissaoList())
					this.grupo.setGrupoPermissaoList(new ArrayList<GrupoPermissao>());

				GrupoPermissao grupoPermissao = new GrupoPermissao();

				grupoPermissao.setPermissao(permissao);
				grupoPermissao.setGrupo(this.grupo);

				// Verifica se a permissao em questao ja foi adicionada a lista de permissoes do grupo
				for (GrupoPermissao gruPer : this.grupo.getGrupoPermissaoList())
				{
					if (gruPer.getPermissao().equals(grupoPermissao.getPermissao()))
					{
						canAdd = false;

						break;
					}
				}

				if (canAdd)
					this.grupo.getGrupoPermissaoList().add(grupoPermissao);
			}
			
			for (Permissao permissao : permissaoPickList.getSource())
			{
				boolean canRemove = false;

				GrupoPermissao grupoPermissao = new GrupoPermissao();

				/** Verifica se a permissao em questao foi adicionada ao pickList source
				 *  e precisa ser removida da lista de grupoPermissaos da grupo
				 **/
				for (GrupoPermissao bacGer : this.grupo.getGrupoPermissaoList())
				{
					if (bacGer.getPermissao().equals(permissao))
					{
						canRemove = true;

						grupoPermissao = bacGer;
						
						break;
					}
				}

				if (canRemove)
					this.grupo.getGrupoPermissaoList().remove(grupoPermissao);
			}

			this.grupo = grupoService.alterarGrupo(this.grupo);
			
			usuarioWeb.setUsuario(this.usuarioService.alterarUsuario(usuarioWeb.getUsuario()));

			this.adicionaMensagemDeSucesso("Grupo atualizado com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();

			this.adicionaMensagemDeErro("Ocorreu um erro ao tentar atualizar o registro");
		}
	}

	public String showFormNovo() 
	{
        return "novo.xhtml?faces-redirect=true";
    }

	public String showFormListar() 
	{
        return "lista.xhtml?faces-redirect=true";
    }

	public String showFormEditar(Integer grupoId) 
	{
		return "novo.xhtml?faces-redirect=true&grupoId=" + grupoId;
	}

	public Grupo getGrupo() {return grupo;}
	public void setGrupo(Grupo grupo) {this.grupo = grupo;}

	public List<Grupo> getGrupoList() {return grupoList;}
	public void setGrupoList(List<Grupo> grupoList) {this.grupoList = grupoList;}

	public String getPesquisa() {return pesquisa;}
	public void setPesquisa(String pesquisa) {this.pesquisa = pesquisa;}

	public DualListModel<Permissao> getPermissaoPickList() {return permissaoPickList;}
	public void setPermissaoPickList(DualListModel<Permissao> permissaoPickList) {this.permissaoPickList = permissaoPickList;}

	public List<Grupo> getFilteredGrupos() {
		return filteredGrupos;
	}

	public void setFilteredGrupos(List<Grupo> filteredGrupos) {
		this.filteredGrupos = filteredGrupos;
	}

	public EnumStatus getEnumGrupo() {
		return enumGrupo;
	}

	public void setEnumGrupo(EnumStatus enumGrupo) {
		this.enumGrupo = enumGrupo;
	}

}