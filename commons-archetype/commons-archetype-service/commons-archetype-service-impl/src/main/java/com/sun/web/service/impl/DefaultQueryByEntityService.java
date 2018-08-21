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

import com.kedacom.ctsp.orm.dsl.Query;
import com.kedacom.ctsp.orm.param.Param;
import com.kedacom.ctsp.orm.param.QueryParam;
import com.kedacom.ctsp.web.dao.dynamic.QueryByParamDao;
import com.kedacom.ctsp.web.entity.page.PagerResult;
import com.kedacom.ctsp.web.entity.param.QueryParamEntity;
import com.kedacom.ctsp.web.service.QueryByEntityService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

public interface DefaultQueryByEntityService<E> extends QueryByEntityService<E> {

    <Q extends Param> QueryByParamDao<E> getDao();

    /**
     * 分页进行查询数据，查询条件同 {@link DefaultQueryByEntityService#select}
     *
     * @param param 查询参数
     * @return 分页查询结果
     */
    @Override
    default <Q extends Param> PagerResult<E> selectPager(Q param) {
        PagerResult<E> pagerResult = new PagerResult<>();
        if (param instanceof QueryParam) {
            QueryParam entity = ((QueryParam) param);
            if (entity.isPaging()) {
                long total = getDao().count(param);
                pagerResult.setPage(entity);
                pagerResult.setTotal(total);
                if (total == 0) {
                    pagerResult.setData(Collections.emptyList());
                } else {
                    //根据实际记录数量重新指定分页参数
                    entity.rePaging(total);
                    pagerResult.setData(getDao().query(param));
                }
                return pagerResult;
            }
        }
        //不分页,不进行count
        pagerResult.setData(getDao().query(param));
        pagerResult.setTotal(pagerResult.getData().size());
        return pagerResult;

    }

    /**
     * 根据查询参数进行查询，参数可使用 {@link Query}进行构建
     *
     * @param param 查询参数
     * @return 查询结果
     * @see QueryParamEntity
     */
    @Override
    @Transactional(readOnly = true)
    default <Q extends Param> List<E> select(Q param) {
        if (param == null) {
            param = (Q) QueryParamEntity.empty();
        }
        return getDao().query(param);
    }


    /**
     * 查询记录总数，用于分页等操作。查询条件同 {@link DefaultQueryByEntityService#select}
     *
     * @param param 查询参数
     * @return 查询结果，实现mapper中的sql应指定默认值，否则可能抛出异常
     */
    @Override
    @Transactional(readOnly = true)
    default <Q extends Param> long count(Q param) {
        if (param == null) {
            param = (Q) QueryParamEntity.empty();
        }
        return getDao().count(param);
    }

    /**
     * 查询只返回单个结果,如果有多个结果,只返回第一个
     *
     * @param param 查询条件
     * @return 单个查询结果
     */
    @Override
    @Transactional(readOnly = true)
    default <Q extends Param> E selectSingle(Q param) {
        if (param instanceof QueryParamEntity)
            ((QueryParamEntity) param).doPaging(0, 1);
        List<E> list = this.select(param);
        if (list.size() == 0) return null;
        else return list.get(0);
    }

}
