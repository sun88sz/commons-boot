package com.sun.entity;

/**
 * 表现对象
 * Controller层 使用的entity
 *
 * @author : Sun
 * @date : 2018/7/16 14:16
 */
public interface VO<E, PK> extends Entity, ConvertTo<E>, ConvertFrom<E> {

    PK getId();

    void setId(PK id);
}
