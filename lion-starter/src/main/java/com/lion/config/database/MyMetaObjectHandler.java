package com.lion.config.database;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * @author: lianhong
 * @date: 2019/3/26
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("creator", "admin", metaObject);
        this.setFieldValByName("createDate", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("modifier", "admin", metaObject);
        this.setFieldValByName("updateDate", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), metaObject);
    }
}
