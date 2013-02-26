package com.zimu.ao.board;

import java.awt.Point;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.zimu.ao.character.Player;
import com.zimu.ao.enums.Direction;
import com.zimu.ao.enums.Status;
import com.zimu.ao.item.Item;
import com.zimu.ao.state.AOGame;

public class SaleBoard {
	
	private final int ITEMS_PER_PAGE = 5;
	private Status status = Status.SHOP_CONSUMABLE;
	
	private Item[] items;
	private int totalItems;
	
	private int cursor;
	private int top_cursor;
	
	public SaleBoard() {}
	
	public void setItems(Item[] it) {
		int i = 0;
		for (; i < it.length; i++)
			if (it[i] == null) break;
		items = Arrays.copyOf(it, i);
		totalItems = items.length;
		cursor = 0;
		top_cursor = 0;
	}
	
	public void sell(Player player) {
		if (totalItems == 0)
			return;
		Item[] it = player.sellItem(cursor, 1, status);
		if (it != null)
			items = Arrays.copyOf(it, it.length);
	}
	
	public Status switchItemPage() {
		if (status == Status.SHOP_CONSUMABLE)
			status = Status.SHOP_TOOLS;
		else if (status == Status.SHOP_TOOLS)
			status = Status.SHOP_CONSUMABLE;
		return status;
	}
	
	public void moveCursor(Direction direction) {
		if (direction == Direction.UP) {
			if (cursor >= 1) cursor--;
		} else if (direction == Direction.DOWN) {
			if (cursor < totalItems - 1) cursor++;
		}
		if (cursor <= ITEMS_PER_PAGE) top_cursor = 0;
		else top_cursor = cursor - ITEMS_PER_PAGE;
	}
	
	public void render(Graphics g, Point point, int gold) {
		Color orgColor = g.getColor();
		int x = point.x;
		int y = point.y;
		
		g.setColor(Color.white);
		g.fillRect(point.x, point.y, AOGame.WIDTH, AOGame.HEIGHT);
		g.setColor(Color.black);
		g.drawRect(x + 20, y + 30, 275, 425);
		g.drawLine(x + 20, y + 60, 275 + x + 20, y + 60);
		g.drawString("Gold: " + String.valueOf(gold), x + 10, y + 10);
		g.drawString("Item          Price Amount", x + 60, y + 35);

		x = point.x + 30;
		y = point.y + 80;
		for (int i = top_cursor, j = 0; j < ITEMS_PER_PAGE + 1; i++, j++) {
			if (i >= totalItems) break;
			Item item = null;
			try {
				item = items[i];
			} catch (Exception e) {
				break;
			}
			if (item == null) break;
			g.drawImage(item.getItem().getImage(), x, y + (j * 55));
			g.drawImage(item.getItem().getLabel(), x + 35, y + (j * 55));
			g.drawString(String.valueOf(item.getItem().getPrice() / 2), x + 175, y + 7 + (j * 55));
			g.drawString(String.valueOf(item.getAmount()), x + 225, y + 7 + (j * 55));
			if (cursor == i) {
				g.drawImage(item.getItem().getDescription(), x + 270, y);
				g.drawRect(x - 10, y - 10 + (j * 55), 275, 55);
			}
		}
		if (status == Status.SHOP_CONSUMABLE) {
			g.drawString("Consumable", x + 160, point.y + 10);
			g.setColor(Color.gray);
			g.drawString("Tools", x + 270, point.y + 10);
			g.drawString("Quest", x + 360, point.y + 10);
		} else if (status == Status.SHOP_TOOLS) {
			g.drawString("Tools", x + 280, point.y + 10);
			g.setColor(Color.gray);
			g.drawString("Consumable", x + 160, point.y + 10);
			g.drawString("Quest", x + 360, point.y + 10);
		}
		g.setColor(orgColor);
	}

}
