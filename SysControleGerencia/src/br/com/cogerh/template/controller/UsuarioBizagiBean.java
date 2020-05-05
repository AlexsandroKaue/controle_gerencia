package br.com.cogerh.template.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.cogerh.template.model.UsuarioBizagi;
import br.com.cogerh.template.service.UsuarioBizagiService;

@Controller
@Scope("view")
public class UsuarioBizagiBean extends AbstractBean
{

	@Autowired
	private UsuarioBizagiService usuarioBizagiService;
	
	private UsuarioBizagi usuarioBizagi;
	
	private List<UsuarioBizagi> usuarioBizagiList = new ArrayList<UsuarioBizagi>();
	
	@PostConstruct
	public void init()
	{
		try
		{
			this.usuarioBizagiList = usuarioBizagiService.buscarUsuarios();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public UsuarioBizagi getUsuarioBizagi() {
		return usuarioBizagi;
	}

	public void setUsuarioBizagi(UsuarioBizagi usuarioBizagi) {
		this.usuarioBizagi = usuarioBizagi;
	}

	public List<UsuarioBizagi> getUsuarioBizagiList() {
		return usuarioBizagiList;
	}

	public void setUsuarioBizagiList(List<UsuarioBizagi> usuarioBizagiList) {
		this.usuarioBizagiList = usuarioBizagiList;
	}
}
