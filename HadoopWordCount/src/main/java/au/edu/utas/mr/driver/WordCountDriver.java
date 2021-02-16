package au.edu.utas.mr.driver;

import au.edu.utas.mr.mapper.WordCountMapper;
import au.edu.utas.mr.reducer.WordCountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        //Step1:
        //Get configuration
        Configuration conf = new Configuration();

        //Get Job object and encapsulate Job
        Job job = Job.getInstance(conf);

        //Step2: Set the path when loading jar
        job.setJarByClass(WordCountDriver.class);

        //Step3: Set mapper and reducer classes
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //Step4: Set the type of key and value of input and output of map stage
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //Step5: Set the type of key and value of output eventually
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //Step6: Set the path of input and output
        //Input path (from args[0], the first arg)
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        //Output path (from args[1], the second arg)
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //Step7: Submit the job
        //job.submit();

        //Submit the job and print subsequent info (check the source code to see it invokes method submit() )
        boolean result = job.waitForCompletion(true);

        //Success - print 0, or print 1
        System.exit(result ? 0 : 1);
    }
}
