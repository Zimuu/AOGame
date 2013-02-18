package com.zimu.ao.board;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.zimu.ao.enums.Direction;
import com.zimu.ao.enums.Status;

public class PreShopBoard {

	private int cursor;
	
	public PreShopBoard() {
		
	}
	
	public void moveCursor(Direction direction) {
		if (direction == Direction.RIGHT)
			cursor = 1;
		else if (direction == Direction.LEFT) 
			cursor = 0;
	}
	
	public void render(Graphics g, Point point) {
		Color orgColor = g.getColor();
		g.setColor(Color.white);
		
		int baseX = point.x + 150;
		int baseY = point.y + 120;
		g.fillRect(baseX, baseY, 300, 120);
		g.setColor(Color.black);
		g.drawString("What do you want?", baseX + 20, baseY + 30);
		g.drawString("Buy", baseX + 60, baseY + 80);
		g.drawString("Sell", baseX + 180, baseY + 80);
		if (cursor == 0)
			g.drawRect(baseX + 43, baseY + 75, 65, 30);
		else if (cursor == 1)
			g.drawRect(baseX + 167, baseY + 75, 65, 30);
		g.setColor(orgColor);
	}
	
	public Status getSelected() {
		return cursor == 0 ? Status.BUY : Status.SELL;
	}

}
