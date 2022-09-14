package com.example.rigge.Service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rigge.Mapper.DishFlavorMapper;
import com.example.rigge.Service.DishFlavorService;
import com.example.rigge.entity.DishFlavor;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
