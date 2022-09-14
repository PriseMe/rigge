package com.example.rigge.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rigge.Service.SetmealService;
import com.example.rigge.common.R;
import com.example.rigge.dto.SetmealDto;
import com.example.rigge.entity.Category;
import com.example.rigge.entity.Setmeal;
import com.example.rigge.entity.SetmealDish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: SetmealCntroller
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/4 23:23
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealCntroller {

    @Autowired
    public SetmealService setmealService;

    /**
     * title 套餐分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> seachPage(int page ,int pageSize ,String name){

        return setmealService.seachPageService(page,pageSize,name);
    }

    /**
     * title 添加套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> savesetmealDish(@RequestBody SetmealDto setmealDto){
        log.info("seachPage"+setmealDto.toString());

        return setmealService.addSetMeal(setmealDto);
    }

    /**
     * title 添加套餐数据页面数据回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> seachSetmeal(@PathVariable long id){
        return setmealService.seachSetmeal(id);
    }

    /**
     * title 保存修改数据
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> updauteSetmeal(@RequestBody SetmealDto setmealDto){
        log.info(setmealDto.toString());
        return setmealService.updateSetmealService(setmealDto);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);

        setmealService.removeWithDish(ids);

        return R.success("套餐数据删除成功");
    }


    /**
     * 停售套餐
     * @param
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> putStatus(@PathVariable("status") int status,
            @RequestParam List<Long> ids){
        log.info("ids:{}",ids);

        setmealService.updateStatus(status,ids);

        return R.success("套餐数据删除成功");
    }

    /**
     *  根据条件查询套餐数据
     * @param categoryId
     * @param status
     * @return
     */
    @GetMapping("list")
    public R<List<Setmeal>> setmealDtoList(long categoryId , int status){
        LambdaQueryWrapper<Setmeal> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();

        categoryLambdaQueryWrapper.eq(Setmeal::getCategoryId,categoryId)
                .eq(Setmeal::getStatus,status)
                .orderByDesc(Setmeal::getCreateTime);
        return R.success(setmealService.list(categoryLambdaQueryWrapper));
    }



}
