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

import com.kedacom.ctsp.orm.Conditional;
import com.kedacom.ctsp.orm.dsl.Query;
import com.kedacom.ctsp.web.dao.dynamic.QueryByParamDao;
import com.kedacom.ctsp.web.entity.param.QueryParamEntity;
import com.kedacom.ctsp.web.service.QueryService;

import java.util.List;

public interface DefaultDSLQueryService<E, PK>
        extends DefaultQueryByEntityService<E>, QueryService<E, PK> {

    @Override
    default List<E> getAll() {
        return createQuery().noPaging().list();
    }

    @Override
    default long count() {
        return createQuery().total();
    }

    /**
     * 创建本服务的dsl查询操作对象
     * 可通过返回的Query对象进行dsl方式操作如:<br>
     * <code>
     * createQuery().where("id",1).single();
     * </code>
     *
     * @return {@link Query}
     * @see Query
     * @see Conditional
     * @since 3.0
     */
    default Query<E, QueryParamEntity> createQuery() {
        Query<E, QueryParamEntity> query = Query.empty(new QueryParamEntity());
        query.setListExecutor(this::select);
        query.setTotalExecutor(this::count);
        query.setSingleExecutor(this::selectSingle);
        query.noPaging();
        return query;
    }

    /**
     * 指定一个dao映射接口,接口需继承{@link QueryByParamDao}创建dsl数据查询对象<br>
     * 可通过返回的Query对象进行dsl方式操作如:<br>
     * <code>
     * createQuery(userMapper).where("id",1).single();
     * </code>
     *
     * @param dao dao接口
     * @param <E> PO泛型
     * @return {@link Query}
     * @see Query
     * @see Conditional
     * @since 3.0
     */
    static <E> Query<E, QueryParamEntity> createQuery(QueryByParamDao<E> dao) {
        Query<E, QueryParamEntity> query = new Query<>(new QueryParamEntity());
        query.setListExecutor(dao::query);
        query.setTotalExecutor(dao::count);
        query.setSingleExecutor((param) -> {
            param.doPaging(0, 1);
            List<E> list = dao.query(param);
            if (null == list || list.size() == 0) {
                return null;
            } else {
                return list.get(0);
            }
        });
        query.noPaging();
        return query;
    }
}
