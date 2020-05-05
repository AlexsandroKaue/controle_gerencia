package br.com.cogerh.template.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoOracle {

	private Connection conexao;

	public ConexaoOracle() throws ClassNotFoundException, SQLException {
		criaConexao();
	}
	
	

	public void criaConexao() throws ClassNotFoundException, SQLException {

//		String endereco = "172.31.136.122";
//		String porta = "5432";
//		String banco = "gemet";
//		String usuario = "postgres";
//		String senha = "postgres";

		/* DESENVOLVIMENTO */
		String endereco = "172.31.136.41";
		String porta = "1521";
		String usuario = "bizagi_consulta";
		String senha = "c0nsulta123";
		String servico = "bizagi";
		
		

		/* PRODUÇÃO 		
		String endereco = "172.31.136.22";
		String porta = "1521";
		String usuario = "adm_outorga";
		String senha = "outorga";
		String servico = "COGERH";
		*/
		
		
		
		/*
		 * LOCALHOST String endereco = "localhost"; String porta = "5432";
		 * String banco = "gemet"; String usuario = "postgres"; String senha =
		 * "postgres";
		 */

		try {
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String Sconexao =  "jdbc:oracle:thin:@"+endereco + ":"+porta + ":"+servico;
			conexao = DriverManager.getConnection(Sconexao,usuario,senha);
		} catch (ClassNotFoundException ex) {
			throw ex;
		} catch (SQLException ex) {
			throw ex;
		}

	}

	public void fechaConexao() throws SQLException {
		conexao.close();
		conexao = null;
	}

	public boolean isFechada() {
		try {
			return conexao.isClosed();
		} catch (SQLException ex) {
			return false;
		}
	}

	public Connection getConexao() {
		return conexao;
	}

	
	
	
	
}
