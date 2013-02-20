package com.zimu.ao.item.equipment;

import org.newdawn.slick.Image;

import com.zimu.ao.character.AbstractChar;
import com.zimu.ao.enums.EquipmentType;
import com.zimu.ao.item.AbstractItem;


public abstract class Equipment extends AbstractItem {
	
	protected EquipmentType equipmentType;
	
	protected int defence;
	protected int health;
	protected int attack;

	public Equipment(String name, int price, Image image, Image label,
			Image description) {
		super(name, price, image, label, description);
		this.type = TOOLS;
	}

	public abstract void equip(AbstractChar character);
	
	public EquipmentType getEquipmentType() {
		return equipmentType;
	}
	
	public int getDefence() {
		return defence;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getAttack() {
		return attack;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Equipment))
			return false;
		Equipment e = (Equipment) obj;
		return e.name.equals(this.name);
	}
	
}
