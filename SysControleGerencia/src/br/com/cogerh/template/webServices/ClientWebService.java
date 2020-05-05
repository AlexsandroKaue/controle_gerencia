package br.com.cogerh.template.webServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ClientWebService {
	
	protected InputStream consume(String path) {
		HttpURLConnection connection = null;
		try {
			
			URL url = new URL(path);
			connection = (HttpURLConnection) url.openConnection();
			InputStream content = connection.getInputStream();
			return content;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected JSONObject parse(InputStream json) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;

		try {
			//Salva no objeto JSONObject o que o parse tratou do arquivo
			jsonObject = (JSONObject) parser.parse(new InputStreamReader(json));
			return jsonObject;
		 
		//Trata as exceptions que podem ser lançadas no decorrer do processo
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
