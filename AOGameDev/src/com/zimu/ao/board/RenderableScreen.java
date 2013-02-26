package com.zimu.ao.board;

import java.awt.Point;
import java.util.Observable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.zimu.ao.character.Player;

public abstract class RenderableScreen extends Observable {

	protected Player player;
	protected boolean draw;
	
	protected int cursor;
	protected int topCursor;
	
	public RenderableScreen() {}
	
	public abstract boolean actionPerformed(Input input);
	
	public abstract void render(Graphics g, Point p);
	
	protected abstract void init();
	
	public void setDraw(boolean draw) {
		this.draw = draw;
		init();
	}
	
	public boolean draw() {
		return draw;
	}
	
}
