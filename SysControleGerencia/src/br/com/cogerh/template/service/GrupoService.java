package br.com.cogerh.template.service;

import java.util.List;

import br.com.cogerh.template.model.Grupo;

public interface GrupoService
{
	public Grupo salvarGrupo(Grupo grupo) throws Exception;
	public Grupo alterarGrupo(Grupo grupo) throws Exception;
	public void removerGrupo(Grupo grupo) throws Exception;
	public Grupo buscarPorId(Integer id) throws Exception;
	public List<Grupo> listarGrupos(String pesquisa) throws Exception;
	public Grupo buscarGrupoByNome(String pesquisa) throws Exception;

}