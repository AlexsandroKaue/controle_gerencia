package br.com.cogerh.template.enumeration;

public enum EnumAtivo 
{
	NÃO("0"), SIM("1");

	private String descricao;

	EnumAtivo(String descricao) 
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

	public static EnumAtivo valueOf( final int ordinal )
	{
		final EnumAtivo[] values = EnumAtivo.values();

		for (final EnumAtivo enumAtivo : values)
		{
			if (enumAtivo.ordinal() == ordinal)
				return enumAtivo;
		}

		return null;
	}
}
