package au.edu.utas.mr.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


/**
 * The stage of Map
 *
 * For the four args below, it means:
 * KEYIN: the key to input data that is the offsets of the data
 * VALUEIN: the value to input data
 * KEYOUT: the key to output data
 * VALUEOUT: the value to output data
 *
 * Note: all type of args below are decided by requirements, the fourth arg (IntWritable) also can be LongWritable.
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    //Define variables outside method 'map' to reuse both to reduce object creation
    Text keyOutput = new Text();

    //Initialize the increment to 1
    IntWritable valueOutput = new IntWritable(1);

    /**
     * This method will be invoked when scanning each line of the input data
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //Data example:
        //Bob MJ Bob MJ MJ

        //Step1: Read one whole line once
        String onelineData = value.toString();

        //Step2: split the whole line str
        String[] words = onelineData.split(" ");

        //Step3: Write output by loop
        for (String word : words) {
            //Set key
            keyOutput.set(word);

            //
            context.write(keyOutput, valueOutput);
        }
    }
}
