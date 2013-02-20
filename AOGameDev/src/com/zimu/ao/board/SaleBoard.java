package com.zimu.ao.board;

import java.awt.Point;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.zimu.ao.character.Player;
import com.zimu.ao.enums.Direction;
import com.zimu.ao.enums.Status;
import com.zimu.ao.item.Item;

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
		g.setColor(Color.white);
		g.fillRect(point.x + 20, point.y + 20, 600, 400);
		g.setColor(Color.black);
		g.drawString("Gold: " + String.valueOf(gold), point.x + 50, point.y + 20);
		g.drawString("Item           Price Amount", point.x + 60, point.y + 60);
		
		int baseX = point.x + 60;
		int baseY = point.y + 90;
		for (int i = top_cursor, j = 0; j < ITEMS_PER_PAGE + 1; i++, j++) {
			if (i >= totalItems) break;
			Item item = null;
			try {
				item = items[i];
			} catch (Exception e) {
				break;
			}
			if (item == null) break;
			g.drawImage(item.getItem().getImage(), baseX, baseY + (j * 55));
			g.drawImage(item.getItem().getLabel(), baseX + 35, baseY + (j * 55));
			g.drawString(String.valueOf(item.getItem().getPrice() / 2), baseX + 155, baseY + 7 + (j * 55));
			g.drawString(String.valueOf(item.getAmount()), baseX + 205, baseY + 7 + (j * 55));
			if (cursor == i) {
				g.drawImage(item.getItem().getDescription(), baseX + 250, baseY);
				g.drawRect(baseX - 30, baseY - 10 + (j * 55), 265, 55);
			}
		}
		if (status == Status.SHOP_CONSUMABLE) {
			g.drawString("Consumable", baseX + 150, point.y + 30);
			g.setColor(Color.gray);
			g.drawString("Tools", baseX + 270, point.y + 30);
			g.drawString("Quest", baseX + 350, point.y + 30);
		} else if (status == Status.SHOP_TOOLS) {
			g.drawString("Tools", baseX + 270, point.y + 30);
			g.setColor(Color.gray);
			g.drawString("Consumable", baseX + 150, point.y + 30);
			g.drawString("Quest", baseX + 350, point.y + 30);
		}
		g.setColor(orgColor);
	}

}
