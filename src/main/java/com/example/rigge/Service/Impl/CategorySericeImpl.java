package com.example.rigge.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rigge.Mapper.CategoryMapper;
import com.example.rigge.Service.CategoryService;
import com.example.rigge.Service.DishService;
import com.example.rigge.Service.SetmealService;
import com.example.rigge.common.CustomException;
import com.example.rigge.entity.Category;
import com.example.rigge.entity.Dish;
import com.example.rigge.entity.Setmeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: CategorySericeImpl
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/3 17:46
 */
@Service
@Slf4j
public class CategorySericeImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    public DishService dishService;

    @Lazy
    @Autowired
    public SetmealService setmealService;

    @Override
    public boolean removeCategroy(long id) {
        log.info("removeCategroy:{}",id);
        //是否关联菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getId,id);

        int disCount = dishService.count(dishLambdaQueryWrapper);
        if (disCount != 0){
            //抛出异常
             throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getId,id);

        int setumealCount = setmealService.count(setmealLambdaQueryWrapper);
        if (setumealCount != 0){
            //抛出异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }


        return super.removeById(id) ;
    }
}
