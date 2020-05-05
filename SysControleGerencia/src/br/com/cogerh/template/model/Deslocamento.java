package br.com.cogerh.template.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.cogerh.template.enumeration.EnumLotacao;

/**
 * @author kaue
 *
 */

@Entity
@Table(name="deslocamento")
public class Deslocamento extends PersistentEntityImpl
{
	@Id
	@Column(name = "DES_CODIGO_PK")
	@SequenceGenerator(name = "seq_deslocamento", sequenceName = "seq_deslocamento", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_deslocamento", strategy = GenerationType.SEQUENCE)
    private Integer id;
	
	@Column(name = "DES_NUM_PROCESSO")
	private String numeroProcesso;
	
	@Column(name = "DES_NUM_CASE")
	private Integer casee;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "DES_GER_ORIGEM")
	private EnumLotacao gerenciaOrigem;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "DES_GER_DESTINO")
	private EnumLotacao gerenciaDestino;
	
	@Column(name = "DES_TIMESTAMP_ENTRADA")
	private Timestamp timestampEntrada;
	
	@Column(name = "DES_TIMESTAMP_SAIDA")
	private Timestamp timestampSaida;

	@Column(name = "DES_PERMANENCIA")
	private Long permanencia;
	
	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public Integer getCasee() {
		return casee;
	}

	public void setCasee(Integer casee) {
		this.casee = casee;
	}

	public EnumLotacao getGerenciaOrigem() {
		return gerenciaOrigem;
	}

	public void setGerenciaOrigem(EnumLotacao gerenciaOrigem) {
		this.gerenciaOrigem = gerenciaOrigem;
	}

	public EnumLotacao getGerenciaDestino() {
		return gerenciaDestino;
	}

	public void setGerenciaDestino(EnumLotacao gerenciaDestino) {
		this.gerenciaDestino = gerenciaDestino;
	}

	/*public String getTimestamp() {
		try {
			Date dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(timestamp);
			timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dt);
			return timestamp;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setTimestamp(String timestamp) {
		try {
			Date dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(timestamp);
			timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(dt);
			this.timestamp = timestamp;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public Long getPermanencia() {
		return permanencia;
	}

	public void setPermanencia(Long permanencia) {
		this.permanencia = permanencia;
	}

	public Timestamp getTimestampEntrada() {
		return timestampEntrada;
	}

	public void setTimestampEntrada(Timestamp timestampEntrada) {
		this.timestampEntrada = timestampEntrada;
	}

	public Timestamp getTimestampSaida() {
		return timestampSaida;
	}

	public void setTimestampSaida(Timestamp timestampSaida) {
		this.timestampSaida = timestampSaida;
	}
 

}

