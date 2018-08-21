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

import com.kedacom.ctsp.web.dao.CrudDao;
import com.kedacom.ctsp.web.entity.GenericEntity;
import com.kedacom.ctsp.web.service.CrudService;

import java.io.Serializable;

/**
 * @author
 * @see DefaultDSLQueryService
 * @see DefaultDSLUpdateService
 * @see DefaultDSLDeleteService
 * @see CrudService
 * @see CrudDao
 */
public interface GenericService<E extends GenericEntity<PK>, PK extends Serializable> extends
        DefaultDSLQueryService<E, PK>,
        DefaultDSLUpdateService<E, PK>,
        DefaultDSLDeleteService<PK>,
        CrudService<E, PK> {

    @Override
    CrudDao<E, PK> getDao();
}
