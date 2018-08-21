package com.sun.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Description: <br/>
 * Date: 2018-08-06
 *
 * @author Sun
 */
public class PageResult {
}

@ApiModel(description = "分页结果")
@Data
public class PagerResult<E> implements Serializable {
    private static final long serialVersionUID = -6171751136953308027L;

    public static <E> PagerResult<E> empty() {
        return new PagerResult<>(0, Collections.emptyList());
    }

    public static <E> PagerResult<E> of(int total, List<E> list) {
        return new PagerResult<>(total, list);
    }

    public PagerResult() {
    }

    @ApiModelProperty("数据总数量")
    private long total;

    /**
     * 第几页 从0开始
     */
    @ApiModelProperty("PageNo")
    private int pageNo = 0;

    /**
     * 每页显示记录条数
     */
    @ApiModelProperty("PageSize")
    private int pageSize;

    @ApiModelProperty("查询结果")
    private List<E> data;

    public PagerResult(long total, List<E> data) {
        this.total = total;
        this.data = data;
    }

    public void setPage(QueryParam param) {
        this.pageNo = param.getPageIndex();
        this.pageSize = param.getPageSize();
    }

    public <VO> PagerResult<VO> convertTo(Function<E, VO> converter) {
        PagerResult newResult = new PagerResult();
        newResult.setTotal(this.total);
        newResult.setPageNo(this.pageNo);
        newResult.setPageSize(this.pageSize);
        List<VO> data = BeanMapper.convert(this.data, converter);
        newResult.setData(data);
        return newResult;
    }
}