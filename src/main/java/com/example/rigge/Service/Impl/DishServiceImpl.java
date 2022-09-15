package com.example.rigge.Service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rigge.Mapper.DishMapper;
import com.example.rigge.Service.CategoryService;
import com.example.rigge.Service.DishFlavorService;
import com.example.rigge.Service.DishService;
import com.example.rigge.common.R;
import com.example.rigge.dto.DishDto;
import com.example.rigge.entity.Category;
import com.example.rigge.entity.Dish;
import com.example.rigge.entity.DishFlavor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    public DishFlavorService dishFlavorService;

    @Lazy
    @Autowired
    public CategoryService categorySerice;

    @Override
    public Boolean saveWithFlavor(DishDto dishDto) {
        log.info("saveWithFlavor..........");
        this.save(dishDto);

        long id = dishDto.getId();
        List<DishFlavor> flavor =  dishDto.getFlavors();
        flavor = flavor.stream().map((item) -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());
        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavor);
        return true;
    }

    /**
      * @Author: Promiseme
      * @Title: dishuudtoPageSeach 查询菜品分页数据
      * @Description:
      * @param @param
      * @return @return
      * @throws
      * @Date: 2022/9/4 18:40
      */
    @Override
    public R<Page> dishdtoPageSeach( int page , int pageSize ,String name) {
        log.info("dishuudtoPageSeach");

        //查询菜品分页
        Page<Dish> dishPage = new Page<>(page,pageSize);
        //菜品分页包装
        Page<DishDto> dishDtoPage = new Page<>(page,pageSize);


        //dishPage条件构造器
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(name != null , Dish::getName , name)
                .orderByAsc(Dish::getUpdateTime)
                .orderByDesc(Dish::getCreateTime);

        //执行分页查询
        this.page(dishPage,dishLambdaQueryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(dishPage,dishDtoPage,"records");

        List<Dish> records = dishPage.getRecords();
        List<DishDto> recordsdto =    records.stream().map((item) -> {
            //创建dishDto对象copy每一个dish里Records里的东西
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            //获取商品分类ID
            Long categoryId = item.getCategoryId();

            Category category = categorySerice.getById(categoryId);
            if (category.equals(null)){
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());
        //将前面忽略的Records补齐
        dishDtoPage.setRecords(recordsdto);

        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Override
    public R<DishDto> seachDishDto(long id) {
        //查询菜品基本信息，从dish表查询
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish,dishDto);
        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return R.success(dishDto);
    }


    /**
     * title 保存修改数据
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //先更新dish菜品基础信息表
        this.updateById(dishDto);
        //删除dishflavor表数据
//      清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);


        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

        //清理所有菜品的缓存数据
        //Set keys = redisTemplate.keys("dish_*");
        //redisTemplate.delete(keys);

        //清理某个分类下面的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);

    }


    @Override
    public R<List<DishDto>> seachDishDtoandCategroyid(Dish dish) {
        List<DishDto> dishDtoList = null;

        //动态构造key
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();//dish_1397844391040167938_1

        //先从redis中获取缓存数据
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        if(dishDtoList != null){
            //如果存在，直接返回，无需查询数据库
            return R.success(dishDtoList);
        }

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId , dish.getCategoryId())
                .orderByDesc(Dish::getCreateTime);

        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();


        List<Dish> list = this.list(dishLambdaQueryWrapper);

        dishDtoList =  list.stream().map((item) -> {
            //将dish对象封装到dishDto对象
            DishDto dishDto = new DishDto();
            //将dishdo拷贝
            BeanUtils.copyProperties(item,dishDto);


            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,item.getId());
            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
            //查询口味分组构造器清空方便下次使用
            dishFlavorLambdaQueryWrapper.clear();

            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        //如果不存在，需要查询数据库，将查询到的菜品数据缓存到Redis
        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);

        return R.success(dishDtoList);
    }
}
