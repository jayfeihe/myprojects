package com.tera;

public class ThreadB extends Thread{
	int total;
	public String name;
	public void run()
	{
	synchronized(this)
	{
	System.out.println("ThreadB is running..");
	for(int i=0;i<100;i++)
	{
	total+=i;
	System.out.println(name+" total is"+total);
	}
	notify();
	}
	}
}
