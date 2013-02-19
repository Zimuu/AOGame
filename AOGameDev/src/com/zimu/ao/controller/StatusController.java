package com.zimu.ao.controller;

import com.zimu.ao.enums.Status;

/**
 * 状态控制器
 * 控制玩家当前正在进行的状态(战斗状态,购物状态,等等)
 * @author zimu
 *
 */
public class StatusController {
	
	private Status status;
	
	public StatusController() {
		status = Status.NONE;
	}
	
	public void active(Status status) {
		this.status = status;
	}
	
	
	public void deactive() {
		status = Status.NONE;
	}
	
	public Status status() {
		return status;
	}
	
	public boolean inUnmovableStatus() {
		return status == Status.BATTLE || status == Status.BAG || 
				status == Status.SHOP || status == Status.SELL || status == Status.BUY;
	}

}
