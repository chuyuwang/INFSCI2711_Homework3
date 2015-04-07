package edu.pitt.sis.infsci2711.wordcount;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;


public class FriendsWritable implements Writable{
	
	public int user;
	public int mutualfriend;
	
	public FriendsWritable(int user,int mutualfriend)
	{
		this.user=user;
		this.mutualfriend=mutualfriend;
	}
	
	public FriendsWritable(){
		this(-1,-1);
	}
	
	public void write(DataOutput out) throws IOException{
		out.writeInt(user);
		out.writeInt(mutualfriend);
	}
	
	public void readFields(DataInput in) throws IOException{
		user=in.readInt();
		mutualfriend=in.readInt();
	}
	

}
