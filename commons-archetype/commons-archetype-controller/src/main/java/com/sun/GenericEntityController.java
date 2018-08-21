
package com.sun;

import com.sun.entity.ConvertFrom;
import com.sun.entity.ConvertTo;
import com.sun.web.service.CrudService;

/**
 * 通用实体的增删改查控制器
 *
 * @author
 * @see GenericEntity
 * @see CrudController
 * @see CrudService
 */
public abstract class GenericEntityController<E extends GenericEntity<PK>, PK, Q extends Param, VO extends ConvertTo<E> & ConvertFrom<E>> extends CrudController<E, PK, Q, VO> {
    public abstract CrudService<E, PK> getService();
}
