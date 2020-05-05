package br.com.cogerh.template.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.cogerh.template.model.Conjunto;
import br.com.cogerh.template.model.Cronometro;
import br.com.cogerh.template.model.Sensor;
import br.com.cogerh.template.model.SensorConjunto;
import br.com.cogerh.template.model.SensorUnitario;
import br.com.cogerh.template.model.Sla;
import br.com.cogerh.template.service.ConjuntoService;
import br.com.cogerh.template.service.ConsultaService;
import br.com.cogerh.template.service.CronometroService;
import br.com.cogerh.template.service.SensorService;
import br.com.cogerh.template.service.TaskService;

@Controller
@Scope("view")
public class SensorBean extends AbstractBean
{
	
	private List<Sensor> sensorList;
	private Sensor sensor = new Sensor();
	private Sla sla = new Sla();
	
	private Integer contagem;
	
	private String duracaoMinima;
	
	private String duracaoMaxima;
	
	private String duracaoMedia;
	
	private String desvioPadrao;
	
	private Double perMenosQueUmDia;
	private Double perUmDia;
	private Double perDoisDias;
	private Double perTresDias;
	private Double perQuatroDias;
	private Double perCincoDias;
	private Double perSeisDias;
	private Double perMaisQueSeisDias;
	
	//private DualListModel<Conjunto> conjuntoPickList;
	
	private List<Conjunto> conjuntoListSource;
	
	private List<Conjunto> conjuntoListTarget;
	
	private List<Conjunto> selectedConjuntoListSource;
	
	private List<Conjunto> selectedConjuntoListTarget;
	
	private Conjunto selectedConjunto;
	
	private List<Cronometro> cronometroList;
	
	@Autowired
	private SensorService sensorService;
	
	@Autowired
	private CronometroService cronometroService;
	
	@Autowired
	private ConsultaService consultaService;
	
	@Autowired
	private ConjuntoService conjuntoService;
	
	@Autowired
	private TaskService taskService;
	
	private LineChartModel lineModel1;
	
	private LineChartModel lineModel2;
	
	private HorizontalBarChartModel horizontalBarModel;
	
	private String dadosDoGrafico;
	
	@PostConstruct
	public void init()
	{
		try
		{
			
			this.sensorList = sensorService.listarSensores(null);
			
			this.setConjuntoListSource(conjuntoService.listarConjuntos(null));
			
			this.setConjuntoListTarget(new ArrayList<Conjunto>());
			
			this.setSelectedConjuntoListSource(new ArrayList<Conjunto>());
			
			this.setSelectedConjuntoListTarget(new ArrayList<Conjunto>());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private LineChartModel initLinearModel() {
		LineChartModel model = new LineChartModel();
		 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
 
        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");
        
        
 
        series2.set(2, 3);
        series2.set(2, 5);
        series2.set(5, 2);
        series2.set(7, 2);
        series2.set(9, 2);
        
        horizontalBarModel = new HorizontalBarChartModel();
        horizontalBarModel.setExtender("NAME");
        
 
        
        model.addSeries(series1);
        model.addSeries(series2);
 
        return model;
    }
	
	public void initConsultaDetalhada(Integer sensorId) 
	{
		try {
			
			this.sensor = sensorService.buscarPorId(sensorId);
			
			this.cronometroList = cronometroService.listarCronometrosBySensor(sensor);
			
			calcularPorcentagemCronometro();
			
			//sincronizarInformacoesDosCronometros(sensor);
			
			obterLevantamentoInformacoes();
			
			montarGrafico();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createLineModels() {
		lineModel1 = initLinearModel();
        lineModel1.setTitle("Linear Chart");
        lineModel1.setLegendPosition("e");
        
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
 
        /*lineModel2 = initCategoryModel();
        lineModel2.setTitle("Category Chart");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
 
        zoomModel = initLinearModel();
        zoomModel.setTitle("Zoom");
        zoomModel.setZoom(true);
        zoomModel.setLegendPosition("e");
        yAxis = zoomModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);*/
    }
	
	public void sincronizarInformacoesDosCronometros() 
	{
		// TODO Auto-generated method stub
		try {
			List<Sensor> sensorList = sensorService.listarSensores(null);
			
			for(Sensor sensor : sensorList) {
				sensorService.obterInstanciasDoSensor(sensor);
				sensorService.ajustarDuracaoSensores(sensor);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		consultaService.obterInstanciasDeCronometrosDoSensor(sensor);
	}
	
	public void montarGrafico() {
		dadosDoGrafico = "{\"zeroDias\": " + obterQuantidadeDeInstanciasPorDia(0) + ", " 
				+ "\"umDia\": " + obterQuantidadeDeInstanciasPorDia(1) + ", "
				+ "\"doisDias\": " + obterQuantidadeDeInstanciasPorDia(2) + ", "
				+ "\"tresDias\": " + obterQuantidadeDeInstanciasPorDia(3) + ", "
				+ "\"quatroDias\": " + obterQuantidadeDeInstanciasPorDia(4) + ", " 
				+ "\"cincoDias\": " + obterQuantidadeDeInstanciasPorDia(5) + ", "
				+ "\"seisDias\": " + obterQuantidadeDeInstanciasPorDia(6) + ", "
				+ "\"seteDias\":" + obterQuantidadeDeInstanciasPorDia(7) + ", "
				+ "\"oitoDias\": " + obterQuantidadeDeInstanciasPorDia(8) + ", "
				+ "\"noveDias\": " + obterQuantidadeDeInstanciasPorDia(9) + ", "
				+ "\"dezDias\": " + obterQuantidadeDeInstanciasPorDia(10) + ", "
				+ "\"onzeDias\": " + obterQuantidadeDeInstanciasPorDia(11) + ", "
				+ "\"dozeDias\": " + obterQuantidadeDeInstanciasPorDia(12) + ", "
				+ "\"trezeDias\": " + obterQuantidadeDeInstanciasPorDia(13) + ", "
				+ "\"quatorzeDias\": "+ obterQuantidadeDeInstanciasPorDia(14) + "}"; 
	}
	
	public Integer obterQuantidadeDeInstanciasPorDia(Integer dias) 
	{
		Integer retorno = 0;
		try {
			
			for(Cronometro cronometro : cronometroList) 
			{
				Integer d = cronometro.getDuracao() / 480;
				if(dias == d) retorno++;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retorno;
	}

	public void initForm(Integer sensorId) 
	{
		try
		{
    		    		
	        if (null == sensor || sensorId == 0)
	        {
	        	this.viewState = ViewState.NOVO;

	            this.sensor = new Sensor();
	            
	        }
	        else
	        {
	        	this.viewState = ViewState.EDITAR;

	            this.sensor = this.sensorService.buscarPorId(sensorId);

	            for (SensorConjunto sensorConjunto : this.sensor.getSensorConjuntoList())
	            {
	            	// Adiciona a lista de destino as tarefas que ja fazem parte do sensor
	            	conjuntoListTarget.add(sensorConjunto.getConjunto());

	            	// Remove as tarefas que ja fazem parte do sensor
	            	conjuntoListSource.remove(sensorConjunto.getConjunto());
	            }

		        //conjuntoPickList = new DualListModel<Conjunto>(conjuntoOrigemList, conjuntoDestinoList);

		        //setConjuntoPickList(new DualListModel<Conjunto>(conjuntoOrigemList, conjuntoDestinoList));
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
			/*this.sensor.setConjuntoList(conjuntoPickList.getTarget());*/
			for(Conjunto conjunto : conjuntoListTarget)
			{
				if (null == this.sensor.getSensorConjuntoList())
					this.sensor.setSensorConjuntoList(new ArrayList<SensorConjunto>());

				SensorConjunto sensorConjunto = new SensorConjunto();

				sensorConjunto.setConjunto(conjunto);
				sensorConjunto.setSensor(this.sensor);

				this.sensor.getSensorConjuntoList().add(sensorConjunto);
			}
			
			sensorService.criarSensorNoBancoDoBizagi(sensor);
			
			sensorService.salvarSensor(sensor);
			
			if(sensor.getSensorConjuntoList().size() == 1){ //sensor possui apenas um conjunto de tarefa - sensor unitário 
				Conjunto conjunto = sensor.getSensorConjuntoList().get(0).getConjunto();
				conjunto.setSensorUnitario(sensor);
				conjuntoService.alterarConjunto(conjunto); 
			}
			
			sensor = new Sensor();

			this.adicionaMensagemDeSucesso("Sensor adicionado com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();

			this.adicionaMensagemDeErro("Ocorreu um erro ao tentar salvar o registro");
		}
	}
	
	public void remover(Sensor sensor)
	{
		try
		{
			sensorService.removerSensor(sensor);

			this.sensorList = sensorService.listarSensores(null);

			this.adicionaMensagemDeSucesso("Sensor removido com sucesso");
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
			for (Conjunto conjunto : conjuntoListTarget)
			{
				boolean canAdd = true;

				if (null == this.sensor.getSensorConjuntoList())
					this.sensor.setSensorConjuntoList(new ArrayList<SensorConjunto>());

				SensorConjunto sensorConjunto = new SensorConjunto();

				sensorConjunto.setConjunto(conjunto);
				sensorConjunto.setSensor(this.sensor);

				// Verifica se a conjunto em questao ja foi adicionada a lista de permissoes do sensor
				for (SensorConjunto gruPer : this.sensor.getSensorConjuntoList())
				{
					if (gruPer.getConjunto().equals(sensorConjunto.getConjunto()))
					{
						canAdd = false;

						break;
					}
				}

				if (canAdd)
					this.sensor.getSensorConjuntoList().add(sensorConjunto);
			}
			
			for (Conjunto conjunto : conjuntoListSource)
			{
				boolean canRemove = false;

				SensorConjunto sensorConjunto = new SensorConjunto();

				/** Verifica se a conjunto em questao foi adicionada ao pickList source
				 *  e precisa ser removida da lista de sensorConjuntos da sensor
				 **/
				for (SensorConjunto bacGer : this.sensor.getSensorConjuntoList())
				{
					if (bacGer.getConjunto().equals(conjunto))
					{
						canRemove = true;

						sensorConjunto = bacGer;
						
						break;
					}
				}

				if (canRemove)
					this.sensor.getSensorConjuntoList().remove(sensorConjunto);
			}

			this.sensor = sensorService.alterarSensor(this.sensor);

			this.adicionaMensagemDeSucesso("Sensor atualizado com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();

			this.adicionaMensagemDeErro("Ocorreu um erro ao tentar atualizar o registro");
		}
	}
	
	
	public void addTarefaSelecionada() 
	{
        conjuntoListTarget.addAll(selectedConjuntoListSource);
        conjuntoListSource.removeAll(selectedConjuntoListSource);
    }
	
	public void removeTarefaSelecionada() 
	{
		conjuntoListSource.addAll(selectedConjuntoListTarget);
        conjuntoListTarget.removeAll(selectedConjuntoListTarget);
    }
	
	public String showFormNovo() 
	{
        return "novo.xhtml?faces-redirect=true&sensorId="+0;
    }

	public String showFormListar() 
	{
        return "lista.xhtml?faces-redirect=true";
    }

	public String showFormEditar(Integer sensorId) 
	{
		return "novo.xhtml?faces-redirect=true&sensorId=" + sensorId;
	}
	
	public String showFormVisualizar(Integer sensorId) {

		return "consulta_detalhada.xhtml?faces-redirect=true&sensorId=" + sensorId;
	}
	
	public void obterLevantamentoInformacoes() 
	{
		try {
			
			contagem = sensorService.obterQuantidadeDeInstancias(sensor);
			Integer dMedia = contagem > 0 ? sensorService.obterDuracaoTotal(sensor) / contagem : 0;
			duracaoMedia = dMedia.toString();
			duracaoMinima = sensorService.obterDuracaoMinima(sensor) != null ? sensorService.obterDuracaoMinima(sensor).toString() : null;
			duracaoMaxima = sensorService.obterDuracaoMaxima(sensor) != null ? sensorService.obterDuracaoMaxima(sensor).toString() : null;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void calcularPorcentagemCronometro() {
		
		perMenosQueUmDia = perUmDia = perDoisDias = perTresDias = perQuatroDias = perCincoDias = perSeisDias = perMaisQueSeisDias = 0.0;
		
		try {
			
			Integer quantidade = cronometroService.obterQuantidadeCronometros(sensor);
			
			for(Cronometro cronometro : cronometroList) 
			{
				Integer dias = cronometro.getDuracao() / 480;
				if(dias < 1) perMenosQueUmDia++;
				if(dias == 1) perUmDia++;
				if(dias == 2) perDoisDias++;
				if(dias == 3) perTresDias++;
				if(dias == 4) perQuatroDias++;
				if(dias == 5) perCincoDias++;
				if(dias == 6) perSeisDias++;
				if(dias > 6) perMaisQueSeisDias++;
			}
			
			perMenosQueUmDia = (perMenosQueUmDia / quantidade) * 100;
			perUmDia = (perUmDia / quantidade) * 100;
			perDoisDias = (perDoisDias / quantidade) * 100;
			perTresDias = (perTresDias / quantidade) * 100;
			perQuatroDias = (perQuatroDias / quantidade) * 100;
			perCincoDias = (perCincoDias / quantidade) * 100;
			perSeisDias = (perSeisDias / quantidade) * 100;
			perMaisQueSeisDias = (perMaisQueSeisDias / quantidade) * 100;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void showMessage() {
		System.out.println("Chamada executada!");
	}

	public List<Sensor> getSensorList() {
		return sensorList;
	}

	public void setSensorList(List<Sensor> sensorList) {
		this.sensorList = sensorList;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Sla getSla() {
		return sla;
	}

	public void setSla(Sla sla) {
		this.sla = sla;
	}

	public Conjunto getSelectedConjunto() {
		return selectedConjunto;
	}

	public void setSelectedConjunto(Conjunto selectedConjunto) {
		this.selectedConjunto = selectedConjunto;
	}

	public List<Conjunto> getConjuntoListTarget() {
		return conjuntoListTarget;
	}

	public void setConjuntoListTarget(List<Conjunto> conjuntoListTarget) {
		this.conjuntoListTarget = conjuntoListTarget;
	}

	public List<Conjunto> getConjuntoListSource() {
		return conjuntoListSource;
	}

	public void setConjuntoListSource(List<Conjunto> conjuntoListSource) {
		this.conjuntoListSource = conjuntoListSource;
	}

	public List<Conjunto> getSelectedConjuntoListSource() {
		return selectedConjuntoListSource;
	}

	public void setSelectedConjuntoListSource(List<Conjunto> selectedConjuntoListSource) {
		this.selectedConjuntoListSource = selectedConjuntoListSource;
	}

	public List<Conjunto> getSelectedConjuntoListTarget() {
		return selectedConjuntoListTarget;
	}

	public void setSelectedConjuntoListTarget(List<Conjunto> selectedConjuntoListTarget) {
		this.selectedConjuntoListTarget = selectedConjuntoListTarget;
	}

	public ConjuntoService getConjuntoService() {
		return conjuntoService;
	}

	public void setConjuntoService(ConjuntoService conjuntoService) {
		this.conjuntoService = conjuntoService;
	}

	public SensorService getSensorService() {
		return sensorService;
	}

	public void setSensorService(SensorService sensorService) {
		this.sensorService = sensorService;
	}

	public Integer getContagem() {
		return contagem;
	}

	public void setContagem(Integer contagem) {
		this.contagem = contagem;
	}

	public String getDuracaoMinima() {
		return duracaoMinima;
	}

	public void setDuracaoMinima(String duracaoMinima) {
		this.duracaoMinima = duracaoMinima;
	}

	public String getDuracaoMaxima() {
		return duracaoMaxima;
	}

	public void setDuracaoMaxima(String duracaoMaxima) {
		this.duracaoMaxima = duracaoMaxima;
	}

	public String getDuracaoMedia() {
		return duracaoMedia;
	}

	public void setDuracaoMedia(String duracaoMedia) {
		this.duracaoMedia = duracaoMedia;
	}

	public String getDesvioPadrao() {
		return desvioPadrao;
	}

	public void setDesvioPadrao(String desvioPadrao) {
		this.desvioPadrao = desvioPadrao;
	}

	public List<Cronometro> getCronometroList() {
		return cronometroList;
	}

	public void setCronometroList(List<Cronometro> cronometroList) {
		this.cronometroList = cronometroList;
	}

	public Double getPerMenosQueUmDia() {
		return perMenosQueUmDia;
	}

	public void setPerMenosQueUmDia(Double perMenosQueUmDia) {
		this.perMenosQueUmDia = perMenosQueUmDia;
	}

	public Double getPerUmDia() {
		return perUmDia;
	}

	public void setPerUmDia(Double perUmDia) {
		this.perUmDia = perUmDia;
	}

	public Double getPerDoisDias() {
		return perDoisDias;
	}

	public void setPerDoisDias(Double perDoisDias) {
		this.perDoisDias = perDoisDias;
	}

	public Double getPerTresDias() {
		return perTresDias;
	}

	public void setPerTresDias(Double perTresDias) {
		this.perTresDias = perTresDias;
	}

	public Double getPerQuatroDias() {
		return perQuatroDias;
	}

	public void setPerQuatroDias(Double perQuatroDias) {
		this.perQuatroDias = perQuatroDias;
	}

	public Double getPerCincoDias() {
		return perCincoDias;
	}

	public void setPerCincoDias(Double perCincoDias) {
		this.perCincoDias = perCincoDias;
	}

	public Double getPerSeisDias() {
		return perSeisDias;
	}

	public void setPerSeisDias(Double perSeisDias) {
		this.perSeisDias = perSeisDias;
	}

	public Double getPerMaisQueSeisDias() {
		return perMaisQueSeisDias;
	}

	public void setPerMaisQueSeisDias(Double perMaisQueSeisDias) {
		this.perMaisQueSeisDias = perMaisQueSeisDias;
	}

	public LineChartModel getLineModel1() {
		return lineModel1;
	}

	public void setLineModel1(LineChartModel lineModel1) {
		this.lineModel1 = lineModel1;
	}

	public LineChartModel getLineModel2() {
		return lineModel2;
	}

	public void setLineModel2(LineChartModel lineModel2) {
		this.lineModel2 = lineModel2;
	}

	public HorizontalBarChartModel getHorizontalBarModel() {
		return horizontalBarModel;
	}

	public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel) {
		this.horizontalBarModel = horizontalBarModel;
	}

	public String getDadosDoGrafico() {
		return dadosDoGrafico;
	}

	public void setDadosDoGrafico(String dadosDoGrafico) {
		this.dadosDoGrafico = dadosDoGrafico;
	}
}
