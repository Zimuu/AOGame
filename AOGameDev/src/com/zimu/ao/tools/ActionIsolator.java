package com.zimu.ao.tools;

public class ActionIsolator implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (Exception e) {}	
	}

}
