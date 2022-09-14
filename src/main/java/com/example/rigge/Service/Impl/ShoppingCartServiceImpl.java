package com.example.rigge.Service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rigge.Mapper.ShoppingCartMapper;
import com.example.rigge.Service.ShoppingCartService;
import com.example.rigge.entity.ShoppingCart;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
