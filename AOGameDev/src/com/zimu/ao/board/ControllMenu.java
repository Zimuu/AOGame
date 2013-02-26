package com.zimu.ao.board;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.zimu.ao.enums.Menu;
import com.zimu.ao.state.AOGame;

public class ControllMenu extends RenderableScreen {
	
	private Menu menu;
	
	public ControllMenu() {
		draw = true;
		init();
	}

	@Override
	public boolean actionPerformed(Input input) {
		if (!draw) return false;
		if (input.isKeyPressed(Input.KEY_F1)) {
			menu = Menu.CM_SYS;
			cursor = Menu.SYS_S.ordinal();
		} else if (input.isKeyPressed(Input.KEY_F2)) {
			menu = Menu.CM_C;
			cursor = Menu.CHAR_C.ordinal();
		} else if (input.isKeyPressed(Input.KEY_F3)) {
			menu = Menu.CM_M1;
			cursor = 0;
		} else if (input.isKeyPressed(Input.KEY_F4)) {
			menu = Menu.CM_M2;
			cursor = 0;
		} else if (input.isKeyPressed(Input.KEY_F5)) {
			menu = Menu.CM_M3;
			cursor = 0;
		} else {
			if (menu == Menu.NONE) return false;
			if (input.isKeyPressed(Input.KEY_UP)) {
				if (menu == Menu.CM_SYS) {
					if (cursor > Menu.SYS_S.ordinal()) cursor--;
				} else if (menu == Menu.CM_C) {
					if (cursor > Menu.CHAR_C.ordinal()) cursor--;
				}
			} else if (input.isKeyPressed(Input.KEY_DOWN)) {
				if (menu == Menu.CM_SYS) {
					if (cursor < Menu.SYS_E.ordinal()) cursor++;
				} else if (menu == Menu.CM_C) {
					if (cursor < Menu.CHAR_M.ordinal()) cursor++;
				}				
			} else if (input.isKeyPressed(Input.KEY_SPACE)) {
				draw = false;
				setChanged();
				notifyObservers(Menu.valueOf(cursor));
			} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
				init();
			}
		}
		return true;
	}
	
	@Override
	public void render(Graphics g, Point point) {
		Color orgColor = g.getColor();
		int x = point.x;
		int y = point.y;
		
		g.setColor(Color.white);
		g.fillRect(x, y, AOGame.WIDTH, 15);
		g.setColor(Color.gray);
		g.drawString("System (F1)|", x + 15, y - 3);
		g.drawString("Character (F2)|", x + 135, y - 3);
		g.drawString("MENU 1 (F3)|", x + 285, y - 3);
		g.drawString("MENU 2 (F4)|", x + 405, y - 3);
		g.drawString("MENU 3 (F5)", x + 525, y - 3);
		g.setColor(Color.black);
		Menu m = Menu.valueOf(cursor);
		switch (menu) {
			case CM_SYS:
				g.drawString("System (F1)|", x + 15, y - 3);
	
				g.setColor(Color.white);
				g.fillRect(x, y + 15, 120, 110);
				g.setColor(Color.gray);
				g.drawString("Save", x + 38, y + 20);
				g.drawString("Load", x + 38, y + 50);
				g.drawString("Settings", x + 20, y + 75);
				g.drawString("Exit", x + 38, y + 100);
				g.setColor(Color.black);
				switch (m) {
					case SYS_S:
						g.drawString("Save", x + 38, y + 20);
						break;
					case SYS_L:
						g.drawString("Load", x + 38, y + 50);
						break;
					case SYS_O:
						g.drawString("Settings", x + 20, y + 75);
						break;
					case SYS_E:
						g.drawString("Exit", x + 38, y + 100);
						break;
					default:
				}
				break;
			case CM_C:
				g.drawString("Character (F2)|", x + 135, y - 3);

				g.setColor(Color.white);
				g.fillRect(x + 120, y + 15, 147, 160);
				g.setColor(Color.gray);
				g.drawString("Character", x + 153, y + 20);
				g.drawString("Equipment", x + 153, y + 45);
				g.drawString("Skill tree", x + 148, y + 70);
				g.drawString("Items", x + 171, y + 95);
				g.drawString("Quest", x + 171, y + 120);
				g.drawString("Map", x + 179, y + 145);
				g.setColor(Color.black);
				switch (m) {
					case CHAR_C:
						g.drawString("Character", x + 153, y + 20);
						break;
					case CHAR_E:
						g.drawString("Equipment", x + 153, y + 45);
						break;
					case CHAR_S:
						g.drawString("Skill tree", x + 148, y + 70);
						break;
					case CHAR_I:
						g.drawString("Items", x + 171, y + 95);
						break;
					case CHAR_Q:
						g.drawString("Quest", x + 171, y + 120);
						break;
					case CHAR_M:
						g.drawString("Map", x + 179, y + 145);
						break;
					default:
				}
				break;
			case CM_M1:
				g.drawString("MENU 1 (F3)|", x + 285, y - 3);
				break;
			case CM_M2:
				g.drawString("MENU 2 (F4)|", x + 405, y - 3);
				break;
			case CM_M3:
				g.drawString("MENU 3 (F5)", x + 525, y - 3);
				break;
			default:
				break;
		}
		g.setColor(orgColor);
	}
	
	@Override
	protected void init() {
		menu = Menu.NONE;
		cursor = 0;
	}

}
