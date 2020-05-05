package br.com.cogerh.template.webServices;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import br.com.cogerh.template.model.Computador;

public class ComputadorWebService extends ClientWebService {
	
	public static void main(String args[]) {
		ComputadorWebService computadorWebService = new ComputadorWebService();
		computadorWebService.listarComputadores();
	}
	
	public List<Computador> listarComputadores() {
		
		InputStream json = consume(ClientWebServiceDefinitions.LISTAR_TODOS);
		JSONObject jsonObject = parse(json);
		if(jsonObject != null) {
			List<Computador> computadores = new ArrayList<>();
			JSONArray array = (JSONArray) jsonObject.get("maquina");
			for(int i = 0; i<array.size() ; i++) 
			{
				Computador computador = new Computador();
				computador.setID((String)((JSONObject)array.get(i)).get("id"));
				computador.setDevice((String)((JSONObject)array.get(i)).get("device"));
				computador.setNome((String)((JSONObject)array.get(i)).get("nome"));
				computador.setGrupo((String)((JSONObject)array.get(i)).get("grupo"));
				computador.setSistemaOperacional((String)((JSONObject)array.get(i)).get("sistemaOperacional"));
				computador.setVersaoSistemaOperacional((String)((JSONObject)array.get(i)).get("versaoSistemaOperacional"));
				computador.setProcessador((String)((JSONObject)array.get(i)).get("processador"));
				computador.setMemoria((String)((JSONObject)array.get(i)).get("memoria"));
				computador.setArquitetura((String)((JSONObject)array.get(i)).get("arquiterura"));
				computadores.add(computador);
				
				System.out.println("id: "+((JSONObject)array.get(i)).get("id"));
				System.out.println("device: "+((JSONObject)array.get(i)).get("device"));
				System.out.println("nome: "+((JSONObject)array.get(i)).get("nome"));
				System.out.println("grupo: "+((JSONObject)array.get(i)).get("grupo"));
				System.out.println("sistemaOperacional: "+((JSONObject)array.get(i)).get("sistemaOperacional"));
				System.out.println("versaoSistemaOperacional: "+((JSONObject)array.get(i)).get("versaoSistemaOperacional"));
				System.out.println("processador: "+((JSONObject)array.get(i)).get("processador"));
				System.out.println("memoria: "+((JSONObject)array.get(i)).get("memoria"));
				System.out.println("arquitetura: "+((JSONObject)array.get(i)).get("arquiterura"));
				System.out.println("\n");
			}
			return computadores;
			
		}
		
		return null;
	}
	
}