package br.com.cogerh.template.webServices.test;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONRead {
	
	public static String carrega(String json) {
		JSONObject jsonObject;
		//Cria o parse de tratamento
		JSONParser parser = new JSONParser();
		//Variaveis que irao armazenar os dados do arquivo JSON
		String nome;
		String sobrenome;
		String estado;
		String pais;

		try {
			//Salva no objeto JSONObject o que o parse tratou do arquivo
			jsonObject = (JSONObject) parser.parse(json);
			
			//Salva nas variaveis os dados retirados do arquivo
			nome = (String) jsonObject.get("nome");
			sobrenome = (String) jsonObject.get("sobrenome");
			estado = (String) jsonObject.get("estado");
			pais = (String) jsonObject.get("pais");

			/*System.out.printf(
					"Nome: %s\nSobrenome: %s\nEstado: %s\nPais: %s\n",
					nome, sobrenome, estado, pais);*/
			return "Nome: " + nome+ "Sobrenome: " + sobrenome + "Estado: " + estado + "Pais: " + pais;
		} 
		//Trata as exceptions que podem ser lançadas no decorrer do processo
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}