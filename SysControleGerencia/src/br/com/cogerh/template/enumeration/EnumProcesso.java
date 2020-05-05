package br.com.cogerh.template.enumeration;

public enum EnumProcesso 
{
	/*Cogerh_GEOFI("0"), 
	Cogerh_REGIONAL("1"), 
	Cogerh_Diretoria_de_Planejamento("2"), 
	SRH_CGERH("3"), 
	SRH_ASJUR("4"), 
	SRH_SECRETARIO("5"), 
	SRH_CELOU("6");*/
	
	Solicitacao_de_Outorga(1, "Solicitação de Outorga"),
	Resolucao_de_Pendencia(3, "Resolução de Pendência"),
	IntegracaoViproc(4, "Integração VIPROC");

	private String descricao;
	
	private Integer id;

	/*EnumLotacao(String descricao) 
	{
		this.descricao = descricao;
	}*/
	
	EnumProcesso(Integer id, String descricao) 
	{
		this.id = id;
		this.descricao = descricao;
		
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public static EnumProcesso valueOf( final int ordinal )
	{
		final EnumProcesso[] values = EnumProcesso.values();

		for (final EnumProcesso enumLotacao : values)
		{
			if (enumLotacao.ordinal() == ordinal)
				return enumLotacao;
		}

		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
