package com.example.rigge.Service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rigge.common.R;
import com.example.rigge.dto.DishDto;
import com.example.rigge.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public Boolean saveWithFlavor(DishDto dishDto);

    //分页
    public R<Page> dishdtoPageSeach(int page , int pageSize , String name);

    //根据id查询菜品信息和对应的口味信息
    public R<DishDto> seachDishDto(long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

    public R<List<DishDto>> seachDishDtoandCategroyid(Dish dish);

}
