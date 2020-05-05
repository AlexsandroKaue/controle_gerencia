package br.com.cogerh.template.webServices;

import java.io.InputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PocoWebService extends ClientWebService {
	
	public static void main(String args[]) {
		PocoWebService pocoWebService = new PocoWebService();
		pocoWebService.buscarPocos();
	}
	
	public void buscarPocos() {
		
		InputStream json = consume(ClientWebServiceDefinitions.BUSCAR_POCOS);
		JSONObject jsonObject = parse(json);
		if(jsonObject != null) {

			JSONArray array = (JSONArray) jsonObject.get("pocoForm");
			for(int i = 0; i<array.size() ; i++) {
				System.out.println("PES_CODIGO_FK: "+((JSONObject)array.get(i)).get("PES_CODIGO_FK"));
				System.out.println("MUN_CODIGO_FK: "+((JSONObject)array.get(i)).get("MUN_CODIGO_FK"));
				System.out.println("PES_NOME: "+((JSONObject)array.get(i)).get("PES_NOME"));
				System.out.println("MUN_NOME: "+((JSONObject)array.get(i)).get("MUN_NOME"));
				System.out.println("POC_LATITUDE_UTM: "+((JSONObject)array.get(i)).get("POC_LATITUDE_UTM"));
				System.out.println("POC_LONGITUDE_UTM: "+((JSONObject)array.get(i)).get("POC_LONGITUDE_UTM"));
				System.out.println("PED_NUM_PROCESSO: "+((JSONObject)array.get(i)).get("PED_NUM_PROCESSO"));
				System.out.println("distancia: "+((JSONObject)array.get(i)).get("distancia"));
				System.out.println("\n");
			}
		}
	}
	
}
