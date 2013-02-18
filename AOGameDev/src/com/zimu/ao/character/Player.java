package com.zimu.ao.character;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.zimu.ao.item.AbstractItem;
import com.zimu.ao.item.Item;

/**
 * 玩家信息(总体)
 * 包含玩家的包裹,金钱,等等信息.
 * @author zimu
 *
 */
public class Player {
	
	private final int BAG_ROWS = 1;
	private final int BAG_COLS = 3;

	private static Player instance;
	private int gold = 1000;
	
	private Item[][] bag;
	
	private Player() {
		bag = new Item[BAG_ROWS][BAG_COLS];
	}
	
	public static Player getInstance() {
		if (instance == null)
			instance = new Player();
		return instance;
	}
	
	public int putItem(AbstractItem item) {
		boolean added = false;
		int x = 0, y = 0;
		
		loop:
		for (x = 0; x < BAG_ROWS; x++)
			for (y = 0; y < BAG_COLS; y++) {
				Item it = null;
				try {
					it = bag[x][y];
				} catch (ArrayIndexOutOfBoundsException e) {
					break loop;
				}
				if (it == null)
					break loop;
				else if (it.equals(item) && it.getAmount() < 25) {
					added = true;
					it.add(1);
					break loop;
				}
			}
		if (!added) {
			if (x >= BAG_ROWS)
				return -1;
			bag[x][y] = new Item(item);
			bag[x][y].add(1);
		}
		
		return 1;
	}
	
	public void renderBag(Graphics g, Point point) {
		Color orgColor = g.getColor();
		g.setColor(Color.white);
		g.fillRect(point.x + 20, point.y + 20, 600, 400);
		g.setColor(Color.black);
		g.drawString("Gold: " + String.valueOf(gold), point.x + 50, point.y + 20);
		
		int baseX = point.x + 30;
		int baseY = point.y + 60;	
		for (int i = 0; i < bag.length; i++)
			for (int j = 0; j < bag[i].length; j++) {
				AbstractItem item = null;
				try {
					item = bag[i][j].getItem();
				} catch (NullPointerException e) {
					break;
				}
				g.drawImage(item.getImage(), baseX + (j * 200), baseY + (i * 85));
				g.drawImage(item.getLabel(), baseX + 35 + (j * 200), baseY + (i * 85));
				g.drawImage(item.getDescription(), baseX + (j * 200), baseY + 35 + (i * 85));
				g.drawString(String.valueOf(bag[i][j].getAmount()), baseX + 150 + (j * 200), baseY + (i * 85));
			}
		
		g.setColor(orgColor);
	}

	public int getGold() {
		return gold;
	}

	public void takeGold(int gold) {
		this.gold += gold;
	}
	
}
