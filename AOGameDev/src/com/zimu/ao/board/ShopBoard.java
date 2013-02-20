package com.zimu.ao.board;

import java.awt.Font;
import java.awt.Point;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import com.zimu.ao.character.Player;
import com.zimu.ao.enums.Direction;
import com.zimu.ao.item.AbstractItem;

/**
 * 商店页面
 * 负责商店页面的绘图,构造方法参数为商品数组
 * 商品将按照每列三个进行排列
 * @author zimu
 *
 */
@SuppressWarnings("deprecation")
public class ShopBoard {
	
	private final int ITEMS_PER_PAGE = 5;
		
	private AbstractItem[] items;
	private int totalItems;
	private int top_cursor;
	private int cursor;
	private TrueTypeFont font = new TrueTypeFont(new Font("Verdana", Font.BOLD, 22), true);
	
	public ShopBoard(AbstractItem...items) {
		setItems(items);
	}
	
	private void setItems(AbstractItem...items) {
		totalItems = items.length;
		this.items = Arrays.copyOf(items, totalItems);
	}
	
	public void initCursor() {
		cursor = 0;
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
	
	public int buy(Player player) {
		AbstractItem item = items[cursor];
		
		if (player.getGold() < item.getPrice())
			return -2;
		if (player.putItem(item) == -1)
			return -1;
		player.takeGold(0 - item.getPrice());
		return 1;
	}
	
	public void render(Graphics g, Point point, int gold) {
		Color orgColor = g.getColor();
		g.setColor(Color.white);
		g.setFont(font);
		g.fillRect(point.x + 20, point.y + 20, 600, 400);
		g.setColor(Color.black);
		g.drawString("Gold: " + String.valueOf(gold), point.x + 50, point.y + 20);
		g.drawString("Item", point.x + 80, point.y + 45);
		g.drawString("Price", point.x + 220, point.y + 45);
		
		int baseX = point.x + 80;
		int baseY = point.y + 90;
		for (int i = top_cursor, j = 0; j < ITEMS_PER_PAGE + 1; i++, j++) {
			if (i >= totalItems) break;
			AbstractItem item = items[i];
			if (item == null) break;
			g.drawImage(item.getImage(), baseX, baseY + (j * 55));
			g.drawImage(item.getLabel(), baseX + 35, baseY + (j * 55));
			g.drawString(String.valueOf(item.getPrice()), baseX + 150, baseY + 7 + (j * 55));
			if (cursor == i) {
				g.drawImage(item.getDescription(), baseX + 220, baseY);
				g.drawRect(baseX - 30, baseY - 10 + (j * 55), 235, 55);
			}
		}
		g.setColor(orgColor);
	}
	
}
