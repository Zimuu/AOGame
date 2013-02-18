package com.zimu.ao.enums;

public enum GameProperty {
	
	BLOCK, FOE, SAFE, APPLE, TBD, MISSION, UNDERGROUND, SHOP;
	
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
				return TBD;
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
			case BLOCK: case APPLE: case SHOP:
				return true;
			default :
				return false;
		}
	}
}
