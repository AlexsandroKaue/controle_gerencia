package br.com.cogerh.template.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.GrupoDAO;
import br.com.cogerh.template.dao.UsuarioBizagiDAO;
import br.com.cogerh.template.model.Grupo;
import br.com.cogerh.template.model.UsuarioBizagi;
import br.com.cogerh.template.util.ConexaoOracle;

@Transactional
@Repository
public class UsuarioBizagiDAOImpl extends GenericDAOImpl<UsuarioBizagi, Integer> implements UsuarioBizagiDAO 
{
	
	private static ConexaoOracle conOracle;
	
	public List<UsuarioBizagi> buscarUsuarios()
	{
		String sql = null;
		
		List<UsuarioBizagi> usuarioBizagiList = new ArrayList<>();
		UsuarioBizagi usuarioBizagi = null;
		
		try {
			
			conOracle = new ConexaoOracle();
			Statement stmt = conOracle.getConexao().createStatement();
			
			sql = "SELECT WFUSER.FULLNAME, GERENCIA.DSC FROM BPM_COGERH10.WFUSER WFUSER, BPM_COGERH10.GERENCIA GERENCIA WHERE WFUSER.IDGERENCIA = GERENCIA.EXT";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			
			while(rs.next())
			{
				usuarioBizagi = new UsuarioBizagi();
				usuarioBizagi.setNome(rs.getString("FULLNAME"));
				usuarioBizagi.setGerencia(rs.getString("DSC"));
				
				usuarioBizagiList.add(usuarioBizagi);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usuarioBizagiList;
		
	}
	
	public static void main(String args[]) 
	{
		List<UsuarioBizagi> usuarioList = new UsuarioBizagiDAOImpl().buscarUsuarios();
		for(UsuarioBizagi usuarioBizagi : usuarioList)
		{
			System.out.println();
			System.out.format("%50s%50s", usuarioBizagi.getNome(), usuarioBizagi.getGerencia());
		}
		
	}
	
}
