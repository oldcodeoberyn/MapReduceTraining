package com.lex;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

/**
 * @author Lex Li
 * @date 31/03/2017
 */
public class TemperatureDriverTest
{

    private TemperatureDriver testObject;

    @Before
    public void setUp() throws Exception
    {
        testObject = new TemperatureDriver();

    }

    @Test
    public void testDriver() throws Exception
    {
        Configuration conf = new Configuration(  );
        conf.set( "fs.default.name", "file:///" );
        conf.set("mapreduce.framework.name", "local");
        Path inputPath = new Path( "/input" );
        Path outputPath = new Path( "/output" );
        FileSystem fs =  FileSystem.getLocal(conf);
        fs.delete( outputPath, true );
        testObject.setConf( conf );
        int exitCode = testObject.run( new String[]{inputPath.toString(), outputPath.toString()} );
        assertThat(exitCode, is(0));
        //checkOutput(conf, outputPath);

    }

}
