/*
 * Copyright (c) 2017 Nokia Solutions and Networks. All rights reserved.
 */

package com.lex;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapreduce.Reducer;


/**
 * @author Lex Li
 * @date 03/03/2017
 */
public class LogReduce
        extends Reducer<Text, IntWritable, Text, IntWritable>
{
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
//        super.reduce(key, values, context);
        int nbValues = 0;
        for (IntWritable value : values)
        {
            nbValues++;
        }
        context.write(key, new IntWritable(nbValues));
    }

    protected void reduce(Text key, Iterator<IntWritable> values, OutputCollector collector) throws IOException, InterruptedException {
        int count = 0;
        while (values.hasNext())
        {
            count = count + 1;
        }
        collector.collect(key, new IntWritable(count));
    }
}