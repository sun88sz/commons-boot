package com.sun.util;


import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

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
        List<ExcelMapping> childrenMappings = Arrays.asList(excelMappings);
        addChildren(childrenMappings);
        return this;
    }

    public ExcelMapping addChildren(List<ExcelMapping> mappings) {
        if (CollectionUtils.isEmpty(children)) {
            children = Lists.newArrayList();
        }
        if (CollectionUtils.isNotEmpty(mappings)) {
            this.children.addAll(mappings);
            mappings.stream().forEach(e -> e.setParent(this));
        }
        return this;
    }


    public ExcelMapping(String title, String field) {
        this.title = title;
        this.field = field;
    }

    public ExcelMapping(String title, String field, int columnWidth) {
        this.title = title;
        this.field = field;
        this.titleStyle = new ExcelColumnCellStyle(columnWidth);
    }


    public ExcelMapping(String title, String field, ExcelColumnCellStyle titleStyle) {
        this.title = title;
        this.field = field;
        this.titleStyle = titleStyle;
    }


    public ExcelMapping(String title, String field, ExcelColumnCellStyle titleStyle, Integer columnWidth) {
        this(title, field, titleStyle);
        this.titleStyle.setColumnWidth(columnWidth);
    }


    public ExcelMapping(String title, String field, ExcelColumnCellStyle titleStyle, Integer columnWidth, Integer rows, Integer cols) {
        this(title, field, titleStyle);
        this.rows = rows;
        this.cols = cols;
        this.titleStyle.setColumnWidth(columnWidth);
    }

    public ExcelMapping(String title, String field, Function function) {
        this(title, field, function, null);
    }

    public ExcelMapping(String title, String field, Function function, int columnWidth) {
        this(title, field, function, new ExcelColumnCellStyle(columnWidth));
    }

    public ExcelMapping(String title, String field, Function function, int columnWidth, Integer rows, Integer cols) {
        this(title, field, function, new ExcelColumnCellStyle(columnWidth));
        this.rows = rows;
        this.cols = cols;
    }

    public ExcelMapping(String title, String field, Function function, ExcelColumnCellStyle titleStyle) {
        this.title = title;
        this.field = field;
        this.function = function;

        this.titleStyle = titleStyle.clone();
    }

    public ExcelMapping(String title, String field, Function function, ExcelColumnCellStyle titleStyle, int columnWidth) {
        this(title, field, function, titleStyle);
        this.titleStyle.setColumnWidth(columnWidth);
    }


    public ExcelMapping(String title, String field, Function function, ExcelColumnCellStyle titleStyle, int columnWidth, Integer rows, Integer cols) {
        this(title, field, function, titleStyle);
        this.rows = rows;
        this.cols = cols;
        this.titleStyle.setColumnWidth(columnWidth);
    }

    public ExcelMapping(String content, int rows, int cols) {
        this.content = content;
        this.rows = rows;
        this.cols = cols;
    }


    public ExcelMapping(String content, int rows, int cols, ExcelColumnCellStyle headerStyle) {
        this(content, rows, cols);
        this.titleStyle = headerStyle;
    }


    @Override
    public String toString() {
        return "ExcelMapping{" +
                "content=" + content +
                ", title='" + title + '\'' +
                ", field='" + field + '\'' +
                ", rows=" + rows +
                ", cols=" + cols +
                ", columnStyle=" + columnStyle +
                ", titleStyle=" + titleStyle +
                '}';
    }
}
