package com.zimu.ao.item;

/**
 * 用于背包/商店管理的物品类
 * 一个物品(item)和物品的数量
 * @author zimu
 *
 */
public class Item {
	
	public static final int MAX = 50;
	
	private int amount;
	private AbstractItem item;
	
	public Item(AbstractItem item) {
		this.item = item;
	}
	
	public int add(int n) {
		if (amount + n >= MAX) {
			int temp = MAX - amount;
			amount = MAX;
			return temp;
		} else {
			amount +=n;
			return n;
		}
	}
	
	public int remove(int n) {
		if (amount - n < 0) {
			int temp = amount;
			amount = 0;
			return temp;
		} else {
			amount -= n;
			return n;
		}
	}
	
	public int removeAll() {
		int temp = amount;
		amount = 0;
		return temp;	
	}
	
	public int getAmount() {
		return amount;
	}
	
	public AbstractItem getItem() {
		return item;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractItem) {
			AbstractItem i = (AbstractItem) obj;
			return i.getName().equals(this.item.getName());
		} else if (obj instanceof Item) {
			Item i = (Item) obj;
			return i.item.getName().equals(this.item.getName());
		} else
			return false;
	}
}
