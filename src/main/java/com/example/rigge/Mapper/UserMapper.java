package com.example.rigge.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rigge.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
