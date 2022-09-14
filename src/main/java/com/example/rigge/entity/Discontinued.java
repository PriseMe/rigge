package com.example.rigge.entity;

import lombok.Data;

import java.util.List;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: Discontinued
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/5 16:23
 */
@Data
public class Discontinued {
    public int status;
    public List<Long> putListId;
}
