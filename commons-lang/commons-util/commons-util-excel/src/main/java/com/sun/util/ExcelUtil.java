package com.sun.util;

import com.google.common.base.Joiner;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Excel 工具类
 */
public class ExcelUtil {

    /**
     * 输出到File
     *
     * @param wb       book
     * @param filePath 文件夹路径
     * @param fileName 文件名（带后缀名）
     */
    public static void writeLocalFile(HSSFWorkbook wb, String filePath, String fileName) {
        // 设置默认文件名为当前时间：年月日时分秒
        if (StringUtils.isBlank(fileName)) {
            fileName = defaultFileName();
        }
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            wb.write(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLocalFile(HSSFWorkbook wb, String filePath) {
        writeLocalFile(wb, filePath, null);
    }

    /**
     * 输出到 http response
     *
     * @param wb
     * @param response
     * @param fileName
     */
    public static void writeHttpResponse(HSSFWorkbook wb, HttpServletResponse response, String fileName) {
        // 设置默认文件名为当前时间：年月日时分秒
        if (StringUtils.isBlank(fileName)) {
            fileName = defaultFileName();
        }
        // 设置response头信息
        response.reset();
        response.setContentType("application/vnd.ms-excel"); // 改成输出excel文件
        try {
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
            //将文件输出
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置默认文件名为当前时间：年月日时分秒
     *
     * @return
     */
    private static String defaultFileName() {
        // 设置默认文件名为当前时间：年月日时分秒
        return DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddhhmmss") + ".xls";
    }

    /**
     * 导出Excel
     *
     * @param sheetName 要导出的excel名称
     * @param list      要导出的数据集合
     * @param mappings  字段映射,即要导出的excel表头
     * @return HSSFWorkbook
     */
    public static <T> HSSFWorkbook createBook(String sheetName, List<T> list, List<ExcelMapping> mappings) {

        //创建一个WorkBook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //在Workbook中，创建一个sheet，对应Excel中的工作薄（sheet）
        return createSheet(wb, sheetName, list, mappings);
    }

    public static <T> HSSFWorkbook createBook() {
        //创建一个WorkBook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        return wb;
    }


    /**
     * 创建一个列表标签
     *
     * @param book      excel
     * @param sheetName 标签名称
     * @param list      内容列表
     * @param mappings  映射列表
     * @return
     */
    public static <T> HSSFWorkbook createSheet(HSSFWorkbook book, String sheetName, List<T> list, List<ExcelMapping> mappings) {
        return createSheet(book, sheetName, list, mappings, null, 0);
    }


    public static <T> HSSFWorkbook createSheet(HSSFWorkbook book, String sheetName, List<T> list, List<ExcelMapping> mappings, List<ExcelHeader> headers, int startRow) {
        //在Workbook中，创建一个sheet，对应Excel中的工作薄（sheet）
        HSSFSheet sheet = book.createSheet(sheetName);
        //创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = book.createCellStyle();
        //创建一个居中格式
        //style.setAlignment(HSSFCellStyle.s);
        // 填充工作表
        try {
            if (headers != null && headers.size() > 0) {
                fillHeader(book, sheet, headers);
            }
            fillSheet(list, mappings, startRow, sheet, style);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    /**
     * 填充自定义表头
     *
     * @param sheet
     * @param headers
     */
    private static void fillHeader(HSSFWorkbook book,HSSFSheet sheet, List<ExcelHeader> headers) {
        Map<Integer, HSSFRow> rows = new HashMap<>();
        headers.stream().forEach(
                h -> {
                    sheet.addMergedRegion(new CellRangeAddress(h.getFirstRow(), h.getLastRow(), h.getFirstCol(), h.getLastCol()));
                    HSSFRow row = rows.get(h.getFirstRow());
                    if (row == null) {
                        row = sheet.createRow(h.getFirstRow());
                        rows.put(h.getFirstRow(), row);
                    }
                    HSSFCell cell = row.createCell(h.getFirstCol());

                    HSSFCellStyle cellStyle = book.createCellStyle();

                    HSSFFont font = book.createFont();
                    font.setBold(true);
                    font.setFontHeightInPoints((short) 20);
                    font.setFontName("黑体");

                    cellStyle.setFont(font);
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cell.setCellValue(h.getContent());

                    cell.setCellStyle(cellStyle);
                }
        );
    }


    /**
     * 根据字段名获取字段对象
     *
     * @param fieldName 字段名
     * @param clazz     包含该字段的类
     * @return 字段
     */
    public static Field getFieldByName(String fieldName, Class<?> clazz) {
        // 根据字段名获取字段对象:getFieldByName()
        // 拿到本类的所有字段
        Field[] selfFields = clazz.getDeclaredFields();

        // 如果本类中存在该字段，则返回
        for (Field field : selfFields) {
            //如果本类中存在该字段，则返回
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        // 否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            //递归
            return getFieldByName(fieldName, superClazz);
        }

        // 如果本类和父类都没有，则返回空
        return null;
    }

    /**
     * 根据字段名获取字段值
     *
     * @param fieldName 字段名
     * @param o         对象
     * @return 字段值
     * @throws Exception 异常
     */
    public static Object getFieldValueByName(String fieldName, Object o)
            throws Exception {

        // 根据字段名获取字段值:getFieldValueByName()");
        Object value = null;
        //根据字段名得到字段对象
        Field field = getFieldByName(fieldName, o.getClass());

        //如果该字段存在，则取出该字段的值
        if (field != null) {
            field.setAccessible(true);//类中的成员变量为private,在类外边使用属性值，故必须进行此操作
            value = field.get(o);//获取当前对象中当前Field的value
        } else {
            throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 "
                    + fieldName);
        }

        return value;
    }

    /**
     * 根据带路径或不带路径的属性名获取属性值,即接受简单属性名，
     * 如userName等，又接受带路径的属性名，如student.department.name等
     *
     * @param fieldNameSequence 带路径的属性名或简单属性名
     * @param o                 对象
     * @return 属性值
     * @throws Exception 异常
     */
    public static Object getFieldValueByNameSequence(String fieldNameSequence, Object o) throws Exception {
        // 根据带路径或不带路径的属性名获取属性值,即接受简单属性名:getFieldValueByNameSequence
        Object value = null;

        // 将fieldNameSequence进行拆分
        String[] attributes = fieldNameSequence.split("\\.");
        if (attributes.length == 1) {
            value = getFieldValueByName(fieldNameSequence, o);
        } else {
            // 根据数组中第一个连接属性名获取连接属性对象，如student.department.name
            Object fieldObj = getFieldValueByName(attributes[0], o);
            if (fieldObj != null) {
                //截取除第一个属性名之后的路径
                String subFieldNameSequence = fieldNameSequence.substring(fieldNameSequence.indexOf(".") + 1);
                //递归得到最终的属性对象的值
                value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
            }
        }
        return value;

    }

    /**
     * 向工作表中填充数据
     *
     * @param list     数据源
     * @param mappings 对应关系的list
     * @param sheet    excel的工作表名称
     * @param style    表格中的格式
     * @param <T>
     * @throws Exception
     */
    public static <T> void fillSheet(List<T> list, List<ExcelMapping> mappings, int startRow, HSSFSheet sheet, HSSFCellStyle style) throws Exception {
        // 向工作表中填充数据:fillSheet()
        if (CollectionUtils.isNotEmpty(mappings)) {
            //  添加表头
            setTitle(sheet, mappings, style, startRow);
            // 添加内容
            setContent(sheet, mappings, list, startRow);
            // 设置列宽
            setColumnWidth(sheet, mappings);
        }
    }


    /**
     * 添加表头
     *
     * @param sheet
     * @param mappings
     * @param style
     */
    private static void setTitle(HSSFSheet sheet, List<ExcelMapping> mappings, HSSFCellStyle style, int startRow) {
        // 在sheet中添加表头第 startRow 行
        HSSFRow row = sheet.createRow(startRow);
        // 填充表头
        for (int i = 0; i < mappings.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(mappings.get(i).getTitle());
            cell.setCellStyle(style);
        }
    }

    /**
     * 设置内容
     *
     * @param sheet
     * @param mappings
     * @param list
     * @throws Exception
     */
    private static <T> void setContent(HSSFSheet sheet, List<ExcelMapping> mappings, List<T> list, int startRow) throws Exception {
        // 填充内容
        for (int n = 0; n < list.size(); n++) {
            HSSFRow row = sheet.createRow(n + startRow);
            // 获取单个对象
            T item = list.get(n);
            for (int i = 0; i < mappings.size(); i++) {
                ExcelMapping mapping = mappings.get(i);
                Object objValue = getFieldValueByNameSequence(mapping.getField(), item);

                if (mapping.getFunction() != null && objValue != null) {
                    objValue = mapping.getFunction().apply(objValue);
                }

                String fieldValue = defaultConvert(objValue);
                row.createCell(i).setCellValue(fieldValue);
            }
        }

    }

    /**
     * 设置列宽
     *
     * @param sheet
     * @param mappings
     */
    private static void setColumnWidth(HSSFSheet sheet, List<ExcelMapping> mappings) {
        // 设置默认列宽，width为字符个数
        //  sheet.setDefaultColumnWidth( int width);

        for (int i = 0; i < mappings.size(); i++) {
            // 设置默认列宽，width为字符个数
            // 设置第columnIndex+1列的列宽，单位为字符宽度的1/256
            ExcelMapping mapping = mappings.get(i);
            if (mapping.getColumnWidth() != null && mapping.getColumnWidth() != 0) {
                sheet.setColumnWidth(i, mapping.getColumnWidth() * 256);
            } else {
                sheet.autoSizeColumn(i);
            }
        }
    }

    /**
     * 默认转换一些非String类型
     *
     * @param objValue
     * @return
     */
    private static String defaultConvert(Object objValue) {
        String fieldValue;
        // 默认的时间转换 可在Function中自己转换
        if (objValue instanceof Date) {
            fieldValue = DateFormatUtils.format((Date) objValue, "yyyy-MM-dd HH:mm:ss");
        } else if (objValue instanceof Number) {
            fieldValue = String.valueOf(objValue);
        }
        // 集合
        else if (objValue instanceof Collection) {
            fieldValue = Joiner.on(",").join((Collection) objValue);
        } else {
            fieldValue = objValue == null ? "" : objValue.toString();
        }
        return fieldValue;
    }

}