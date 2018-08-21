package com.sun.example.po;

import com.sun.entity.PO;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("tb_student")
@Data
public class Student implements PO {

    @TableId(value="id",type=IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("no")
    private String no;

    private Integer gender;
    private Integer age;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;



    // setter和getter方法省略


}