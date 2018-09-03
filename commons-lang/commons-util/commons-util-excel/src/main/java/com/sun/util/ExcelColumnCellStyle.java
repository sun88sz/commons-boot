package com.sun.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Sun
 * @date : 2018/8/22 09:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelColumnCellStyle implements Cloneable {

    /**
     * 列宽
     */
    private Integer columnWidth;

    /**
     * 行高
     */
    private Integer rowHeight;

    /**
     * 字体大小
     */
    private Integer fontSize;

    /**
     * 字体
     */
    private String fontName;

    /**
     * 是否加粗
     */
    private Boolean bold = false;

    /**
     * 水平方向对齐方式（1左对齐 2居中 3右对齐）
     */
    private Integer horizontalAlign = 1;

    /**
     * 垂直方向对齐方式（1上对齐 2居中 3下对齐）
     */
    private Integer verticalAlign = 2;

    public ExcelColumnCellStyle(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    public ExcelColumnCellStyle(Integer columnWidth, Integer fontSize, Boolean bold) {
        this.columnWidth = columnWidth;
        this.fontSize = fontSize;
        this.bold = bold;
    }

    public ExcelColumnCellStyle(Integer fontSize, String fontName, Integer horizontalAlign) {
        this.fontSize = fontSize;
        this.fontName = fontName;
        this.horizontalAlign = horizontalAlign;
    }


    public ExcelColumnCellStyle(Integer columnWidth, Integer fontSize, Integer horizontalAlign, Boolean bold) {
        this(columnWidth, fontSize, bold);
        this.horizontalAlign = horizontalAlign;
    }

    public ExcelColumnCellStyle(Integer columnWidth, Integer rowHeight, Integer fontSize, String fontName, Boolean bold) {
        this.columnWidth = columnWidth;
        this.rowHeight = rowHeight;
        this.fontSize = fontSize;
        this.fontName = fontName;
        this.bold = bold;
    }


    @Override
    protected ExcelColumnCellStyle clone() {
        try {
            return (ExcelColumnCellStyle) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
