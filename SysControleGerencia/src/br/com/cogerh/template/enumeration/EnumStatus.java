package br.com.cogerh.template.enumeration;

public enum EnumStatus 
{
	NÃO("0"), SIM("1");

	private String descricao;

	EnumStatus(String descricao) 
	{
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

	public static EnumStatus valueOf( final int ordinal )
	{
		final EnumStatus[] values = EnumStatus.values();

		for (final EnumStatus enumAtivo : values)
		{
			if (enumAtivo.ordinal() == ordinal)
				return enumAtivo;
		}

		return null;
	}
}
