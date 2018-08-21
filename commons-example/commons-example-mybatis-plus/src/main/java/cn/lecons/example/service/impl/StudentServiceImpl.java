package com.sun.example.service.impl;

import com.sun.example.entity.Student;
import com.sun.example.dao.StudentMapper;
import com.sun.example.service.StudentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sun
 * @since 2018-07-16
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
