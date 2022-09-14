package com.example.rigge.Service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rigge.Mapper.AddressBookMapper;
import com.example.rigge.Service.AddressBookService;
import com.example.rigge.entity.AddressBook;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
