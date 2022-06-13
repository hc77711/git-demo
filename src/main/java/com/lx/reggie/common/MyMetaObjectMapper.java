package com.lx.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lx.reggie.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 小兴
 * @description TODO
 * @className MyMetaObjectMapper
 * @date 2022/6/11 20:24
 */
@Component
@Slf4j
public class MyMetaObjectMapper implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        Long empId = UserHolder.getUser();
        metaObject.setValue("createUser", empId);
        metaObject.setValue("updateUser", empId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long empId = UserHolder.getUser();
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", empId);
    }
}
