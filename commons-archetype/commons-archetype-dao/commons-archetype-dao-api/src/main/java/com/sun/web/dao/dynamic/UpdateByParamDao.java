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

/**
 * 根据实体类进行更新,实体类支持动态条件或者普通实体类。
 * 动态条件和{@link QueryByParamDao} 一致。
 *
 * @author
 * @since 3.0
 */
public interface UpdateByParamDao<E> extends Dao {

//    int update(UpdateParamEntity<E> entity);

}
