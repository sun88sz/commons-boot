package com.sun.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

//        List<ExcelMapping> mappings = new ArrayList<>();
//        mappings.add(new ExcelMapping("年龄", "age"));
//        mappings.add(new ExcelMapping("创建时间", "createTime"));
//        mappings.add(new ExcelMapping("生日", "birthday", e -> DateFormatUtils.format((Date) e, "yyyy-MM-dd")));
//        mappings.add(new ExcelMapping("姓名", "name", 7));
//        mappings.add(new ExcelMapping("爱好", "hobbies", 10, e -> Joiner.on("、").join((List) e)));
//        mappings.add(new ExcelMapping("班主任老师", "teacher.name"));
//        mappings.add(new ExcelMapping("任课老师", "teachers",
//                e -> {
//                    List<Teacher> ts = (List) e;
//                    List<String> collect = ts.stream().map(
//                            t -> {
//                                String name = t.getName() + "老师";
//                                return name;
//                            }
//                    ).collect(Collectors.toList());
//                    return Joiner.on("+").join(collect);
//                })
//        );
//
//        List<ExcelHeader> headers =new ArrayList<>();
//        headers.add(new ExcelHeader( 0,0,0,6,"xxxxx" ));
//        headers.add(new ExcelHeader( 1,1,0,2,"yy" ));
//        headers.add(new ExcelHeader( 1,1,3,6,"zz" ));
//
//        headers.add(new ExcelHeader( 2,3,0,0,"a" ));
//        headers.add(new ExcelHeader( 2,3,1,2,"b" ));
//        headers.add(new ExcelHeader( 2,3,3,3,"c" ));
//        headers.add(new ExcelHeader( 2,3,4,5,"d" ));
//        headers.add(new ExcelHeader( 2,3,6,6,"e" ));
//
//
//        ExcelUtil.createSheet(book, "1111", list, mappings,headers,4);
//        ExcelUtil.writeLocalFile(book, "D:\\Programming\\workspace");
    }
}
