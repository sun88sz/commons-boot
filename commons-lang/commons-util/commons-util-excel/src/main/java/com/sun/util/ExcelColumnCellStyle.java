package com.sun.util;

/**
 * @author : Sun
 * @date : 2018/8/22 09:49
 */
public class ExcelColumnCellStyle {
    
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
    private Integer fontSize = 12;
    
    /**
     * 字体
     */
    private String fontName;
    
    /**
     * 是否加粗
     */
    private Boolean bold = false;
    
    /**
     * 垂直对齐方式（1左对齐 2居中 3右对齐）
     */
    private Integer horizontalAlign = 1;

    /**
     * 水平对齐方式（1上对齐 2居中 3下对齐）
     */
    private Integer verticalAlign = 2;
    
    
    public static ExcelColumnCellStyle create(Integer columnWidth){
        return new ExcelColumnCellStyle(columnWidth);
    }

    public ExcelColumnCellStyle() {
    }

    public ExcelColumnCellStyle(Integer columnWidth, Integer rowHeight, Integer fontSize, String fontName, Boolean bold, Integer horizontalAlign, Integer verticalAlign) {
        this.columnWidth = columnWidth;
        this.rowHeight = rowHeight;
        this.fontSize = fontSize;
        this.fontName = fontName;
        this.bold = bold;
        this.horizontalAlign = horizontalAlign;
        this.verticalAlign = verticalAlign;
    }

    public ExcelColumnCellStyle(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    public ExcelColumnCellStyle(Integer columnWidth, String fontName, Boolean bold) {
        this.columnWidth = columnWidth;
        this.fontName = fontName;
        this.bold = bold;
    }

    public ExcelColumnCellStyle(Integer fontSize, String fontName, Integer horizontalAlign) {
        this.fontSize = fontSize;
        this.fontName = fontName;
        this.horizontalAlign = horizontalAlign;
    }

    public ExcelColumnCellStyle(Integer columnWidth, Integer rowHeight, Integer fontSize, String fontName, Boolean bold) {
        this.columnWidth = columnWidth;
        this.rowHeight = rowHeight;
        this.fontSize = fontSize;
        this.fontName = fontName;
        this.bold = bold;
    }

    public Integer getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    public Integer getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(Integer rowHeight) {
        this.rowHeight = rowHeight;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public Integer getHorizontalAlign() {
        return horizontalAlign;
    }

    public void setHorizontalAlign(Integer horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }

    public Integer getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(Integer verticalAlign) {
        this.verticalAlign = verticalAlign;
    }
}
