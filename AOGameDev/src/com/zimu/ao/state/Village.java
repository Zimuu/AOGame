package com.zimu.ao.state;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import com.zimu.ao.board.CharBoard;
import com.zimu.ao.board.ControllMenu;
import com.zimu.ao.board.EquipmentBoard;
import com.zimu.ao.board.ItemBoard;
import com.zimu.ao.board.ShopBoard;
import com.zimu.ao.controller.MainController;
import com.zimu.ao.enums.Direction;
import com.zimu.ao.enums.GameProperty;
import com.zimu.ao.enums.GameState;
import com.zimu.ao.enums.Menu;
import com.zimu.ao.enums.Status;
import com.zimu.ao.item.consumable.Apple;
import com.zimu.ao.item.consumable.SuperApple;
import com.zimu.ao.npc.NPC;
import com.zimu.ao.tools.Camera;

public class Village extends BasicGameState implements Observer {
	
	private GameState state;
	
	private TiledMap tileMap;
	private GameProperty[][] propertyMap;
	private NPC[][] npcMap;
	private String curr_dialog;
	
	private ControllMenu controllMenu;
	
	private ShopBoard shopBoard;
	private ItemBoard itemBoard;
	private EquipmentBoard equipmentBoard;
	private CharBoard charBoard;
	
	private Camera camera;
	private MainController mc;
	
	public Village(GameState state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		mc = MainController.getInstance();
		tileMap = new TiledMap("resource/map/village.tmx");
		camera = new Camera(gc, tileMap);

		propertyMap = new GameProperty[tileMap.getWidth()][tileMap.getHeight()];
		npcMap = new NPC[propertyMap.length][propertyMap[0].length];
		
		int npc_index = 0;
		for (int x = 0; x < tileMap.getWidth(); x++) {
			for (int y = 0; y < tileMap.getHeight(); y++) {
				int tileID = tileMap.getTileId(x, y, 0);
				int value = Integer.parseInt(tileMap.getTileProperty(tileID, "prop", "0"));
				GameProperty property = GameProperty.getProperty(value);
				propertyMap[x][y] = property;
				if (property == GameProperty.NPC) {
					switch (npc_index++) {
						case 0:
							npcMap[x][y] = new NPC(null, "Hahaha", "Hehehe", "Hohoho");
							break;
						case 1:
							npcMap[x][y] = new NPC(null, "WTF", "LOL", "NOOB");
							break;
						default:
					}
				}
			}
		}
		
		controllMenu = new ControllMenu();
		
		shopBoard = new ShopBoard(mc.getPlayer(),
				new Apple(), new SuperApple(), new Apple(), 
				new SuperApple(), new Apple(), new SuperApple(),
				new SuperApple(), new Apple(), new SuperApple());
		itemBoard = new ItemBoard(mc.getPlayer());
		charBoard = new CharBoard(mc.getPlayer());
		equipmentBoard = new EquipmentBoard(mc.getPlayer());

		controllMenu.addObserver(this);
		shopBoard.addObserver(this);
		itemBoard.addObserver(this);
		charBoard.addObserver(this);
		equipmentBoard.addObserver(this);
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Point p = camera.getPosition();
		
		camera.drawMap();
		camera.translateGraphics();
		mc.getSprite().draw((int) mc.getX(), (int) mc.getY());
		if (itemBoard.draw())
			itemBoard.render(g, p);
		else if (charBoard.draw()) 
			charBoard.render(g, p);
		else if (shopBoard.draw())
			shopBoard.render(g, p);
		else if (equipmentBoard.draw())
			equipmentBoard.render(g, p);
		else if (controllMenu.draw())
			controllMenu.render(g, p);
		switch (mc.status()) {
			case NPC:
				NPC.render(g, camera.getPosition(), curr_dialog);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {}
				break;
			default:
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		boolean performed = false;
		if (controllMenu.actionPerformed(input))
			performed = true;
		else if (itemBoard.actionPerformed(input))
			performed = true;
		else if (charBoard.actionPerformed(input))
			performed = true;
		else if (shopBoard.actionPerformed(input))
			performed = true;
		else if (equipmentBoard.actionPerformed(input))
			performed = true;
		else if (!performed) {
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
				} else if (frontTiles[0] == GameProperty.SHOP || frontTiles[1] == GameProperty.SHOP) {
					shopBoard.setDraw(true);
					controllMenu.setDraw(false);
				} else if (frontTiles[0] == GameProperty.NPC) {
					Point[] pos = mc.getFrontPos(delta);
					curr_dialog = npcMap[pos[0].x][pos[0].y].getDialog();
					mc.active(Status.NPC);
				}
			}
			camera.centerOn(mc.getX(), mc.getY());
			enterState(sbg);
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
	public void update(Observable o, Object arg) {
		if (o.getClass() == controllMenu.getClass()) {
			Menu menu = (Menu) arg;
			charBoard.setDraw(false);
			equipmentBoard.setDraw(false);
			itemBoard.setDraw(false);
			controllMenu.setDraw(false);
			switch (menu) {
				case SYS_S: case SYS_L: case SYS_O: case SYS_E: break;
				case CHAR_C:
					charBoard.setDraw(true);
					break;
				case CHAR_E:
					equipmentBoard.setDraw(true);
					break;
				case CHAR_I:
					itemBoard.setDraw(true);
					break;
				case CHAR_S:
					break;
				case NONE:
					controllMenu.setDraw(true);					
					break;
				default:
			}
		} else {
			if (((Menu) arg) == Menu.NONE) controllMenu.setDraw(true);
		}
	}

	@Override
	public int getID() {
		return state.ordinal();
	}

}
