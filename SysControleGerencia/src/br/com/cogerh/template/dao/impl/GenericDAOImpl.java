package br.com.cogerh.template.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.cogerh.template.dao.GenericDAO;
import br.com.cogerh.template.model.PersistentEntity;

@Repository
@SuppressWarnings("rawtypes")
public abstract class GenericDAOImpl <T extends PersistentEntity, ID extends Serializable> implements GenericDAO<T, ID>, Serializable
{
	@PersistenceContext
	EntityManager entityManager;

	private Class persistentClass;

	/**
     * Inicia o repositorio identificando qual e a classe de nossa entidade, seu
     * tipo {@link Class<?>}
     */
    @SuppressWarnings("unchecked")
	public GenericDAOImpl() 
    {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public T save(T entity) throws Exception 
	{
		try 
		{
			this.entityManager.persist(entity);

			return entity;
         }
		catch (Exception e) 
		{
			e.printStackTrace();

            throw new Exception("Ocorreu um erro ao tentar salvar");
        }
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public T saveFlushAndClear(T entity) throws Exception 
	{
		try 
		{
			this.entityManager.persist(entity);
            this.entityManager.flush();
            this.entityManager.clear();

            return entity;
		}
		catch (Exception e) 
		{
			e.printStackTrace();

			throw new Exception("Ocorreu um erro ao tentar salvar");
        }
    }

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public T update(T entity) throws Exception 
	{
		try 
		{
			return this.entityManager.merge(entity);
        }
		catch (Exception e) 
		{
			e.printStackTrace();

			throw new Exception("Ocorreu um erro ao tentar atualizar");
        }
    }

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public void delete(T entity) throws Exception 
	{
		try 
		{
			entity = this.entityManager.merge(entity);
			
			this.entityManager.remove(entity);
        }
		catch (Exception e) 
		{
			e.printStackTrace();

            throw new Exception("Ocorreu um erro ao tentar deletar");
        }
	}

	@SuppressWarnings("unchecked")
	public T buscarPorId(ID id) 
	{
        final T entity;

        entity = (T) this.entityManager.find(this.getPersistentClass(), id);

        return entity;
    }

	/**
     * @return a classe de nossa entidade persistente
     */
	protected final Class getPersistentClass()
	{
		return persistentClass;
	}
}