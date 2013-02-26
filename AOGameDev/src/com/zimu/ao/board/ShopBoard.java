package com.zimu.ao.board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.zimu.ao.character.Player;
import com.zimu.ao.enums.Menu;
import com.zimu.ao.item.AbstractItem;
import com.zimu.ao.item.Item;
import com.zimu.ao.state.AOGame;

public class ShopBoard extends RenderableScreen {
	
	private final int DIALOG = 0;
	private final int BUY = 1;
	private final int SELL = 2;
	
	private final int CONSUMABLE = 0;
	private final int EQUIPMENT = 1;
	
	private final int ITEMS_PER_PAGE = 6;
	
	private final List<AbstractItem> shopItems;
	
	private int status;
	
	private List<Item> playerItems;
	
	private List<AbstractItem> items;
	
	private int page;
	
	public ShopBoard(Player player, AbstractItem...items) {
		this.player = player;
		shopItems = new ArrayList<AbstractItem>();
		for (AbstractItem it : items)
			shopItems.add(it);
		init();
	}

	@Override
	public boolean actionPerformed(Input input) {
		if (!draw) return false;
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			draw = false;
			setChanged();
			notifyObservers(Menu.valueOf(0));
			return true;
		}
		if (status == DIALOG) {
			if (input.isKeyPressed(Input.KEY_RIGHT)) {
				cursor = 1;
			} else if (input.isKeyPressed(Input.KEY_LEFT)) {
				cursor = 0;
			} else if (input.isKeyPressed(Input.KEY_SPACE)) {
				if (cursor == 0) {
					status = BUY;
					items = shopItems;
				} else {
					status = SELL;
					playerItems = player.getConsumable();
				}
			}
		} else if (status == BUY) {
			if (input.isKeyPressed(Input.KEY_UP)) {
				if (cursor >= 1) cursor--;
			} else if (input.isKeyPressed(Input.KEY_DOWN)) {
				if (cursor < items.size() - 1) cursor++;
			} else if (input.isKeyPressed(Input.KEY_SPACE)) {
				buy();
			}
			if (cursor <= ITEMS_PER_PAGE) topCursor = 0;
			else topCursor = cursor - ITEMS_PER_PAGE;
		} else if (status == SELL) {
			if (input.isKeyPressed(Input.KEY_UP)) {
				if (cursor >= 1) cursor--;
			} else if (input.isKeyPressed(Input.KEY_DOWN)) {
				if (cursor < playerItems.size() - 1) cursor++;
			} else if (input.isKeyPressed(Input.KEY_TAB)) {
				if (page == CONSUMABLE) {
					page = EQUIPMENT;
					playerItems = player.getEquipments();
				} else { 
					page = CONSUMABLE;
					playerItems = player.getConsumable();
				}
			} else if (input.isKeyPressed(Input.KEY_SPACE)) {
				sell();
			}
			if (cursor <= ITEMS_PER_PAGE) topCursor = 0;
			else topCursor = cursor - ITEMS_PER_PAGE;
		}
		return true;
	}
	
	private void sell() {
		if (items.size() == 0)
			return;
		player.sellItem(cursor, 1, status);
	}
	
	private int buy() {
		AbstractItem item = items.get(cursor);
		
		if (player.getGold() < item.getPrice())
			return -2;
		if (player.putItem(item) == -1)
			return -1;
		player.takeGold(0 - item.getPrice());
		return 1;
	}

	@Override
	public void render(Graphics g, Point point) {
		Color orgColor = g.getColor();
		if (status == DIALOG)
			drawDialog(g, point.x, point.y);
		else if (status == BUY)
			drawBuy(g, point.x, point.y);
		else if (status == SELL)
			drawSell(g, point.x, point.y);
		g.setColor(orgColor);
	}
	
	private void drawDialog(Graphics g, int x, int y) {
		g.setColor(Color.white);
		g.fillRect(x + 150, y + 120, 300, 120);
		g.setColor(Color.black);
		g.drawString("What do you want?", x + 170, y + 150);
		g.drawString("Buy", x + 211, y + 200);
		g.drawString("Sell", x + 330, y + 200);
		if (cursor == 0)
			g.drawRect(x + 193, y + 195, 65, 30);
		else if (cursor == 1)
			g.drawRect(x + 317, y + 195, 65, 30);
	}
	
	private void drawBuy(Graphics g, int x, int y) {
		g.setColor(Color.white);
		g.fillRect(x, y, AOGame.WIDTH, AOGame.HEIGHT);
		g.setColor(Color.black);
		g.drawRect(x + 20, y + 30, 235, 425);
		g.drawLine(x + 20, y + 60, 235 + x + 20, y + 60);
		g.drawString("Gold: " + String.valueOf(player.getGold()), x + 10, y + 10);
		g.drawString("Item", x + 60, y + 35);
		g.drawString("Price", x + 185, y + 35);
		
		for (int i = topCursor, j = 0; j < ITEMS_PER_PAGE + 1; i++, j++) {
			if (i >= items.size()) break;
			AbstractItem item = items.get(i);
			if (item == null) break;
			g.drawImage(item.getImage(), x + 30, y + 80 + (j * 55));
			g.drawImage(item.getLabel(), x + 65, y + 80 + (j * 55));
			g.drawString(String.valueOf(item.getPrice()), x + 210, y + 87 + (j * 55));
			if (cursor == i) {
				g.drawImage(item.getDescription(), x + 290, y + 60);
				g.drawRect(x + 20, y + 70 + (j * 55), 235, 55);
			}
		}
	}
	
	private void drawSell(Graphics g, int x, int y) {
		g.setColor(Color.white);
		g.fillRect(x, y, AOGame.WIDTH, AOGame.HEIGHT);
		g.setColor(Color.black);
		g.drawRect(x + 20, y + 30, 275, 425);
		g.drawLine(x + 20, y + 60, 275 + x + 20, y + 60);
		g.drawString("Gold: " + String.valueOf(player.getGold()), x + 10, y + 10);
		g.drawString("Item          Price Amount", x + 60, y + 35);

		for (int i = topCursor, j = 0; j < ITEMS_PER_PAGE + 1; i++, j++) {
			if (i >= playerItems.size()) break;
			Item item = null;
			try {
				item = playerItems.get(i);
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
		}
	}
	
	@Override
	protected void init() {
		items = shopItems;
		status = DIALOG;
		page = CONSUMABLE;
		cursor = 0;
		topCursor = 0;
	}
}
