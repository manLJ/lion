package com.lion.core.web;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.lang.reflect.Type;

public class JodaTimeSerializer implements ObjectSerializer {

    private final String pattern;

    public JodaTimeSerializer(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName,
                      Type fieldType, int features) throws IOException {

        if (object == null) {
            serializer.out.writeNull();
            return;
        }

        final DateTime date = (DateTime) object;
        serializer.write(date.toString(DateTimeFormat.forPattern(pattern)));
    }
	

}
