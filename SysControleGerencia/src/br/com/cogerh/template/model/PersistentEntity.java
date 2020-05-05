package br.com.cogerh.template.model;

import java.io.Serializable;

public interface PersistentEntity<T extends Serializable>
{
    public Integer getId();
}