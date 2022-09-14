package com.example.rigge.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rigge.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: EmployeeMapper
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/2 18:39
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {


}
