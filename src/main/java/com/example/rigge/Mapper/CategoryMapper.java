package com.example.rigge.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rigge.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: CategoryMapper
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/3 17:45
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
