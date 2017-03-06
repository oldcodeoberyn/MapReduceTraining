/*
 * Copyright (c) 2017 Nokia Solutions and Networks. All rights reserved.
 */

package com.lex;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lex Li
 * @date 03/03/2017
 */
public class LogMap extends Mapper<Text, Text, Text, IntWritable>
{

    public static final IntWritable accmulator = new IntWritable(1);
    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        String keyContent = key.toString();

        if(keyContent.isEmpty())
        {
            return;
        }
        Pattern pattern = Pattern.compile("Caused by:.*\\.(.*Exception)");
        Matcher m = pattern.matcher(keyContent);
        if(m.find())
        {
            context.write(new Text(m.group(1)), accmulator);
        }
    }
}