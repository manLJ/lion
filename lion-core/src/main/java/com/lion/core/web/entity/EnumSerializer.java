package com.lion.core.web.entity;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * <p>
 * 通用枚举注入演示，注意需要实现 IEnums 也需要扫描枚举包
 * </p>
 *
 * @author HuangPeng
 * @since 2018-08-15
 */

//ObjectSerializer和ObjectDeserializer分别是fastjson的编码器和解码器接口
public class EnumSerializer implements ObjectSerializer, ObjectDeserializer {
    //反序列化过程
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String value = String.valueOf(parser.parse());
        if (IEnum.class.isAssignableFrom((Class<IEnum>) type)) {
            return resolveIEnum(value, (Class<IEnum>) type);
        } else {
            return resolveNormalEnum(value, (Class<Enum>) type);
        }
    }

    private <T> T resolveNormalEnum(String value, Class<Enum> type) {
        for (Enum constant : type.getEnumConstants()) {
            if (constant.toString().equalsIgnoreCase(value)) {
                return (T) constant;
            }
        }

        return null;
    }

    private <T> T resolveIEnum(String value, Class<IEnum> type) {
        for (IEnum constant : type.getEnumConstants()) {
            if (String.valueOf(constant.getValue()).equalsIgnoreCase(value)) {
                return (T) constant;
            }
        }
        return null;
    }


    //序列化过程
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) serializer.write(null);
        else serializer.write(((IEnum) object).getValue());
    }


    //暂时还不清楚
    public int getFastMatchToken() {
        return 0;
    }
}
