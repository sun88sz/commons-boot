package com.sun.web.service.impl;

import com.kedacom.ctsp.web.entity.GenericEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.util.List;

/**
 * 启用缓冲的通用实体曾删改查服务,继承此类
 * 在类上注解{@link org.springframework.cache.annotation.CacheConfig}即可
 *
 * @author
 * @see org.springframework.cache.annotation.CacheConfig
 * @see Cacheable
 * @see CacheEvict
 * @since 3.0
 */
public abstract class EnableCacheGenericEntityService<E extends GenericEntity<PK>, PK extends Serializable> extends GenericEntityService<E, PK> {

    @Override
    @Cacheable(key = "'id:'+#pk")
    public E get(PK pk) {
        return super.get(pk);
    }

    @Override
    @CacheEvict(allEntries = true)
    public int update(List<E> data) {
        return super.update(data);
    }

    @Override
    @CacheEvict(key = "'id:'+#pk")
    public int update(PK pk, E entity) {
        return super.update(pk, entity);
    }

    @Override
    @CacheEvict(key = "'id:'+#entity.id")
    public int update(E entity) {
        return super.update(entity);
    }

    @Override
    @CacheEvict(key = "'id:'+#result")
    public PK insert(E entity) {
        return super.insert(entity);
    }

    @Override
    @CacheEvict(key = "'id:'+#pk")
    public int delete(PK pk) {
        return super.delete(pk);
    }

    @Override
    @CacheEvict(key = "'id:'+#result")
    public PK saveOrUpdate(E entity) {
        return super.saveOrUpdate(entity);
    }

}
