package com.wbchao.hadoop.mr.taggen;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class TaggenApp {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        args = new String[]{"D:/资料/BigData/big9-hadoop资料/big9-03hadoop-day06/temptags.txt", "D:/资料/BigData/big9-hadoop资料/big9-03hadoop-day06/out1", "D:/资料/BigData/big9-hadoop资料/big9-03hadoop-day06/out2"};
        Configuration conf = new Configuration();

        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        fs.delete(new Path(args[1]),true);
        fs.delete(new Path(args[2]),true);

        Job job = Job.getInstance(conf);
        job.setJobName("Tag_Gen1");
        job.setJarByClass(TaggenApp.class);

        FileInputFormat.addInputPath(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setMapperClass(TaggenMapper1.class);
        job.setPartitionerClass(BusidPatitioner.class);
        job.setGroupingComparatorClass(BusidGroupComparator.class);
        job.setReducerClass(TaggenReducer1.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setNumReduceTasks(2);

        if(job.waitForCompletion(true)){
            Configuration conf2 = new Configuration();

            conf2.set("fs.defaultFS", "file:///");

            Job job2 = Job.getInstance(conf2);
            job2.setJobName("Tag_Gen2");
            job2.setJarByClass(TaggenApp.class);

            FileInputFormat.addInputPath(job2,new Path(args[1]));
            FileOutputFormat.setOutputPath(job2, new Path(args[2]));

            job2.setInputFormatClass(KeyValueTextInputFormat.class);
            job2.setOutputFormatClass(TextOutputFormat.class);

            job2.setMapOutputKeyClass(IntWritable.class);
            job2.setMapOutputValueClass(Text.class);

            job2.setMapperClass(TaggenMapper2.class);
            job2.setReducerClass(TaggenReducer2.class);

            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(NullWritable.class);

            job2.setNumReduceTasks(1);
            job2.waitForCompletion(true);
        }

    }
}
