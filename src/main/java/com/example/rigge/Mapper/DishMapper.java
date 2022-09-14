package com.example.rigge.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rigge.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
