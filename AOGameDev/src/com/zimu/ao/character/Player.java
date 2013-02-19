package com.zimu.ao.character;

import com.zimu.ao.enums.Status;
import com.zimu.ao.item.AbstractItem;
import com.zimu.ao.item.Item;
import com.zimu.ao.item.consumable.Apple;
import com.zimu.ao.item.consumable.SuperApple;
import com.zimu.ao.item.equipment.BasicArmor;
import com.zimu.ao.item.equipment.BasicHelmet;
import com.zimu.ao.item.quest.Letter;
import com.zimu.ao.item.quest.Ring;

/**
 * 玩家信息(总体)
 * 包含玩家的包裹,金钱,等等信息.
 * @author zimu
 *
 */
public class Player {
	
	private final int SIZE = 50;

	private static Player instance;
	private int gold = 1000;
	
	private Item[] consumable;
	private Item[] tools;
	private Item[] questItems;
	
	private Player() {
		consumable = new Item[SIZE];
		tools = new Item[SIZE];
		questItems = new Item[20];
	}
	
	public void tester() {
		try {
			for (int i = 0; i < 150; i++) {
				putItem(new BasicArmor());
				putItem(new BasicHelmet());
			}
			putItem(new Letter());
			putItem(new Ring());
			putItem(new Apple());
			putItem(new SuperApple());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static Player getInstance() {
		if (instance == null)
			instance = new Player();
		return instance;
	}
	
	public int putItem(AbstractItem item) {
		boolean added = false;
		int x = 0;
		int type = item.getType();
		
		for (x = 0; x < SIZE; x++) {
			Item it = null;
			try {
				if (type == AbstractItem.CONSUMABLE)
					it = consumable[x];
				else if (type == AbstractItem.TOOLS)
					it = tools[x];
				else if (type == AbstractItem.QUESTITEM)
					it = questItems[x];
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
			if (it == null) break;
			else if (it.equals(item) && it.getAmount() < Item.MAX) {
				added = true;
				it.add(1);
				break;
			}
		}
		if (!added) {
			if (x >= SIZE)
				return -1;
			if (type == AbstractItem.CONSUMABLE) {
				consumable[x] = new Item(item);
				consumable[x].add(1);
			} else if (type == AbstractItem.TOOLS) {
				tools[x] = new Item(item);
				tools[x].add(1);
			} else if (type == AbstractItem.QUESTITEM) {
				questItems[x] = new Item(item);
				questItems[x].add(1);
			}
		}
		
		return 1;
	}
	
	public Item[] sellItem(int index, int count, Status status) {
		Item item = null;
		if (status == Status.SHOP_CONSUMABLE)
			item = consumable[index];
		else if (status == Status.SHOP_TOOLS)
			item = tools[index];
		if (item == null) return null;
		int res = item.remove(count);
		gold += item.getItem().getPrice() * res / 2;
		if (item.getAmount() == 0) {
			if (status == Status.SHOP_CONSUMABLE) {
				consumable[index] = null;
				format(status);
			} else if (status == Status.SHOP_TOOLS) {
				tools[index] = null;
				format(status);
			}
		}
		if (status == Status.SHOP_CONSUMABLE) 
			return consumable;
		else if (status == Status.SHOP_TOOLS)
			return tools;
		return null;
	}
	
	private void format(Status status) {
		if (status == Status.BAG_CONSUMABLE || status == Status.SHOP_CONSUMABLE) {
			for (int i = 0; i < SIZE; i++)
				if (consumable[i] == null)
					for (int j = i + 1; j < SIZE; j++)
						consumable[j - 1] = consumable[j]; 
		} else if (status == Status.BAG_TOOLS|| status == Status.SHOP_TOOLS) {
			for (int i = 0; i < SIZE; i++)
				if (tools[i] == null)
					for (int j = i + 1; j < SIZE; j++)
						tools[j - 1] = tools[j]; 
		} else if (status == Status.BAG_CONSUMABLE) {
			for (int i = 0; i < SIZE; i++)
				if (questItems[i] == null)
					for (int j = i + 1; j < SIZE; j++)
						questItems[j - 1] = questItems[j]; 
		}
	}
	
	public Item[] getItems(Status status) {
		if (status == Status.BAG_CONSUMABLE || status == Status.SHOP_CONSUMABLE)
			return consumable;
		else if (status == Status.BAG_TOOLS|| status == Status.SHOP_TOOLS)
			return tools;
		else if (status == Status.BAG_QUESTITEMS) 
			return questItems;
		return null;
	}

	public int getGold() {
		return gold;
	}

	public void takeGold(int gold) {
		this.gold += gold;
	}
	
}
