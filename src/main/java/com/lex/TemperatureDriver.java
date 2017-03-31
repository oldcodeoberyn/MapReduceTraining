/*
 * Copyright (c) 2017 Nokia Solutions and Networks. All rights reserved.
 */

package com.lex;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author Lex Li
 * @date 31/03/2017
 */
public class TemperatureDriver extends Configured
                implements Tool
{
    @Override public int run( String[] args ) throws Exception
    {
        if( args.length != 2)
        {
            System.err.printf( "Usage: %s [generic options] <input> <ouput>", getClass().getSimpleName() );
            GenericOptionsParser.printGenericCommandUsage( System.err );
            return -1;
        }

        Job job = new Job( getConf(), "Max Temperature" );
        job.setJarByClass( getClass() );

        FileInputFormat.addInputPath( job, new Path( args[0] ) );
        FileOutputFormat.setOutputPath( job, new Path( args[1]) );

        job.setMapperClass( TemperatureMapper.class );
        job.setCombinerClass( TemperatureReducer.class );
        job.setReducerClass( TemperatureReducer.class );

        return job.waitForCompletion( true )? 0 : -1;
    }

    public static void main( String[] args) throws Exception
    {
        int exitCode = ToolRunner.run( new TemperatureDriver(), args );
        System.exit( exitCode );
    }
}
