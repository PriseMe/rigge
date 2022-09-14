package com.example.rigge.Service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rigge.Mapper.UserMapper;
import com.example.rigge.Service.UserService;
import com.example.rigge.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
