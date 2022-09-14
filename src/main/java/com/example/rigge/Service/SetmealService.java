package com.example.rigge.Service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rigge.common.R;
import com.example.rigge.dto.SetmealDto;
import com.example.rigge.entity.Setmeal;
import com.example.rigge.entity.SetmealDish;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * title 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    public R<Page> seachPageService(int page , int pageSize , String name) ;


    /**
     * title 保存套餐信息
     * @param setmealDto
     * @return
     */
    public R<String> addSetMeal(SetmealDto setmealDto);

    /**
     * title 添加套餐数据页面数据回显
     * @param id
     * @return
     */
    public R<SetmealDto> seachSetmeal(long id);

    /**
     * title 保存修改数据
     * @param setmealDto
     * @return
     */
    public R<String> updateSetmealService(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);


    /**
     * title 停售商品
     * @param status
     * @param list
     * @return
     */
    public R<String> updateStatus(int status , List<Long> list);

}
