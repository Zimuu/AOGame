package com.zimu.ao.enums;

public enum GameProperty {
	
	BLOCK, FOE, SAFE, APPLE, NPC, MISSION, UNDERGROUND, SHOP;
	
	public static GameProperty getProperty(int prop) {
		switch (prop) {
			case 0:
				return BLOCK;
			case 1:
				return FOE;
			case 2:
				return SAFE;
			case 3:
				return APPLE;
			case 4:
				return NPC;
			case 5:
				return MISSION;
			case 6:
				return UNDERGROUND;
			case 7:
				return SHOP;
			default :
				return BLOCK;
		}
	}
	
	public static boolean isBlocked(GameProperty property) {
		if (property == null)
			return true;
		switch (property) {
			case BLOCK: case APPLE: case SHOP: case NPC:
				return true;
			default :
				return false;
		}
	}
}
