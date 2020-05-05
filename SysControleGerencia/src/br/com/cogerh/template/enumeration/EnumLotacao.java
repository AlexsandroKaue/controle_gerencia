package br.com.cogerh.template.enumeration;

public enum EnumLotacao 
{
	/*Cogerh_GEOFI("0"), 
	Cogerh_REGIONAL("1"), 
	Cogerh_Diretoria_de_Planejamento("2"), 
	SRH_CGERH("3"), 
	SRH_ASJUR("4"), 
	SRH_SECRETARIO("5"), 
	SRH_CELOU("6");*/
	
	Unidade_de_Recepcao(0, "Unidade de Recepção"),
	Cogerh_GEOFI(1, "Cogerh - GEOFI"), 
	Cogerh_REGIONAL(2, "Cogerh - Regional"), 
	Cogerh_Diretoria_de_Planejamento(3, "Cogerh - Diretoria de Planejamento"), 
	SRH_CGERH(4, "SRH - CGERH"), 
	SRH_ASJUR(5, "SRH - ASJUR"), 
	SRH_SECRETARIO(6, "SRH - Secretário"), 
	SRH_CELOU(7, "SRH - CELOU"),
	RESOLUCAO_PENDENCIA(8, "Resolução de Pendências");

	private String descricao;
	
	private Integer id;

	/*EnumLotacao(String descricao) 
	{
		this.descricao = descricao;
	}*/
	
	EnumLotacao(Integer id, String descricao) 
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

	public static EnumLotacao valueOf( final int ordinal )
	{
		final EnumLotacao[] values = EnumLotacao.values();

		for (final EnumLotacao enumLotacao : values)
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
