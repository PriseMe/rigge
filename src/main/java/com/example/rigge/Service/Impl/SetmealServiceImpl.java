package com.example.rigge.Service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rigge.Mapper.SetmealMapper;
import com.example.rigge.Service.SetmealDishService;
import com.example.rigge.Service.SetmealService;
import com.example.rigge.common.R;
import com.example.rigge.common.CustomException;
import com.example.rigge.dto.SetmealDto;

import com.example.rigge.entity.Setmeal;
import com.example.rigge.entity.SetmealDish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    public SetmealDishService setmealDishService;

    @Autowired
    public SetmealMapper setmealMapper;



    /**
     * title 查询套餐分类
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R<Page> seachPageService(int page, int pageSize, String name) {

        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        //setmealPage
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(name != null , Setmeal::getName , name)
                .orderByAsc(Setmeal::getUpdateTime)
                .orderByDesc(Setmeal::getCreateTime);
        this.page(setmealPage,setmealLambdaQueryWrapper);

        return R.success(setmealPage);
    }


    /**
     * title 保存套餐信息
     * @param setmealDto
     * @return
     */
    @Transactional
    @Override
    public R<String> addSetMeal(SetmealDto setmealDto) {
        //将基础信息保存至setmeal表
        this.save(setmealDto);

        long setmealDtoId = setmealDto.getId();
        List<SetmealDish> list = setmealDto.getSetmealDishes();
        list.stream().map((item) -> {
            item.setSetmealId(setmealDtoId);
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(list);

        return R.success("成功") ;
    }

    /**
     * title 添加套餐数据页面数据回显
     * @param id
     * @return
     */
    @Transactional
    public R<SetmealDto> seachSetmeal(long id) {
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        //查询套餐中菜品信息
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId,id);
        setmealDto.setSetmealDishes(setmealDishService.list(setmealDishLambdaQueryWrapper));
        return R.success(setmealDto);
    }


    @Override
    @Transactional
    public R<String> updateSetmealService(SetmealDto setmealDto) {
        //先更新基础信息
        this.updateById(setmealDto);

        setmealDishService.removeById(setmealDto.getId());
        List<SetmealDish> setmealDishList= setmealDto.getSetmealDishes();
        setmealDishList.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishList);
        return R.success("成功");
    }

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //select count(*) from setmeal where id in (1,2,3) and status = 1
        //查询套餐状态，确定是否可用删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);
        if(count > 0){
            //如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据---setmeal
        this.removeByIds(ids);

        //delete from setmeal_dish where setmeal_id in (1,2,3)
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        //删除关系表中的数据----setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);
    }


    /**
     * title 停售商品
     * @param status
     * @param list
     * @return
     */
    @Override
    public R<String> updateStatus(int status, List<Long> list) {

        setmealMapper.updateStatus(status,list);

        return null;
    }
}
