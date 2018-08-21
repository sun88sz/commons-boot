/*
 * Copyright 2016 http://www.hswebframework.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.sun.web.controller.itf;

import com.sun.web.controller.Param;
import com.sun.web.result.ResponseMessage;
import com.sun.web.service.QueryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;



/**
 * 通用查询控制器。
 *
 * @param <E>  实体类型
 * @param <PK> 主键类型
 * @param <Q>  查询条件实体类型,默认提供{@link QueryParamEntity}实现
 * @author
 * @see QueryParamEntity
 * @see 3.0
 */
public interface QueryController<E, PK, Q extends Param, VO> {

    /**
     * 获取实现了{@link QueryByEntityService}和{@link QueryService}的服务类
     *
     * @param <T> 服务类泛型
     * @return 服务类实例
     */
    <T extends QueryByEntityService<E> & QueryService<E, PK>> T getService();

    /**
     * 根据参数动态查询。<br>
     * 参数泛型如果为QueryParamEntity,
     * 客户的参数 ?terms[0].column=name&terms[0].value=小明 或者?filter_name&terms[0].value=小明
     * 则执行查询条件 where name = '小明'
     * 具体使用方法参照 {@link QueryParamEntity}
     *
     * @param param 参数
     * @return 查询结果
     */
    @GetMapping
    @ApiOperation(value = "根据动态条件查询数据", responseReference = "get")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "无权限")
    })
    default ResponseMessage<PagerResult<VO>> list(Q param, HttpServletRequest request) {
        QueryParamUtil.paddingTerms(param, request);
        PagerResult<E> pagerResult = getService().selectPager(param);
        return ok(pagerResult.convertTo(this::entityToModel));
    }

    @GetMapping(path = "/{id:.+}")
    @ApiOperation("根据主键查询数据")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "数据不存在")
    })
    default ResponseMessage<VO> getByPK(@PathVariable PK id) {
        E result = assertNotNull(getService().get(id));
        return ResponseMessage. ok(entityToModel(result));
    }

    VO entityToModel(E entity);

    static <T> T assertNotNull(T obj) {
        if (null == obj) throw new NotFoundException("{data_not_exist}");
        return obj;
    }

}
