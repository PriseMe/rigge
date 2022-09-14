package com.example.rigge.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rigge.entity.Category;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: CategoryService
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/3 17:46
 */
public interface CategoryService extends IService<Category> {

    public boolean removeCategroy(long id);
}
