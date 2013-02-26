package com.zimu.ao.board;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.zimu.ao.enums.Direction;
import com.zimu.ao.enums.Status;

public class SystemMenu {

	private final int OPTIONS = 3;
	
	private int cursor;
	
	public SystemMenu() {}
	
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
		int x = point.x;
		int y = point.y + 15;
		
		g.setColor(Color.white);
		g.fillRect(x, y, 120, 110);
		g.setColor(Color.gray);
		x = point.x + 20;
		g.drawString("Save", x + 18, y + 10);
		g.drawString("Load", x + 18, y + 35);
		g.drawString("Settings", x, y + 60);
		g.drawString("Exit", x + 18, y + 85);
		g.setColor(Color.black);
		if (cursor == 0) {
			g.drawString("Save", x + 18, y + 10);
		} else if (cursor == 1) {
			g.drawString("Load", x + 18, y + 35);	
		} else if (cursor == 2) {
			g.drawString("Settings", x, y + 60);
		} else if (cursor == 3) {
			g.drawString("Exit", x + 18, y + 85);
		}
		g.setColor(orgColor);
	}
	
	public Status getSelected() {
		if (cursor == 0)
			return Status.SYS_S;
		else if (cursor == 1)
			return Status.SYS_L;
		else if (cursor == 2)
			return Status.SYS_O;
		else if (cursor == 3)
			return Status.SYS_E;
		else
			return Status.NONE;
	}

}
