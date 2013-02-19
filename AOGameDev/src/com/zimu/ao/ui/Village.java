package com.zimu.ao.ui;

import java.awt.Point;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import com.zimu.ao.board.ShopBoard;
import com.zimu.ao.controller.MainController;
import com.zimu.ao.enums.Direction;
import com.zimu.ao.enums.GameProperty;
import com.zimu.ao.enums.GameState;
import com.zimu.ao.enums.Status;
import com.zimu.ao.item.consumable.Apple;
import com.zimu.ao.item.consumable.SuperApple;
import com.zimu.ao.tools.Camera;

public class Village extends BasicGameState {
	
	/**
	 * 这个窗口(地图)的名称
	 */
	private GameState state;

	private TiledMap tileMap;
	/**
	 * 游戏地图的属性,一张地图被分为一个25*20的矩阵,其中的每一个元素标明了当前位置的属性(比如可以遇怪,障碍物,道路等等信息)
	 */
	private GameProperty[][] propertyMap;
	
	/**
	 * 当前地图商店的购物版面
	 */
	private ShopBoard shopBoard;
	
	/**
	 * 控制人物居中的控制器
	 */
	private Camera camera;
	private MainController mc;
	
	public Village(GameState state) {
		this.state = state;
	}

	/**
	 * 初始化类信息
	 * 读取地图元素的信息,生成购物页面
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		mc = MainController.getInstance();
		tileMap = new TiledMap("resource/map/village.tmx");
		camera = new Camera(gc, tileMap);
		
		propertyMap = new GameProperty[tileMap.getWidth()][tileMap.getHeight()];
		for (int x = 0; x < tileMap.getWidth(); x++) {
			for (int y = 0; y < tileMap.getHeight(); y++) {
				int tileID = tileMap.getTileId(x, y, 0);
				int value = Integer.parseInt(tileMap.getTileProperty(tileID, "prop", "0"));
				GameProperty property = GameProperty.getProperty(value);
				propertyMap[x][y] = property;
			}
		}

		shopBoard = new ShopBoard(new Apple(), new SuperApple(), new Apple(), 
				new SuperApple(), new Apple(), new SuperApple());	
	}

	/**
	 * 页面的绘画工作(地图,人物,以及当前页面的绘画)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		camera.drawMap();
		camera.translateGraphics();
		mc.getSprite().draw((int) mc.getX(), (int) mc.getY());
		switch (mc.status()) {
			case BAG:
				mc.getPlayer().renderBag(g, camera.getPosition());
				break;
			case SHOP:
				shopBoard.render(g, camera.getPosition(), mc.gold());
				break;
			default:
		}
	}

	/**
	 * 更新
	 * 类似一个监听控制方法,可以捕获玩家的输入来进行操作判断
	 * delta是距离上一次更新的时间差
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		/*
		 * 判断目前的状态,根据不同的状态进行不同的操作判断(比如在地图中的上下左右和购物商店的上下左右不是相同的操作)
		 * */
		switch (mc.status()) {
			case BAG:
				break;
			case SHOP:
				if (input.isKeyPressed(Input.KEY_UP)) {
					shopBoard.moveCursor(Direction.UP);
				} else if (input.isKeyPressed(Input.KEY_DOWN)) {
					shopBoard.moveCursor(Direction.DOWN);
				} else if (input.isKeyPressed(Input.KEY_LEFT)) {
					shopBoard.moveCursor(Direction.LEFT);
				} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
					shopBoard.moveCursor(Direction.RIGHT);
				} else if (input.isKeyPressed(Input.KEY_SPACE)) {
					int res = shopBoard.buy(mc.getPlayer());
					if (res == -2) {
						System.out.println("not enough gold");
					} else if (res == -1) {
						System.out.println("bag full");
					} else {
						System.out.println("success");
					}
				}
				break;
			default:
				/*
				 * 人物的行走 如果前方没有障碍物,就可以移动
				 */
				GameProperty[] frontTiles = null;
				boolean direction = false;
				if (input.isKeyDown(Input.KEY_UP)) {
					mc.turn(Direction.UP);
					direction = true;
				} else if (input.isKeyDown(Input.KEY_DOWN)) {
					mc.turn(Direction.DOWN);
					direction = true;
				} else if (input.isKeyDown(Input.KEY_LEFT)) {
					mc.turn(Direction.LEFT);
					direction = true;
				} else if (input.isKeyDown(Input.KEY_RIGHT)) {
					mc.turn(Direction.RIGHT);
					direction = true;
				}
				if (direction) {
					frontTiles = frontTiles(delta);
					if (!GameProperty.isBlocked(frontTiles[0]) && !GameProperty.isBlocked(frontTiles[1])) {
						mc.getSprite().update(delta);
						mc.forward(delta);
					}
				}
				
				if (input.isKeyDown(Input.KEY_P))
					mc.musicSwitch();
				if (input.isKeyPressed(Input.KEY_SPACE)) {
					frontTiles = frontTiles(delta);
					if (frontTiles[0] == GameProperty.APPLE || frontTiles[1] == GameProperty.APPLE) {
						if (mc.putItem(new Apple()) == 1) {
							System.out.println("you got an apple");
							clearTile(delta, GameProperty.APPLE, GameProperty.BLOCK);
						} else
							System.out.println("bag is full");
					}
					else if (frontTiles[0] == GameProperty.SHOP || frontTiles[1] == GameProperty.SHOP) {
						mc.active(Status.SHOP);
					}
				}
				camera.centerOn(mc.getX(), mc.getY());
				enterState(sbg);
		}
		if (input.isKeyPressed(Input.KEY_B)) {
			mc.active(Status.BAG);
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			shopBoard.moveCursor(Direction.NONE);
			mc.deactive();
		}
	}
	
	/**
	 * 清空前方的属性(比如可以清空前方已经获得的物品)
	 * @param delta
	 * @param currProperty
	 * @param destProperty
	 */
	private void clearTile(int delta, GameProperty currProperty, GameProperty destProperty) {
		Point[] pos = mc.getFrontPos(delta);
		if (propertyMap[pos[0].x][pos[0].y] == currProperty)
			propertyMap[pos[0].x][pos[0].y] = destProperty;
		else if (propertyMap[pos[1].x][pos[1].y] == currProperty)
			propertyMap[pos[1].x][pos[1].y] = destProperty;
	}
	
	/**
	 * 通过控制器返回的两个位置来获得前方的地图的属性
	 * @param delta
	 * @return
	 */
	private GameProperty[] frontTiles(int delta) {
		GameProperty[] properties = new GameProperty[2];
		Point[] front = mc.getFrontPos(delta);
		try {
			properties[0] = propertyMap[front[0].x][front[0].y];
		} catch (Exception e) {
			properties[0] = GameProperty.BLOCK;
		}
		try {
			properties[1] = propertyMap[front[1].x][front[1].y];			
		} catch (Exception e) {
			properties[1] = GameProperty.BLOCK;
		}
		return properties;
	}
	
	/**
	 * 判断是否要进入到另一张地图
	 * @param sbg
	 */
	private void enterState(StateBasedGame sbg) {
		if (mc.enterState(GameState.LABYRINTH))
			sbg.enterState(GameState.LABYRINTH.ordinal());
	}

	@Override
	public int getID() {
		return state.ordinal();
	}

}
