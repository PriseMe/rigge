package com.example.rigge.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rigge.Service.DishService;
import com.example.rigge.common.R;
import com.example.rigge.dto.DishDto;
import com.example.rigge.entity.Dish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: DishController
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/4 16:32
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    public DishService dishService;



/**
  * @Author: Promiseme
  * @Title: adddish 新增菜品
  * @Description:
  * @param @param
  * @return @return
  * @throws
  * @Date: 2022/9/4 18:32
  */
    @PostMapping
    public R<String>  adddish(@RequestBody DishDto dishDto){
        log.info( dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("成功");
    }

/**
  * @Author: Promiseme
  * @Title: dishdtoPage 菜品分页
  * @Description:
  * @param @param
  * @return @return
  * @throws
  * @Date: 2022/9/4 22:18
  */
    @GetMapping("/page")
    public R<Page> dishdtoPage(int page , int pageSize , String name){
        return dishService.dishdtoPageSeach(page,pageSize,name);
    }


    /**
     * title 修改页面回显数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getDishuDto(@PathVariable("id") long id){
        return dishService.seachDishDto(id);
    }


    /**
     * title 保存修改数据
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> putDishDto(@RequestBody DishDto dishDto){

        dishService.updateWithFlavor(dishDto);
        return R.success("成功");
    }

    /**
     *title 根据条件查询对应的菜品数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> seachDishDto(Dish dish){
        log.info("seachDishDto().....");
        return dishService.seachDishDtoandCategroyid(dish);
    }

}
