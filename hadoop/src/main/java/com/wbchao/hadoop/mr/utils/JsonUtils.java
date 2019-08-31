package com.wbchao.hadoop.mr.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.avro.generic.GenericData;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<String> getTags(String content){
        JSONObject jsonObject = JSON.parseObject(content);
        if(jsonObject != null){
            JSONArray extInfoList = jsonObject.getJSONArray("extInfoList");
            if(extInfoList != null && extInfoList.size()>0){
                JSONObject extInfo = extInfoList.getJSONObject(0);
                if(extInfo != null) {
                    JSONArray tags = extInfo.getJSONArray("values");
                    return  tags.toJavaList(String.class);
                }
            }
        }
        return  new ArrayList<String>();
    }
}
