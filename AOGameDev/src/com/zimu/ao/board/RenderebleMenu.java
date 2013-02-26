package com.zimu.ao.board;

import java.awt.Point;
import java.util.Observable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class RenderebleMenu extends Observable {
	
	protected int status = -1;
	protected int cursor;
	protected boolean draw;

	public abstract void actionPerformed(Input input);
	
	public abstract void render(Graphics g, Point p);

	
	public void setDraw(boolean draw) {
		this.draw = draw;
	}
	
	public boolean draw() {
		return draw;
	}
	
}
