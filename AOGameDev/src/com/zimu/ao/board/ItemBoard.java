package com.zimu.ao.board;

import java.awt.Point;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.zimu.ao.character.Player;
import com.zimu.ao.enums.Menu;
import com.zimu.ao.item.Item;
import com.zimu.ao.state.AOGame;

public class ItemBoard extends RenderableScreen {
	
	public static final int CONSUMABLE = 0;
	public static final int EQUIPMENT = 1;
	public static final int QUEST = 2;
	
	private final int ITEMS_PER_PAGE = 6;
	
	private List<Item> items;
	
	private int page;
	
	public ItemBoard(Player player) {
		this.player = player;
	}
	
	public boolean actionPerformed(Input input) {
		if (!draw) return false;
		if (input.isKeyPressed(Input.KEY_TAB)) {
			if (page == CONSUMABLE) {
				page++;
				items = player.getEquipments();
			} else if (page == EQUIPMENT) {
				page++;
				items = player.getQuestItems();
			} else if (page == QUEST) {
				page = 0;
				items = player.getConsumable();
			}
		} else if (input.isKeyPressed(Input.KEY_UP)) {
			if (cursor >= 1) cursor--;
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			if (cursor < items.size() - 1) cursor++;
		} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			draw = false;
			setChanged();
			notifyObservers(Menu.valueOf(0));
		}
		
		if (cursor <= ITEMS_PER_PAGE) topCursor = 0;
		else topCursor = cursor - ITEMS_PER_PAGE;
		return true;
	}
	
	public void render(Graphics g, Point point) {
		Color orgColor = g.getColor();
		int x = point.x;
		int y = point.y;

		g.setColor(Color.white);
		g.fillRect(point.x, point.y, AOGame.WIDTH, AOGame.HEIGHT);
		g.setColor(Color.black);
		g.drawRect(x + 20, y + 30, 275, 425);
		g.drawLine(x + 20, y + 60, 275 + x + 20, y + 60);
		g.drawString("Gold: " + String.valueOf(player.getGold()), x + 10, y + 10);
		g.drawString("Item          Price Amount", x + 60, y + 35);

		for (int i = topCursor, j = 0; j < ITEMS_PER_PAGE + 1; i++, j++) {
			if (i >= items.size()) break;
			Item item = null;
			try {
				item = items.get(i);
			} catch (Exception e) {
				break;
			}
			if (item == null) break;
			g.drawImage(item.getItem().getImage(), x + 30, y + 80 + (j * 55));
			g.drawImage(item.getItem().getLabel(), x + 65, y + 80 + (j * 55));
			g.drawString(String.valueOf(item.getItem().getPrice() / 2), x + 205, y + 87 + (j * 55));
			g.drawString(String.valueOf(item.getAmount()), x + 255, y + 87 + (j * 55));
			if (cursor == i) {
				g.drawImage(item.getItem().getDescription(), x + 300, y + 80);
				g.drawRect(x + 20, y + 70 + (j * 55), 275, 55);
			}
		}
		if (page == CONSUMABLE) {
			g.drawString("Consumable", x + 210, y + 10);
			g.setColor(Color.gray);
			g.drawString("Equipments", x + 330, y + 10);
			g.drawString("QuestItems", x + 450, y + 10);
		} else if (page == EQUIPMENT) {
			g.drawString("Equipments", x + 330, y + 10);
			g.setColor(Color.gray);
			g.drawString("Consumable", x + 210, y + 10);
			g.drawString("QuestItems", x + 450, y + 10);
		} else if (page == QUEST) {
			g.drawString("QuestItems", x + 450, y + 10);
			g.setColor(Color.gray);
			g.drawString("Consumable", x + 210, y + 10);
			g.drawString("Equipments", x + 330, y + 10);
		}
		g.setColor(orgColor);
	}
	
	@Override
	protected void init() {
		page = CONSUMABLE;
		items = player.getConsumable();
		cursor = 0;
		topCursor = 0;		
	}

}
