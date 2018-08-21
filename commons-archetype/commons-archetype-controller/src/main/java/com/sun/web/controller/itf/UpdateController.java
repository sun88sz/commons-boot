
package com.sun.web.controller.itf;


import com.sun.web.result.ResponseMessage;
import com.sun.web.service.UpdateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 通用更新控制器
 *
 * @author
 */
public interface UpdateController<E, PK, VO> {
    <S extends UpdateService<E, PK>> S getService();

    @PutMapping(path = "/{id}")
    @ApiOperation("根据ID修改数据")
    default ResponseMessage<Integer> updateByPrimaryKey(@PathVariable PK id, @RequestBody VO data) {
        return ResponseMessage.ok(getService().update(id, modelToEntity(data)));
    }

    @PutMapping
    @ApiOperation("保存数据,如果数据不存在则新增一条数据")
    default ResponseMessage<PK> saveOrUpdate(@RequestBody VO data) {
        return ResponseMessage.ok(getService().saveOrUpdate(modelToEntity(data)));
    }

    /**
     * 将model转为entity
     *
     * @param model
     * @return 转换后的结果
     * @see Model
     */
    E modelToEntity(VO model);
}
