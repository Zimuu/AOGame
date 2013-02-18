package com.zimu.ao.state;

import java.awt.Point;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import com.zimu.ao.board.PreShopBoard;
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
	
	private GameState state;
	
	private TiledMap tileMap;
	private GameProperty[][] propertyMap;
	private ShopBoard shopBoard;
	private PreShopBoard preShopBoard;
	
	private Camera camera;
	private MainController mc;
	
	public Village(GameState state) {
		this.state = state;
	}

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
		
		shopBoard = new ShopBoard(
				new Apple(), new SuperApple(), new Apple(), 
				new SuperApple(), new Apple(), new SuperApple(),
				new SuperApple(), new Apple(), new SuperApple());
		preShopBoard = new PreShopBoard();
	}

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
				preShopBoard.render(g, camera.getPosition());
				break;
			case BUY:
				shopBoard.render(g, camera.getPosition(), mc.gold());
				break;
			case SELL:
				break;
			default:
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		switch (mc.status()) {
			case BAG:
				break;
			case SHOP:
				if (input.isKeyPressed(Input.KEY_RIGHT)) {
					preShopBoard.moveCursor(Direction.RIGHT);
				} else if (input.isKeyPressed(Input.KEY_LEFT)) {
					preShopBoard.moveCursor(Direction.LEFT);
				} else if (input.isKeyPressed(Input.KEY_SPACE)) {
					mc.active(preShopBoard.getSelected());
				}
				break;
			case BUY:
				if (input.isKeyPressed(Input.KEY_UP)) {
					shopBoard.moveCursor(Direction.UP);
				} else if (input.isKeyPressed(Input.KEY_DOWN)) {
					shopBoard.moveCursor(Direction.DOWN);
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
	
	private void clearTile(int delta, GameProperty currProperty, GameProperty destProperty) {
		Point[] pos = mc.getFrontPos(delta);
		if (propertyMap[pos[0].x][pos[0].y] == currProperty)
			propertyMap[pos[0].x][pos[0].y] = destProperty;
		else if (propertyMap[pos[1].x][pos[1].y] == currProperty)
			propertyMap[pos[1].x][pos[1].y] = destProperty;
	}
	
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
	
	private void enterState(StateBasedGame sbg) {
		if (mc.enterState(GameState.LABYRINTH))
			sbg.enterState(GameState.LABYRINTH.ordinal());
	}

	@Override
	public int getID() {
		return state.ordinal();
	}

}
