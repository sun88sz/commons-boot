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

package com.sun.web.service;

import com.kedacom.ctsp.orm.param.Param;
import com.kedacom.ctsp.web.entity.page.PagerResult;
import com.kedacom.ctsp.web.entity.param.QueryParamEntity;

import java.util.List;

/**
 * 根据实体类参数执行各种查询的通用服务类
 *
 * @param <E> 实体类型
 * @author
 * @see QueryParamEntity
 * @since 3.0
 */
public interface QueryByExampleService<E> extends Service {

    /**
     * 按分页查询
     *
     * @param param 参数
     * @return 分页查询结果
     */
    <Q extends Param> PagerResult<E> selectPager(Q param);

    /**
     * 直接查询
     *
     * @param param 查询参数
     * @return 查询结果
     */
    <Q extends Param> List<E> select(Q param);

    /**
     * 查询总数
     *
     * @param param 查询参数
     * @return 总数
     */
    <Q extends Param> long count(Q param);

    /**
     * 查询单条数据,如果存在多条数据,则返回第一条
     *
     * @param param 查询参数
     * @return 查询结果
     */
    <Q extends Param> E selectSingle(Q param);
}
