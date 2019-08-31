package com.wbchao.hadoop.mr.taggen;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class BusidGroupComparator extends WritableComparator {

    public BusidGroupComparator() {
        super(Text.class, true);
    }

    public int compare(WritableComparable a, WritableComparable b) {
        Text k1 = (Text) a;
        Text k2 = (Text) b;
        String busId1 = k1.toString()
                          .split("_")[0];
        String busId2 = k2.toString()
                          .split("_")[0];
        return busId1.compareTo(busId2);
    }
}
