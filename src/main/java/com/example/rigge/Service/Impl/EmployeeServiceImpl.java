package com.example.rigge.Service.Impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rigge.Mapper.EmployeeMapper;
import com.example.rigge.Service.EmployeeService;
import com.example.rigge.entity.Employee;
import org.springframework.stereotype.Service;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: EmployeeServiceImpl
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/2 18:40
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{

}
