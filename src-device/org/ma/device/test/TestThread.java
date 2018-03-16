package org.ma.device.test;

public class TestThread {

	public static void main(String[] args) {

		A a = new A();
		a.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println((a.isAlive()));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a.interrupt();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println((a.isAlive()));
		
		System.out.println("end main");
	}

	static class A extends Thread {

		public void run() {
			try {
				while (true) {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("end thread ..");
		}
	}
}
