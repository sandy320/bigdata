package com.wbchao.hadoop.mr.taggen;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaggenReducer2 extends Reducer<IntWritable, Text, Text, NullWritable> {

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        values.forEach(tagInfo ->{
            try {
                context.write(tagInfo, NullWritable.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
