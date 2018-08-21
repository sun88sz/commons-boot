package com.sun.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Integer age;
    private String name;
    private Date createTime;
    private Date birthday;
    private List<String> hobbies;
    private Teacher teacher;
    private List<Teacher> teachers;
}
