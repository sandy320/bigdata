package com.wbchao.hadoop.mr.taggen;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class BusidPatitioner extends Partitioner<Text, IntWritable> {


    @Override
    public int getPartition(Text key, IntWritable value, int i) {
        String busId = key.toString()
                          .split("_")[0];
        return (busId.hashCode() & Integer.MAX_VALUE) % i;
    }
}
