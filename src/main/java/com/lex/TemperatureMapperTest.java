package com.lex;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
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
 * @date 30/03/2017
 */

@RunWith( MockitoJUnitRunner.class)
public class TemperatureMapperTest
{

    @Mock
    Mapper.Context context;

//    @InjectMocks
    private TemperatureMapper testObject;

    @Before
    public void setUp() throws Exception
    {
        testObject = new TemperatureMapper();

    }

    @Test
    public void processValidRecord()
    {
        Text value = new Text( "MP,77" );
        new MapDriver<Text, Text, Text, IntWritable>()
            .withMapper( new TemperatureMapper() )
            .withInputValue( value )
            .withOutput( new Text( "MP" ), new IntWritable( 77 ) )
            .runTest();
    }

    @Test
    public void testWithObject() throws IOException, InterruptedException
    {
        testObject.map( new Text( "" ), new Text( "MP,77" ), context);
        Mockito.verify(context).write(new Text( "MP" ), new IntWritable( 77 ) );
    }


}
