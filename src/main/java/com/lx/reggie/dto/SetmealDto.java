package com.lx.reggie.dto;


import com.lx.reggie.entity.Setmeal;
import com.lx.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
