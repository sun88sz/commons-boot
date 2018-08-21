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

import com.kedacom.ctsp.orm.dsl.Update;
import com.kedacom.ctsp.web.dao.dynamic.UpdateByParamDao;
import com.kedacom.ctsp.web.entity.param.UpdateParamEntity;
import com.kedacom.ctsp.web.service.UpdateService;

/**
 * 默认的DSL方式更新服务
 *
 * @author
 */
public interface DefaultDSLUpdateService<E, PK> extends UpdateService<E, PK> {

    UpdateByParamDao getDao();

    default Update<E, UpdateParamEntity<E>> createUpdate(E data) {
        return createUpdate(getDao(), data);
    }

    default Update<E, UpdateParamEntity<E>> createUpdate() {
        return createUpdate(getDao());
    }

    static <E> Update<E, UpdateParamEntity<E>> createUpdate(UpdateByParamDao<E> dao) {
        return Update.build(dao::update, new UpdateParamEntity());
    }

    static <E> Update<E, UpdateParamEntity<E>> createUpdate(UpdateByParamDao<E> dao, E data) {
        return Update.build(dao::update, new UpdateParamEntity<>(data));
    }
}
