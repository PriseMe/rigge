package com.example.rigge.dto;


import com.example.rigge.entity.Setmeal;
import com.example.rigge.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
