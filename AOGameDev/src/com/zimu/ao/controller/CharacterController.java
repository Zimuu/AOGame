package com.zimu.ao.controller;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import com.zimu.ao.character.Player;
import com.zimu.ao.enums.Direction;
import com.zimu.ao.enums.GameState;
import com.zimu.ao.item.AbstractItem;

/**
 * 任务控制器
 * 负责对玩家的控制(行走,在地图上位置,人物图像等等)
 * @author zimu
 *
 */
public class CharacterController {

	private Animation sprite, up, right, down, left;

	private Player player;
	
	private float x = 450f, y = 80f;
	
	public CharacterController() {
		try {
			player = Player.getInstance();
			setUp();
		} catch (Exception e) {}
	}
	
	/**
	 * 初始化玩家信息,目前只有玩家的图片
	 * @throws Exception
	 */
	private void setUp() throws Exception {
		Image [] movementUp = { new Image("resource/image/UP.gif"), new Image("resource/image/UP.gif") };
		Image [] movementDown = { new Image("resource/image/DO.gif"), new Image("resource/image/DO.gif") };
		Image [] movementLeft = { new Image("resource/image/LE.gif"), new Image("resource/image/LE.gif") };
		Image [] movementRight = { new Image("resource/image/RI.gif"), new Image("resource/image/RI.gif") };
		int [] duration = { 300, 300 }; 
		
		up = new Animation(movementUp, duration, false);
		down = new Animation(movementDown, duration, false);
		left = new Animation(movementLeft, duration, false);
		right = new Animation(movementRight, duration, false);
		
		sprite = left;
	}
	
	public int putItem(AbstractItem item) {
		return player.putItem(item);
	}
	
	public int gold() {
		return player.getGold();
	}
	
	public Direction getDirection() {
		if (sprite == up)
			return Direction.UP;
		else if (sprite == right)
			return Direction.RIGHT;
		else if (sprite == down)
			return Direction.DOWN;
		else 
			return Direction.LEFT;
	}
	
	/**
	 * 玩家的转向
	 * @param direction
	 */
	public void turn(Direction direction) {
		switch (direction) {
			case UP:
				sprite = up;
				break;
			case RIGHT:
				sprite = right;
				break;
			case DOWN:
				sprite = down;
				break;
			case LEFT:
				sprite = left;
				break;
			default:
		}
	}
	
	/**
	 * 向当前方向行走
	 * @param delta
	 */
	public void forward(int delta) {
		if (sprite == up) {
			y -= delta * 0.1f;
		} else if (sprite == right) {
			x += delta * 0.1f;
		} else if (sprite == down) {
			y += delta * 0.1f;
		} else {
			x -= delta * 0.1f;
		}
	}
	
	/**
	 * 判断玩家是否要进入到某一个地图中
	 * @param state
	 * @return
	 */
	public boolean enterState(GameState state) {
		switch (state) {
			case LABYRINTH:
				if (sprite == left)
					if (x <= 0.12f) {
						x = 760f;
						sprite = left;
						return true;
					}
				return false;
			case START_VILLAGE:
				if (sprite == right)
					if (x > 767f) {
						x = 1f;
						sprite = right;
						return true;
					}
				return false;
			default :
				return false;
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public Animation getSprite() {
		return sprite;
	}
}
