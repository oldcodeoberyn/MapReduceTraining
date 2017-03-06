package com.lex;
/*
 * Copyright (c) 2017 Nokia Solutions and Networks. All rights reserved.
 */


//Standard Java imports

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

//Hadoop imports

/**
 * @author Lex Li
 * @date 03/03/2017
 */


public class LogAnalysisDemo
{
    //The Mapper
    //The java main method to execute the MapReduce job
    public static void main(String[] args) throws Exception
    {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: TemperatureDemo <in> <out>");
            System.exit(-1);
        }
        Job job = new Job(conf, "Calculate log line at specific time");
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        job.setJarByClass(LogAnalysisDemo.class);

        job.setMapperClass(LogMap.class);
        job.setReducerClass(LogReduce.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        System.exit(job.waitForCompletion(true) ? 0 : -1);
    }

}