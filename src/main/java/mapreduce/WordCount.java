package mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCount {

    // step 1: Map Class 继承 Mapper
    // 输入数据输入1 LongWritable ；输入数据输入2 Text；输出数据格式1 Text；输出数据格式 2 IntWritable
    // 重写 map方法
    public static class WrodCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // TODO: 2020/6/18  
        }
    }

    // step 2: Reduce Class 继承 Reducer
    // 输入数据输入1 Text ；输入数据输入2 IntWritable；输出数据格式1 Text；输出数据格式 2 IntWritable
    // 重写 Reducer 方法
    public static class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            // TODO: 2020/6/18  
        }
    }

    // step 3: Driver, component job
    public int run(String[] args) throws Exception {

        // step 1: get configuration
        Configuration conf = new Configuration();
        // step 2: create job
        Job job = Job.getInstance(conf, this.getClass().getSimpleName());
        // step 3: run jar
        job.setJarByClass(this.getClass());
        // step 3.1: input
        FileInputFormat.addInputPath(job, new Path(args[0]));
        // step 3.2 :map 设置map 类
        job.setMapperClass(WrodCountMapper.class);
        // step 3.3 :设置map 输出的key的类型
        job.setMapOutputKeyClass(Text.class);
        // step 3.4 :设置map 输出的value的类型
        job.setMapOutputValueClass(IntWritable.class);
        // step 4: reudce
        job.setReducerClass(WordCountReduce.class);
        // step 4.1: reudce 输出的key的类型
        job.setOutputKeyClass(Text.class);
        // step 4.2: reudce 输出的value的类型
        job.setOutputValueClass(IntWritable.class);
        // step 5: output
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        // step 6:提交 job
        boolean isSuccess = job.waitForCompletion(true);

        return isSuccess ? 0 : 1;

    }

    public static void main(String[] args) throws Exception {
        int status = new WordCount().run(args);
        System.exit(status);
    }

}
