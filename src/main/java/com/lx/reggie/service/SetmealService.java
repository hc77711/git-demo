package com.lx.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.reggie.common.R;
import com.lx.reggie.dto.SetmealDto;
import com.lx.reggie.entity.Setmeal;

import java.util.List;

/**
 * 套餐(Setmeal)表服务接口
 *
 * @author makejava
 * @since 2022-06-11 22:57:13
 */
public interface SetmealService extends IService<Setmeal> {

    R<String> saveSetmel(SetmealDto setmealDto);

    R<Page<SetmealDto>> page(int page, int pageSize, String name);

    R<List<String>> del(String ids);

    R<String> updateStatus(int state, String ids);
}

