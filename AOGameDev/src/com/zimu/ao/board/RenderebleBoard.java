package com.zimu.ao.board;

import java.awt.Point;

import org.newdawn.slick.Graphics;

import com.zimu.ao.enums.Direction;

public abstract class RenderebleBoard {
	
	protected int cursor;
	protected int topCursor;

	public abstract void moveCursor(Direction direction);
	
	public abstract void tabSwitch();
	
	public abstract void render(Graphics g, Point point);

}
