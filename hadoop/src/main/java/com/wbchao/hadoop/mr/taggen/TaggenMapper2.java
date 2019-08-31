package com.wbchao.hadoop.mr.taggen;

import com.wbchao.hadoop.mr.utils.JsonUtils;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

public class TaggenMapper2 extends Mapper<Text, Text, IntWritable, Text> {

    public void map(Text key, Text value, Context context) {
        try {
            String busId = key.toString();
            String tagInfo = value.toString();
            Integer maxCount = -Integer.parseInt(tagInfo.substring(tagInfo.indexOf(":") + 1, tagInfo.indexOf(",")));

            context.write(new IntWritable(maxCount), new Text(busId + "\t" + value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
