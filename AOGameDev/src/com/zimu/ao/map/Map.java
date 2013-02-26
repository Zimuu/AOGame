package com.zimu.ao.map;

import org.newdawn.slick.tiled.TiledMap;

import com.zimu.ao.board.CharBoard;
import com.zimu.ao.board.CharMenu;
import com.zimu.ao.board.ControllMenu;
import com.zimu.ao.board.ItemBoard;
import com.zimu.ao.board.SaleBoard;
import com.zimu.ao.board.ShopBoard;
import com.zimu.ao.board.ShopMenu;
import com.zimu.ao.board.SystemMenu;
import com.zimu.ao.controller.MainController;
import com.zimu.ao.enums.GameProperty;
import com.zimu.ao.enums.GameState;
import com.zimu.ao.npc.NPC;
import com.zimu.ao.tools.Camera;

public class Map {

	protected GameState state;
	
	protected TiledMap tileMap;
	protected GameProperty[][] propertyMap;
	protected NPC[][] npcMap;
	protected String curr_dialog;
	
	protected ControllMenu controllMenu;
	protected ShopMenu shopMenu;
	protected SystemMenu systemMenu;
	protected CharMenu charMenu;
	
	protected ShopBoard consumableShop;
	protected ShopBoard usableShop;
	protected ShopBoard equipmentShop;
	protected SaleBoard saleBoard;
	protected ItemBoard itemBoard;
	protected CharBoard charBoard;
	
	protected Camera camera;
	protected MainController mc;
	
	public Map() {}

}
