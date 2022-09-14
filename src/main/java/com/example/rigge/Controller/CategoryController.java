package com.example.rigge.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rigge.Service.CategoryService;
import com.example.rigge.common.R;
import com.example.rigge.entity.Category;
import com.example.rigge.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: CategoryControoler
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/3 17:56
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    public CategoryService categoryService;

    /**
      * @Author: Promiseme
      * @Title: addCategory 添加分类
      * @Description:
      * @param @param
      * @return @return
      * @throws
      * @Date: 2022/9/3 18:13
      */


    @PostMapping
    public R<String> addCategory(@RequestBody Category category){
        log.info("添加：{}",category);
        if (categoryService.save(category)){
            return R.success("添加成功");
        }
        return R.error("添加失败");


    }


    @GetMapping("/page")
    public R<Page> getPageCategroy(int page , int pageSize){
        log.info("分页：page:{} pagesize:{}",page,pageSize);

        //分页构造器
        Page<Category> categoryPageinfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Category> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();

        employeeLambdaQueryWrapper.orderByDesc(Category::getCreateTime);

        categoryService.page(categoryPageinfo,employeeLambdaQueryWrapper);


        return R.success(categoryPageinfo);

    }

    @DeleteMapping
    public R<String> deleuteCategroy( long ids){
        log.info("删除分类，id为：{}",ids);


        //categoryService.removeById(id);
        categoryService.removeCategroy(ids);

        return R.success("分类信息删除成功");
    }

    @PutMapping
    public R<String> updateCategroy(@RequestBody Category category){
        log.info("updateCategroy:{}",category);
        categoryService.updateById(category);
        return R.success("修改成功");
    }


    @GetMapping("/list")
    public R<List<Category>> seachCategroy(Category category){
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();


        categoryLambdaQueryWrapper.eq(category.getType() != null , Category::getType , category.getType())
                .orderByAsc(Category::getCreateTime)
                .orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(categoryLambdaQueryWrapper);
        return R.success(list);


    }


}
