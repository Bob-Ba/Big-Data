package au.edu.utas.mr.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


/**
 * The stage of reduce
 * KEYIN,VALUEIN: the output of the phrase of map (check the args from the mapper class)
 * KEYOUT,VALUEOUT: the tpye of args depend on your requirements
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    //Reuse the variable
    IntWritable outputValue = new IntWritable();

    /**
     * This method will be invoked when scanning each line of the input data from map stage
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        //Data example after mapped:
        //Bob, 1
        //MJ, 1
        //Bob, 1
        //MJ, 1
        //MJ, 1

        //Step1: calculate the number of those keys
        int sum = 0;
        for (IntWritable value : values) {
            //Count the number of the same key
            sum += value.get();
        }

        //Step2: Write to context
        outputValue.set(sum);

        //The output key is the same as the input key
        context.write(key, outputValue);
    }
}
