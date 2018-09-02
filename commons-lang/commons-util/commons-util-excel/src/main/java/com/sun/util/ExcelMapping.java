package com.sun.util;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * title 和 字段 映射
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelMapping {

    /**
     * 顺序序号
     */
    public static String AUTO_INDEX = "AUTO_INDEX";

    /**
     * 如果是列头
     */
    private Object content;
    /**
     * 列头
     */
    private String title;
    /**
     * 字段
     */
    private String field;


    /**
     * 占几行 默认1
     */
    private int rows = 1;
    /**
     * 占几列 默认1
     */
    private int cols = 1;


    /**
     * 如果该列为空 显示的内容
     * 不设置则 为空时不显示
     */
    private Object nullValue;


    private ExcelMapping parent;
    private List<ExcelMapping> children;


    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;


    /**
     * 简单列内容样式
     */
    private ExcelColumnCellStyle columnStyle;

    /**
     * 简单列头样式
     */
    private ExcelColumnCellStyle titleStyle;

    /**
     * 转换函数
     */
    private Function function;

    /**
     * 添加子集
     *
     * @param excelMappings
     * @return
     */
    public ExcelMapping addChildren(ExcelMapping... excelMappings) {
        if (CollectionUtils.isEmpty(children)) {
            children = Lists.newArrayList();
        }
        List<ExcelMapping> children = Arrays.asList(excelMappings);
        if (CollectionUtils.isNotEmpty(children)) {
            this.children.addAll(children);
            children.stream().forEach(e -> e.setParent(this));
        }
        return this;
    }

}
