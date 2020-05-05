package br.com.cogerh.template.dao;

import java.io.Serializable;

import br.com.cogerh.template.model.PersistentEntity;

@SuppressWarnings("rawtypes")
public interface GenericDAO <T extends PersistentEntity, ID extends Serializable>
{
	public T save(T entity) throws Exception;
	public T saveFlushAndClear (T entity) throws Exception;
	public T update(T entity) throws Exception;

	public void delete(T entity) throws Exception;
	
	public T buscarPorId(ID id);
	
}