package com.wbchao.hadoop.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

public class HdfsTest {

    public static final String HDFS_URL = "hdfs://s101:8020";

    @Test
    public void testUpload() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path path = new Path(HDFS_URL + "/user/wbchao/2.txt");
        FSDataOutputStream out = fs.create(path);
        out.write("This is the test line".getBytes());
        out.flush();
        out.close();
    }

    @Test
    public void testDownload() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path path = new Path(HDFS_URL + "/user/wbchao/1.txt");
        FSDataInputStream in = fs.open(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copyBytes(in, out, 1024);
        System.out.println(new String(out.toByteArray()));
    }
}
