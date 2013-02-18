package com.zimu.ao.state;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.zimu.ao.controller.GameProcessController;
import com.zimu.ao.enums.GameState;

/**
 * 游戏主类,初始化每个游戏地图的窗口
 * @author zimu
 *
 */
public class AOGame extends StateBasedGame {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	public AOGame(String name) {
		super(name);
	}	

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new Labyrinth(GameState.LABYRINTH));
		this.addState(new Village(GameState.START_VILLAGE));
		this.enterState(GameProcessController.CURRENT_STATE);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new AOGame("AO Game"));
	        app.setDisplayMode(WIDTH, HEIGHT, false);
	        app.start();
		} catch (SlickException e){
			e.printStackTrace();
		}
	}

}
