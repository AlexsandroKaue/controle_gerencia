package br.com.cogerh.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="conjunto_task")
public class ConjuntoTask extends PersistentEntityImpl
{

	@Id
	@Column(name = "conj_tsk_cod_id")
	@SequenceGenerator(name = "seq_conjunto_task", sequenceName = "seq_conjunto_task", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "seq_conjunto_task", strategy = GenerationType.SEQUENCE)
    private Integer id;

	@ManyToOne
	@JoinColumn(name = "conj_cod_id")
	private Conjunto conjunto;

	@ManyToOne
	@JoinColumn(name = "tsk_cod_id")
	private Task task;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Conjunto getConjunto() {
		return conjunto;
	}

	public void setConjunto(Conjunto conjunto) {
		this.conjunto = conjunto;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
