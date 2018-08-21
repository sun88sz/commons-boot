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

package com.sun;

import com.kedacom.ctsp.authorization.Permission;
import com.kedacom.ctsp.authorization.annotation.Authorize;
import com.kedacom.ctsp.lang.ClassUtil;
import com.kedacom.ctsp.lang.mapper.ConvertFrom;
import com.kedacom.ctsp.lang.mapper.ConvertTo;
import com.kedacom.ctsp.logging.AccessLogger;
import com.kedacom.ctsp.orm.param.Param;
import com.kedacom.ctsp.web.controller.message.ResponseMessage;
import com.kedacom.ctsp.web.service.CrudService;
import com.sun.web.controller.itf.CreateController;
import com.sun.web.controller.itf.DeleteController;
import com.sun.web.controller.itf.QueryController;
import com.sun.web.controller.itf.UpdateController;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 通用增删改查控制器
 *
 * @author
 * @see QueryController
 * @see CreateController
 * @see UpdateController
 * @see DeleteController
 * @see CrudService
 * @since 3.0
 */
public abstract class CrudController<E, PK, Q extends Param, VO extends ConvertTo<E> & ConvertFrom<E>>
        implements QueryController<E, PK, Q, VO>,
        UpdateController<E, PK, VO>,
        CreateController<E, PK, VO>,
        DeleteController<PK>,
        CommandController<E, PK> {

    private final Class<VO> voType;

    public CrudController() {
        this.voType = (Class<VO>) ClassUtil.getGenericType(this.getClass(), 3);
    }

    @SuppressWarnings("unchecked")
    public abstract CrudService<E, PK> getService();

    @Authorize(action = Permission.ACTION_UPDATE)
    @PatchMapping(path = "/{id}")
    @AccessLogger("{patch_by_primary_key}")
    @ApiOperation("根据ID部分修改数据")
    public ResponseMessage<Integer> patchByPrimaryKey(@PathVariable PK id, @RequestBody VO data) {
        E origin = getService().get(id);
        E source = modelToEntity(data);
        com.kedacom.ctsp.web.controller.util.BeanMapper.deepPatch(source, origin);
        return ResponseMessage.ok(
                getService()
                        .update(id, source));
    }

    @Override
    public E modelToEntity(VO model) {
        E entity = getService().createEntity();
        return model.convertTo(entity);
    }

    @Override
    public VO entityToModel(E entity) {
        VO vo = createModel();
        return vo.convertFrom(entity);
    }

    public VO createModel() {
        return getService().createInstance(voType);
    }

}
