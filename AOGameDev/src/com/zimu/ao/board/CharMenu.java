package com.zimu.ao.board;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.zimu.ao.enums.Direction;
import com.zimu.ao.enums.Status;

public class CharMenu {

	private final int OPTIONS = 5;
	
	private int cursor;
	
	public CharMenu() {}
	
	public void moveCursor(Direction direction) {
		if (direction == Direction.UP) {
			if (cursor != 0)
				cursor--;
		} else if (direction == Direction.DOWN) {
			if (cursor != OPTIONS)
				cursor++;
		}
		
	}
	
	public void render(Graphics g, Point point) {
		Color orgColor = g.getColor();
		g.setColor(Color.white);
		
		int x = point.x + 120;
		int y = point.y + 15;
		g.fillRect(x, y, 147, 160);
		g.setColor(Color.gray);
		x = point.x + 148;
		y = point.y + 20;
		g.drawString("Character", x + 5, y);
		g.drawString("Equipment", x + 5, y + 25);
		g.drawString("Skill tree", x, y + 50);
		g.drawString("Items", x + 23, y + 75);
		g.drawString("Quest", x + 23, y + 100);
		g.drawString("Map", x + 31, y + 125);
		g.setColor(Color.black);
		if (cursor == 0) {
			g.drawString("Character", x + 5, y);
		} else if (cursor == 1) {
			g.drawString("Equipment", x + 5, y + 25);		
		} else if (cursor == 2) {
			g.drawString("Skill tree", x, y + 50);	
		} else if (cursor == 3) {
			g.drawString("Items", x + 23, y + 75);
		} else if (cursor == 4) {
			g.drawString("Quest", x + 23, y + 100);
		} else if (cursor == 5) {
			g.drawString("Map", x + 31, y + 125);			
		}
		g.setColor(orgColor);
	}
	
	public Status getSelected() {
		if (cursor == 0)
			return Status.CHAR_INFO;
		else if (cursor == 1)
			return Status.CHAR_EQUIP;
		else if (cursor == 2)
			return Status.CHAR_SKILL;
		else if (cursor == 3)
			return Status.CHAR_ITEM;
		else if (cursor == 4)
			return Status.CHAR_QUEST;
		else if (cursor == 5)
			return Status.CHAR_MAP;
		else
			return Status.NONE;
	}

}
