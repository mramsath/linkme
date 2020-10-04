package com.arz.linkme.profiles.db.repo;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class GenericJpaDao< T extends Serializable>
        extends AbstractJpaDao< T > implements IGenericDao< T >{

    @Override
    public T findOne(long id) {
        return null;
    }

    @Override
    public T create(T entity) {
        return null;
    }

    @Override
    public void deleteById(long entityId) {

    }
    //
}
