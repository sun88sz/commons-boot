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

package com.sun.web.dao.dynamic;


import com.sun.web.dao.Dao;
import com.sun.web.dao.Param;

import java.util.List;

/**
 * 根据实体类动态查询，可传入特定的实体类进行查询。
 * 目前支持{@link } 动态查询
 *
 * @author
 * @since 3.0
 */
public interface QueryByParamDao<E> extends Dao {

    <Q extends Param> List<E> query(Q queryEntity);

    <Q extends Param> long count(Q queryEntity);
}
