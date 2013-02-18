package com.zimu.ao.controller;

import java.awt.Point;

import org.newdawn.slick.Animation;

import com.zimu.ao.character.Player;
import com.zimu.ao.enums.Direction;
import com.zimu.ao.enums.GameState;
import com.zimu.ao.enums.Status;
import com.zimu.ao.item.AbstractItem;

/**
 * 主控制器,包含了所有的其他子控制器
 * @author zimu
 *
 */
public class MainController {

	private CharacterController characterController;
	@SuppressWarnings("unused")
	private GameProcessController gameProcessController;
	private StatusController statusController;
	@SuppressWarnings("unused")
	private BattleController battleController;
	private MusicController musicController;
	
	private static MainController instance;
	
	private MainController() {
		characterController = new CharacterController();
		gameProcessController = new GameProcessController();
		statusController = new StatusController();
		battleController = new BattleController();
		musicController = new MusicController();
	}
	
	public static MainController getInstance() {
		if (instance == null)
			instance = new MainController();
		return instance;
	}
	
	/*Controller*/
	
	/**
	 * 返回玩家面前的两个格子的位置
	 * @param delta
	 * @return
	 */
	public Point[] getFrontPos(int delta) {
		float x = getX(), y = getY(), xn = getX(), yn = getY();
		switch (characterController.getDirection()) {
			case UP:
				x += 2;
				y += delta * 0.1f;
				xn += 28;
				yn = y;
				break;
			case RIGHT:
				x += 32 + delta * 0.1f;
				y += 2;
				xn = x;
				yn += 28;
				break;
			case DOWN:
				x += 2;
				y += 32 + delta * 0.1f;
				xn += 28;
				yn = y;
				break;
			case LEFT:
				x += delta * 0.1f;
				y += 2;
				xn = x;
				yn += 28;
				break;
			default:
		}
		return new Point[] {
			new Point((int) x / 32, (int) y / 32),
			new Point((int) xn / 32, (int) yn / 32)
		};
	}
	
	/*Game Process Control*/
	
	
	/*Character Control*/
	
	public Player getPlayer() {
		return characterController.getPlayer();
	}
	
	public int gold() {
		return characterController.gold();
	}	
	
	public int putItem(AbstractItem item) {
		return characterController.putItem(item);
	}
	
	public Direction getDirection() {
		return characterController.getDirection();
	}
	
	public void forward(int delta) {
		characterController.forward(delta);
	}
	
	public void turn(Direction direction) {
		characterController.turn(direction);
	}
	
	public boolean enterState(GameState state) {
		return characterController.enterState(state);
	}
	
	public float getX() {
		return characterController.getX();
	}
	
	public float getY() {
		return characterController.getY();
	}
	
	public Animation getSprite() {
		return characterController.getSprite();
	}
	
	/*Status Control*/
	
	public Status status() {
		return statusController.status();
	}
	
	public void active(Status status) {
		statusController.active(status);
	}
	
	public void deactive() {
		statusController.deactive();
	}
	
	public boolean inUnmovableStatus() {
		return statusController.inUnmovableStatus();
	}
	
	/*Battle Control*/
	
	/*Music Control*/
	
	public void musicSwitch() {
		musicController.musicSwitch();
	}


}
