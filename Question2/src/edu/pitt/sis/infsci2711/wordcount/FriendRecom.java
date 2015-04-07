package edu.pitt.sis.infsci2711.wordcount;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class FriendRecom extends Configured implements Tool {
   public static void main(String[] args) throws Exception {
      System.out.println(Arrays.toString(args));
      int res = ToolRunner.run(new Configuration(), new FriendRecom(), args);
      System.exit(res);
   }

   @Override
   public int run(String[] args) throws Exception {
      System.out.println(Arrays.toString(args));
      Job job = new Job(getConf(), "FriendRecom");
      job.setJarByClass(FriendRecom.class);
      job.setOutputKeyClass(IntWritable.class);
      job.setOutputValueClass(FriendsWritable.class);

      job.setMapperClass(Map.class);
      job.setReducerClass(Reduce.class);

      job.setInputFormatClass(TextInputFormat.class);
      job.setOutputFormatClass(TextOutputFormat.class);

      FileInputFormat.addInputPath(job, new Path(args[0]));
      FileOutputFormat.setOutputPath(job, new Path(args[1]));

      job.waitForCompletion(true);
      
      return 0;
   }
   
   public static class Map extends Mapper<LongWritable, Text, IntWritable, FriendsWritable > {
      @Override
      public void map(LongWritable key, Text value, Context context)
              throws IOException, InterruptedException {
    	  String line=value.toString();
    	  String[] friends=line.split("\\s+");
    	  int userid=Integer.parseInt(friends[0]);
    	  
    	  if(friends.length==2)
    	  {
    		  String[] friend=friends[1].split(",");
    		  for(String token:friend)
    		  { 
    			  context.write(new IntWritable(userid),new FriendsWritable(Integer.parseInt(token),-1));
    		  }
    		  for(int i=1;i<friend.length;i++){
    			  for(int j=i+1;j<friend.length;j++){
    				  context.write(new IntWritable(Integer.parseInt(friend[i])),new FriendsWritable(Integer.parseInt(friend[j]),userid));
    				  context.write(new IntWritable(Integer.parseInt(friend[j])),new FriendsWritable(Integer.parseInt(friend[i]),userid));
    			  }
    		  }
    	  }
      }
   }

   public static class Reduce extends Reducer< IntWritable, FriendsWritable, IntWritable,Text> {
	   @Override
      public void reduce(IntWritable key, Iterable<FriendsWritable> values,Context context)
              throws IOException, InterruptedException {
		   HashMap<Integer,List<Integer>> FriendRecom=new HashMap<Integer,List<Integer>>();
		   for(FriendsWritable mfriend:values){
			   Boolean isFriend= (mfriend.mutualfriend==-1);
			   int user=mfriend.user;
			   int mutualfriend=mfriend.mutualfriend;
			   if(FriendRecom.containsKey(user)){
				   if(isFriend){
					   FriendRecom.put(user, null);
				   }
				   else if(FriendRecom.get(user)!=null){
						   FriendRecom.get(user).add(mutualfriend);
					   }   
			   }else{
				   if(!isFriend){
					   ArrayList<Integer> Mfriend=new ArrayList<Integer>();
					   Mfriend.add(mutualfriend);
					   FriendRecom.put(user,Mfriend);
				   }else{
					   FriendRecom.put(user,null);
				   }
			   }
		   }
		List<Entry<Integer,List<Integer>>> list=new ArrayList<Entry<Integer,List<Integer>>>();
		for(Entry<Integer,List<Integer>> l:FriendRecom.entrySet()){
			if(l.getValue()!=null){
				list.add(l);
			}
		}

		Collections.sort(list, new Comparator<Entry<Integer,List<Integer>>>(){
			public int compare(Entry<Integer,List<Integer>> o1,Entry<Integer,List<Integer>> o2){
				if(o1.getValue().size()>o2.getValue().size())
				{
					return -1;
				}
				 if(o1.getValue().size()==o2.getValue().size()&& o1.getKey()<o2.getKey())
				 {
					return -1; 
				 }else {
				return 1;
			}
			}
		});
		
		String FriendRecomList="";
		for(Entry<Integer,List<Integer>> entry:list){
			FriendRecomList+=entry.getKey()+",";
		}
		if(list.size()>1){
			FriendRecomList.substring(0,FriendRecomList.length()-1);
		}
    	context.write(key, new Text(FriendRecomList));
      }
   }
}