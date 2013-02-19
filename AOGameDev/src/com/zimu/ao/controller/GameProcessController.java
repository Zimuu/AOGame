package com.zimu.ao.controller;

import com.zimu.ao.enums.GameState;

/**
 * 游戏流程控制器
 * 负责控制游戏任务的流程
 * @author zimu
 *
 */
public class GameProcessController {

	public static int CURRENT_STATE = GameState.START_VILLAGE.ordinal();
	
	public GameProcessController() {
		
	}
	
	public void setState(GameState state) {
		CURRENT_STATE = state.ordinal();
	}
	
}
