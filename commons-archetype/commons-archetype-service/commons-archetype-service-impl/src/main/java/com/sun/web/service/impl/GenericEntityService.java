/*
 *
 *  * Copyright 2016 http://www.hswebframework.org
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.sun.web.service.impl;

import com.kedacom.ctsp.lang.exception.NotFoundException;
import com.kedacom.ctsp.lang.exception.NotSupportedException;
import com.kedacom.ctsp.lang.id.IDGenerator;
import com.kedacom.ctsp.web.dao.CrudDao;
import com.kedacom.ctsp.web.entity.CreatorRecordableEntity;
import com.kedacom.ctsp.web.entity.GenericEntity;
import com.kedacom.ctsp.web.entity.StatefulEntity;
import com.kedacom.ctsp.web.service.Command;
import com.kedacom.ctsp.web.service.CommandFactory;
import com.kedacom.ctsp.web.service.CrudService;
import com.kedacom.ctsp.web.service.simple.command.StatusCommandFactory;
import com.kedacom.ctsp.web.validator.group.CreateGroup;
import com.kedacom.ctsp.web.validator.group.UpdateGroup;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用实体服务类，提供增删改查的默认实现
 *
 * @author
 */
@Transactional(rollbackFor = Throwable.class)
public abstract class GenericEntityService<E extends GenericEntity<PK>, PK extends Serializable>
        extends AbstractService<E, PK>
        implements GenericService<E, PK> {

    @SuppressWarnings("unchecked")
    public GenericEntityService() {
        super();
    }

    /**
     * 获取ID生成器,在insert的时候，如果ID为空,则调用生成器进行生成
     *
     * @return IDGenerator
     * @see IDGenerator
     */
    protected IDGenerator<PK> getIDGenerator() {
        return IDGenerator.AUTO;
    }

    @Setter
    protected CommandFactory commandFactory = new StatusCommandFactory();

    @Override
    public abstract CrudDao<E, PK> getDao();

    @Override
    public int delete(PK pk) {
        Assert.notNull(pk, "parameter can not be null");
        return createDelete()
                .where(GenericEntity.ID, pk)
                .exec();
    }

    @Override
    public int update(PK pk, E entity) {
        Assert.notNull(pk, "primary key can not be null");
        Assert.notNull(entity, "entity can not be null");
        entity.setId(pk);
        tryValidate(entity, UpdateGroup.class);
        return createUpdate(entity)
                //如果是CreatorRecordableEntity则不修改creator_id和creator_time
                .when(entity instanceof CreatorRecordableEntity,
                        update -> update.and().excludes(CreatorRecordableEntity.CREATOR_ID, CreatorRecordableEntity.CREATE_TIME))
                .where(GenericEntity.ID, pk)
                .exec();
    }

    @Override
    public int update(E entity) {
        return update(entity.getId(), entity);
    }


    @Override
    public int update(List<E> data) {
        return data.stream()
                .map(this::update)
                .reduce(Math::addExact)
                .orElse(0);
    }

    @Override
    public PK saveOrUpdate(E entity) {
        if (null != entity.getId() && null != get(entity.getId())) {
            update(entity);
        } else {
            insert(entity);
        }
        return entity.getId();
    }

    @Override
    public PK insert(E entity) {
        if (entity.getId() != null) {
            if ((entity.getId() instanceof String)) {
                tryValidateProperty(entity.getId().toString().matches("[a-zA-Z0-9_\\-]+"), "id", "只能由数字,字母,下划线,和-组成");
            }
            tryValidateProperty(get(entity.getId()) == null, "id", entity.getId() + "已存在");
        }
        if (entity.getId() == null) {
            entity.setId(getIDGenerator().generate());
        }
        tryValidate(entity, CreateGroup.class);
        return getDao().insert(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public E get(PK pk) {
        if (null == pk) {
            return null;
        }
        return createQuery().where(GenericEntity.ID, pk).single();
    }

    @Override
    public List<E> get(List<PK> id) {
        if (id == null || id.isEmpty()) return new ArrayList<>();
        return createQuery().where().in(GenericEntity.ID, id).listNoPaging();
    }

    @Override
    public int submit(E entity, String commandId, String comment) {
        Command command = commandFactory.get(commandId);
        if (command == null) {
            throw new NotFoundException(String.format("command[id:%s] not found", commandId));
        }
        if (!(entity instanceof StatefulEntity)) {
            throw new NotSupportedException(String.format("command mode not supported for entity[%s], must extends from StatefulEntity", entity.getClass()));
        }
        return command.runCommand((StatefulEntity) entity, (CrudService) this);
    }
}
