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
import com.sun.web.service.DeleteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 通用删除控制器
 *
 * @author
 */
public interface DeleteController<PK> {

    DeleteService<PK> getService();

    @DeleteMapping(path = "/{id:.+}")
    @ApiOperation("根据ID删除数据")
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "无权限"),
            @ApiResponse(code = 404, message = "要删除的数据不存在")
    })
    default ResponseMessage deleteByPrimaryKey(@PathVariable PK id) {
        return ResponseMessage.ok(getService().delete(id));
    }

}
