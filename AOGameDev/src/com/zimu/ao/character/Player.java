package com.zimu.ao.character;

import java.util.ArrayList;
import java.util.List;

import com.zimu.ao.board.ItemBoard;
import com.zimu.ao.item.AbstractItem;
import com.zimu.ao.item.Item;
import com.zimu.ao.item.consumable.Apple;
import com.zimu.ao.item.consumable.SuperApple;
import com.zimu.ao.item.equipment.BasicArmor;
import com.zimu.ao.item.equipment.BasicHelmet;
import com.zimu.ao.item.equipment.Equipment;
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
	private List<AbstractChar> characters;
	
	private List<Item> consumable;
	private List<Item> equipments;
	private List<Item> questItems;
	
	private Player() {
		consumable = new ArrayList<Item>();
		equipments = new ArrayList<Item>();
		questItems = new ArrayList<Item>();
		characters = new ArrayList<AbstractChar>();
		try {
			characters.add(new YueMing());
			characters.add(new LingXing());
			characters.get(0).equip(new BasicArmor());
			characters.get(0).equip(new BasicHelmet());
			characters.get(1).equip(new BasicHelmet());
			tester();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void tester() {
		try {
			//putItem(new BasicArmor());
			//putItem(new BasicHelmet());
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
	
	public int equip(Equipment equipment, AbstractChar character) {
		int index = 0;
		for (Item e : equipments)
			if (equipment.equals((Equipment)e.getItem())) break;
			else index++;
		return equip(index, character);
	}
	
	public int equip(int index, AbstractChar character) {
		Equipment e = (Equipment) equipments.get(index).getItem();
		Equipment returned_e = character.equip(e);
		if (returned_e.getEquipmentType() == Equipment.NULL)
			return 1;
		if (returned_e.equals(e))
			return 1;
		putItem(returned_e);
		removeItem(index, 1, ItemBoard.EQUIPMENT);
		return 1;
	}
	
	public int unequip(int equipmentType, AbstractChar character) {
		Equipment e = character.unequip(equipmentType);
		putItem(e);
		return -1;
	}
	
	public int putItem(AbstractItem item) {
		boolean added = false;
		int x = 0;
		int type = item.getType();
		
		for (x = 0; x < SIZE; x++) {
			Item it = null;
			try {
				if (type == AbstractItem.CONSUMABLE)
					it = consumable.get(x);
				else if (type == AbstractItem.EQUIPMENT)
					it = equipments.get(x);
				else if (type == AbstractItem.QUESTITEM)
					it = questItems.get(x);
			} catch (Exception e) {
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
			Item newItem = new Item(item);
			newItem.add(1);
			if (type == AbstractItem.CONSUMABLE)
				consumable.add(newItem);
			else if (type == AbstractItem.EQUIPMENT) 
				equipments.add(newItem);
			else if (type == AbstractItem.QUESTITEM)
				questItems.add(newItem);
		}
		
		return 1;
	}
	
	public void removeItem(int index, int count, int status) {
		Item item = null;
		if (status == ItemBoard.CONSUMABLE)
			item = consumable.get(index);
		else if (status == ItemBoard.EQUIPMENT)
			item = equipments.get(index);
		if (item == null) return;
		
		item.remove(count);
		if (item.getAmount() == 0) {
			if (status == ItemBoard.CONSUMABLE) {
				consumable.remove(index);
				format(status, index);
			} else if (status == ItemBoard.EQUIPMENT) {
				equipments.remove(index);
				format(status, index);
			}
		}
	}
	
	public void sellItem(int index, int count, int status) {
		Item item = null;
		if (status == ItemBoard.CONSUMABLE)
			item = consumable.get(index);
		else if (status == ItemBoard.EQUIPMENT)
			item = equipments.get(index);
		if (item == null) return ;
		int res = item.remove(count);
		gold += item.getItem().getPrice() * res / 2;
		if (item.getAmount() == ItemBoard.CONSUMABLE) {
			if (status == ItemBoard.CONSUMABLE) {
				consumable.remove(index);
				format(status, index);
			} else if (status == ItemBoard.EQUIPMENT) {
				equipments.remove(index);
				format(status, index);
			}
		}
	}
	
	private void format(int status, int index) {
		if (status == ItemBoard.CONSUMABLE) {
			for (int j = index + 1; j < SIZE; j++)
				consumable.set(j - 1, consumable.get(j));
			consumable.remove(consumable.size() - 1);
		} else if (status == ItemBoard.EQUIPMENT) {
			for (int j = index + 1; j < SIZE; j++)
				equipments.set(j - 1, equipments.get(j));
			equipments.remove(equipments.size() - 1);
		} else if (status == ItemBoard.QUEST) {
			for (int j = index + 1; j < SIZE; j++)
				questItems.set(j - 1, questItems.get(j));
			questItems.remove(questItems.size() - 1);
		}
	}
	
	public List<Equipment> getEquipments(int equipmentType) {
		List<Equipment> eqs = new ArrayList<Equipment>();
		for (Item i : equipments)
			if (((Equipment) i.getItem()).getEquipmentType() == equipmentType)
				eqs.add((Equipment) i.getItem());
		return eqs;
	}
	
	public List<Item> getEquipments() {
		return equipments;
	}
	
	public List<Item> getConsumable() {
		return consumable;
	}
	
	public List<Item> getQuestItems() {
		return questItems;
	}

	public List<AbstractChar> getCharacters() {
		return characters;
	}

	public int getGold() {
		return gold;
	}

	public void takeGold(int gold) {
		this.gold += gold;
	}
	
}
