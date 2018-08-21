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

import com.sun.web.result.ResponseMessage;
import com.sun.web.service.InsertService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 通用新增控制器<br>
 * 使用:实现该接口,注解@RestController 以及@RequestMapping("/myController")
 * 客户端调用: 通过POST请求,contentType为application/json 。参数为E泛型的json格式
 * <pre>
 * curl -l -H "Content-type: application/json" -X POST -d '{"field1":"value1","field2":"value2"}' http://domain/contextPath/myController
 * </pre>
 *
 * @author
 * @since 3.0
 */
public interface CreateController<E, PK, VO> {

    <S extends InsertService<E, PK>> S getService();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建数据", responseReference = "add")
    @ApiResponses({
            @ApiResponse(code = 201, message = "创建成功,返回创建数据的ID"),
            @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "无权限")
    })
    default ResponseMessage<PK> add(@RequestBody VO data) {
        return ResponseMessage.ok(getService().insert(modelToEntity(data)));
    }

    E modelToEntity(VO model);
}
