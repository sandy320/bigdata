package com.wbchao.hadoop.mr.taggen;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaggenReducer1 extends Reducer<Text, IntWritable, Text, Text> {

    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        Map<String, Integer> tags = new HashMap<>();
        String busId = key.toString()
                          .split("_")[0];
        values.forEach(v -> {
            String tagName = key.toString()
                                .split("_")[1];
            Integer count = tags.get(tagName);
            if (count == null) {
                tags.put(tagName, 1);
            } else {
                tags.put(tagName, count + 1);
            }
        });

        List<Map.Entry<String, Integer>> sortList = new ArrayList<>(tags.entrySet());
        Collections.sort(sortList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        StringBuffer sb = new StringBuffer();
        sortList.stream()
                .forEach(n -> {
                    sb.append(n.getKey())
                      .append(":")
                      .append(n.getValue())
                      .append(",");
                });

        context.write(new Text(busId), new Text(sb.toString()));
    }
}
