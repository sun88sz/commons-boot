package com.sun.util;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;

public class ExcelUtilTest {
    @Test
    public void writeLocalFile() {
        HSSFWorkbook book = ExcelUtil.createWorkBook();

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("丁磊"));
        teachers.add(new Teacher("马云"));

        List<Student> list = new ArrayList<>();
        list.add(new Student(10, "A", new Date(), new Date(), Arrays.asList("A", "B", "C"), new Teacher("马化腾"), null));
        list.add(new Student(20, "张三", new Date(), new Date(), Arrays.asList("游泳", "跳高"), null, teachers));

        List<ExcelMapping> mappings = new ArrayList<>();
        ExcelColumnCellStyle columnCellStyle = new ExcelColumnCellStyle(null, 12, 2,true);
        mappings.add(new ExcelMapping("序号", ExcelMapping.AUTO_INDEX, columnCellStyle, 2));
        mappings.add(new ExcelMapping("年龄", "age", columnCellStyle, 2));
        mappings.add(new ExcelMapping("创建时间", "createTime", columnCellStyle, 5));
        mappings.add(new ExcelMapping("生日", "birthday", e -> DateFormatUtils.format((Date) e, "yyyy-MM-dd"), columnCellStyle, 5));
        mappings.add(new ExcelMapping("姓名", "name", columnCellStyle, 4));
        mappings.add(new ExcelMapping("爱好", "hobbies", e -> Joiner.on("、").join((List) e), columnCellStyle, 8));
        mappings.add(new ExcelMapping("班主任老师", "teacher.name", columnCellStyle, 6));
        mappings.add(new ExcelMapping("任课老师", "teachers",
                e -> {
                    List<Teacher> ts = (List) e;
                    List<String> collect = ts.stream().map(
                            t -> {
                                String name = t.getName() + "老师";
                                return name;
                            }
                    ).collect(Collectors.toList());
                    return Joiner.on("+").join(collect);
                }, columnCellStyle, 10)
        );


        ExcelColumnCellStyle headerStyleD = new ExcelColumnCellStyle();
        headerStyleD.setFontSize(16);
        ExcelMapping d = new ExcelMapping("D", 1, 8, headerStyleD);
        d.addChildren(mappings);

        ExcelMapping b = new ExcelMapping("B", 1, 4);
        b.addChildren(d);
        ExcelMapping c = new ExcelMapping("C", 1, 4);

        ExcelColumnCellStyle headerStyleA = new ExcelColumnCellStyle();
        headerStyleA.setFontSize(20);
        headerStyleA.setHorizontalAlign(2);
        ExcelMapping a = new ExcelMapping("A", 1, 8, headerStyleA);
        a.addChildren(b, c);


        List<ExcelMapping> excelMappings = Arrays.asList(a);
        List<ExcelMapping> children = excelMappings.get(0).getChildren();

        ExcelUtil.createSheet(book, "1111", list, excelMappings);
        ExcelUtil.writeLocalFile(book, "D:\\Programming\\workspace");
    }



    @Test
    public void writeLocalFile2() {
        HSSFWorkbook book = ExcelUtil.createWorkBook();

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("丁磊"));
        teachers.add(new Teacher("马云"));

        List<Student> list = new ArrayList<>();
        list.add(new Student(10, "A", new Date(), new Date(), Arrays.asList("A", "B", "C"), new Teacher("马化腾"), null));
        list.add(new Student(20, "张三", new Date(), new Date(), Arrays.asList("游泳", "跳高"), null, teachers));

        List<ExcelMapping> mappings = new ArrayList<>();
        ExcelColumnCellStyle columnCellStyle = new ExcelColumnCellStyle(null, 12, 2,true);
        mappings.add(new ExcelMapping("序号", ExcelMapping.AUTO_INDEX, columnCellStyle, 2,2,1));
        mappings.add(new ExcelMapping("年龄", "age", columnCellStyle, 2,2,1));
        mappings.add(new ExcelMapping("创建时间", "createTime", columnCellStyle, 5,2,1));
        mappings.add(new ExcelMapping("生日", "birthday", e -> DateFormatUtils.format((Date) e, "yyyy-MM-dd"), columnCellStyle, 5,2,1));
        mappings.add(new ExcelMapping("姓名", "name", columnCellStyle, 4,2,1));
        mappings.add(new ExcelMapping("爱好", "hobbies", e -> Joiner.on("、").join((List) e), columnCellStyle, 8,2,1));

        
        ExcelMapping em = new ExcelMapping("老师", 1, 2);
        em.setTitleStyle(columnCellStyle);
        List<ExcelMapping> mappings2 = new ArrayList<>();
        mappings2.add(new ExcelMapping("班主任老师", "teacher.name", columnCellStyle, 6));
        mappings2.add(new ExcelMapping("任课老师", "teachers",
                e -> {
                    List<Teacher> ts = (List) e;
                    List<String> collect = ts.stream().map(
                            t -> {
                                String name = t.getName() + "老师";
                                return name;
                            }
                    ).collect(Collectors.toList());
                    return Joiner.on("+").join(collect);
                }, columnCellStyle, 10)
        );

        em.addChildren(mappings2);


        ExcelColumnCellStyle headerStyleD = new ExcelColumnCellStyle();
        headerStyleD.setFontSize(16);
        ExcelMapping d = new ExcelMapping("D", 1, 8, headerStyleD);
        d.addChildren(mappings);
        d.addChildren(em);

        ExcelMapping b = new ExcelMapping("B", 1, 4);
        b.addChildren(d);
        ExcelMapping c = new ExcelMapping("C", 1, 4);

        ExcelColumnCellStyle headerStyleA = new ExcelColumnCellStyle();
        headerStyleA.setFontSize(20);
        headerStyleA.setHorizontalAlign(2);
        ExcelMapping a = new ExcelMapping("A", 1, 8, headerStyleA);
        a.addChildren(b, c);


        List<ExcelMapping> excelMappings = Arrays.asList(a);
        List<ExcelMapping> children = excelMappings.get(0).getChildren();

        ExcelUtil.createSheet(book, "1111", list, excelMappings);
        ExcelUtil.writeLocalFile(book, "D:\\Programming\\workspace");
    }
}
