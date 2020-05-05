package br.com.cogerh.template.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.cogerh.template.enumeration.EnumLotacao;
import br.com.cogerh.template.model.Deslocamento;
import br.com.cogerh.template.service.DeslocamentoService;

@Controller
@Scope("view")
public class DeslocamentoBean extends AbstractBean
{
	private Deslocamento deslocamento;
	
	@Autowired
	private DeslocamentoService deslocamentoService;
	
	@Autowired
	private UsuarioWeb usuarioWeb;
	
	private List<Deslocamento> deslocamentoList = new ArrayList<Deslocamento>();
	
	private String pesquisa; 
	
	private List<Deslocamento> filteredDeslocamentos;
	
	private Date dataInicioFILTRO;
	
	private Date dataFimFILTRO;
	
	private EnumLotacao gerenciaFILTRO;
	
	private Date dtMin = new Date();
	
	private String permanencia;
	
	private boolean buscaRealizada = false;
	
	private String periodo = "";
	
	private String tempoMedio = "";
	
	@PostConstruct
	public void init()
	{
		try
		{
			
			this.deslocamentoList = deslocamentoService.listar(null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void listar()
	{
		try
		{
			this.deslocamentoList = deslocamentoService.listarByFiltros(pesquisa, dataInicioFILTRO, dataFimFILTRO, gerenciaFILTRO != null?gerenciaFILTRO.getId():null);
			buscaRealizada = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void limparFiltros() {
		this.gerenciaFILTRO = null;
		this.dataInicioFILTRO = null;
		this.dataFimFILTRO = null;
		this.pesquisa = null;
		listar();
	}
	
	// Ajusta o calendario para não aceitar datas anteriores a data de inicio da
	// diaria
	public void ajustaDataFinal() {
		setDtMin(dataInicioFILTRO);
	}

	public Deslocamento getDeslocamento() {
		return deslocamento;
	}

	public void setDeslocamento(Deslocamento deslocamento) {
		this.deslocamento = deslocamento;
	}

	public List<Deslocamento> getDeslocamentoList() {
		return deslocamentoList;
	}

	public void setDeslocamentoList(List<Deslocamento> deslocamentoList) {
		this.deslocamentoList = deslocamentoList;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public List<Deslocamento> getFilteredDeslocamentos() {
		return filteredDeslocamentos;
	}

	public void setFilteredDeslocamentos(List<Deslocamento> filteredDeslocamentos) {
		this.filteredDeslocamentos = filteredDeslocamentos;
	}

	public Date getDataInicioFILTRO() {
		return dataInicioFILTRO;
	}

	public void setDataInicioFILTRO(Date dataInicioFILTRO) {
		this.dataInicioFILTRO = dataInicioFILTRO;
	}

	public EnumLotacao getGerenciaFILTRO() {
		return gerenciaFILTRO;
	}

	public void setGerenciaFILTRO(EnumLotacao gerenciaFILTRO) {
		this.gerenciaFILTRO = gerenciaFILTRO;
	}
	
	public EnumLotacao[] getStatus() {
        return EnumLotacao.values();
    }

	public Date getDataFimFILTRO() {
		return dataFimFILTRO;
	}

	public void setDataFimFILTRO(Date dataFimFILTRO) {
		this.dataFimFILTRO = dataFimFILTRO;
	}

	public Date getDtMin() {
		return dtMin;
	}

	public void setDtMin(Date dtMin) {
		this.dtMin = dtMin;
	}

	public String getPermanencia() {
		long milliseconds = Long.parseLong(permanencia);
		int seconds = (int) (milliseconds / 1000) % 60 ;
		int minutes = (int) ((milliseconds / (1000*60)) % 60);
		int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
		int days = (int) (milliseconds / (1000*60*60*24));
		return days+" dias, "+hours+" horas, "+minutes+" min, "+seconds+" seg";
	}

	public void setPermanencia(String permanencia) {
		this.permanencia = permanencia;
	}
	
	public String format(Deslocamento deslocamento) {
		String retorno = "-";
		
		
		if(deslocamento != null && deslocamento.getTimestampSaida() != null){
				Long permanencia = deslocamento.getPermanencia();
				
				retorno = formateTempo(permanencia);
		}
		
		return retorno;
	}
	
	public String formateTempo(Long tempo) {
		
		String retorno = "-";
		
		Long permanencia = tempo;
		long milliseconds = permanencia;
		int seconds = (int) (milliseconds / 1000) % 60 ;
		int minutes = (int) ((milliseconds / (1000*60)) % 60);
		int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
		int days = (int) (milliseconds / (1000*60*60*24));
		retorno = days+" dias, "+hours+" horas e "+minutes+" minutos";
		
		return retorno;
	}
	
	public Long somaPermanencia()
	{
		Long total = 0L;
		for(Deslocamento deslocamento : deslocamentoList)
		{
			if(deslocamento.getPermanencia() != null)
				total += deslocamento.getPermanencia();
		}
		
		return total;
	}

	public boolean isBuscaRealizada() {
		return buscaRealizada;
	}

	public void setBuscaRealizada(boolean buscaRealizada) {
		this.buscaRealizada = buscaRealizada;
	}

	public String getPeriodo() 
	{
		if(dataInicioFILTRO != null && dataFimFILTRO != null) {
			periodo = new SimpleDateFormat("dd/MM/yyyy").format(dataInicioFILTRO) + " a " +  new SimpleDateFormat("dd/MM/yyyy").format(dataFimFILTRO);
		}
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getTempoMedio() {
		Long total = somaPermanencia();
		Long media = 0L;
		if(deslocamentoList.size() > 0)
			media = (total / deslocamentoList.size());
		tempoMedio = formateTempo(media);
		return tempoMedio;
	}

	public void setTempoMedio(String tempoMedio) {
		this.tempoMedio = tempoMedio;
	}
	
}
