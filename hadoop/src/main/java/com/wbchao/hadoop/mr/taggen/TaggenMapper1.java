package com.wbchao.hadoop.mr.taggen;

import com.wbchao.hadoop.mr.utils.JsonUtils;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

public class TaggenMapper1 extends Mapper<LongWritable, Text, Text, IntWritable> {

    public void map(LongWritable key, Text value, Context context) {
        String[] line = value.toString()
                             .split("\t");
        String busid = line[0];
        String content = line[1];
        List<String> tags = JsonUtils.getTags(content);
        tags.stream()
            .forEach(tag -> {
                try {
                    context.write(new Text(busid + "_" + tag), new IntWritable(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }
}
