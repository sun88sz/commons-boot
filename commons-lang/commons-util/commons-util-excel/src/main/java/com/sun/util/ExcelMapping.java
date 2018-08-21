package com.sun.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Function;

@Data
@AllArgsConstructor
class ExcelMapping {
    /**
     * 列头
     */
    private String title;
    /**
     * 字段
     */
    private String field;
    /**
     * 列宽
     */
    private Integer columnWidth;
    /**
     * 转换函数
     */
    private Function function;

    public ExcelMapping(String title, String field) {
        this(title, field, null, null);
    }

    public ExcelMapping(String title, String field, Integer columnWidth) {
        this(title, field, columnWidth, null);
    }

    public ExcelMapping(String title, String field, Function function) {
        this(title, field, null, function);
    }
}
