package br.com.cogerh.template.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.cogerh.template.model.Permissao;
import br.com.cogerh.template.service.PermissaoService;

@Controller
@Scope("view")
public class PermissaoBean extends AbstractBean
{
	private Permissao permissao = new Permissao();
	private String pesquisa;

	@Autowired
	private PermissaoService permissaoService;

	private List<Permissao> permissaoList = new ArrayList<Permissao>();

	@PostConstruct
	public void init()
	{
		try
		{
			this.permissaoList = permissaoService.listarPermissoes(null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

    public void initForm(Integer permissaoId) 
    {
    	try
		{
	        if (null == permissao || permissaoId == 0)
	        {
	        	this.viewState = ViewState.NOVO;

	            this.permissao = new Permissao();
	        }
	        else
	        {
	        	this.viewState = ViewState.EDITAR;

	            this.permissao = this.permissaoService.buscarPorId(permissaoId);
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
			permissaoService.salvarPermissao(this.permissao);

			this.permissao = new Permissao();

			this.adicionaMensagemDeSucesso("Permissão adicionado com sucesso");
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
			this.permissaoList = permissaoService.listarPermissoes(this.pesquisa);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void remover(Permissao permissao)
	{
		try
		{
			permissaoService.removerPermissao(permissao);

			this.permissaoList = permissaoService.listarPermissoes(null);

			this.adicionaMensagemDeSucesso("Permissão removido com sucesso");
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
			this.permissao = permissaoService.alterarPermissao(this.permissao);

			this.adicionaMensagemDeSucesso("Permissão atualizado com sucesso");
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

	public String showFormEditar(Integer permissaoId) 
	{
		return "novo.xhtml?faces-redirect=true&permissaoId=" + permissaoId;
	}

	public Permissao getPermissao() {return permissao;}
	public void setPermissao(Permissao permissao) {this.permissao = permissao;}

	public List<Permissao> getPermissaoList() {return permissaoList;}
	public void setPermissaoList(List<Permissao> permissaoList) {this.permissaoList = permissaoList;}

	public String getPesquisa() {return pesquisa;}
	public void setPesquisa(String pesquisa) {this.pesquisa = pesquisa;}
}