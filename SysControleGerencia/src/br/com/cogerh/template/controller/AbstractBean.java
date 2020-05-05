package br.com.cogerh.template.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Bean utilizado para auxiliar os outros beans da aplicacao
 */
public abstract class AbstractBean implements Serializable
{
	protected ViewState viewState;
	
	
	public void adicionaMensagemDeErro(String mensagem)
	{
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
		getContext().addMessage(null, facesMessage);
	}
	
	public void adicionaMensagemDeSucesso(String mensagem)
	{
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
		getContext().addMessage(null, facesMessage);
	}
	
	public FacesContext getContext() 
	{
		FacesContext context = FacesContext.getCurrentInstance();
	   
		return context;
    }

	/**
     * Enum para controle do estado de execucao da tela
     */
    protected enum ViewState 
    {
        NOVO,
        LISTA,
        SALVAR,
        EDITAR,
        DELETAR;
    }

	public ViewState getViewState() {return viewState;}
}