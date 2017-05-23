package com.tera;

public class ThreadA {
	public static void main(String[]args)
	{
	ThreadB b=new ThreadB();
	b.name="b1";
	b.start();
	
	System.out.println("b is start....");
	synchronized(b)//括号里的b是什么意思,起什么作用?
	{
		try{
			System.out.println("Waiting for b to complete...");
			b.wait();//这一句是什么意思，究竟让谁wait?
			System.out.println("Completed.Now back to main thread");
		}catch(InterruptedException e){}
	}
	System.out.println("Totalis:"+b.total);
	}
}
