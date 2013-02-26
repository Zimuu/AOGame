package com.zimu.ao.item.equipment;

import org.newdawn.slick.Image;

import com.zimu.ao.character.AbstractChar;
import com.zimu.ao.item.AbstractItem;


public abstract class Equipment extends AbstractItem {
	//ARMOR, BOOT, AMULET, CHARM, PRIMARYWEAPON, SECONDARYWEAPON, HELMET, NULL
	public static final int HELMET = 0;
	public static final int AMULET = 1;
	public static final int PRIMARYWEAPON = 2;
	public static final int ARMOR = 3;
	public static final int SECONDARYWEAPON = 4;
	public static final int CHARM = 5;
	public static final int BOOT = 6;
	public static final int NULL = 7;
	protected int equipmentType;
	
	protected int health;
	protected int armorDefence;
	protected int primaryDefence;
	protected int primaryAttack;
	protected int secondaryAttack;
	
	public Equipment(String name, int price, Image image, Image label,
			Image description) {
		super(name, price, image, label, description);
		this.type = EQUIPMENT;
	}
	
	public int getHealth() {
		return health;
	}

	public abstract void equip(AbstractChar character);
	
	public int getEquipmentType() {
		return equipmentType;
	}
	
	public int getArmorDefence() {
		return armorDefence;
	}

	public int getPrimaryDefence() {
		return primaryDefence;
	}

	public int getPrimaryAttack() {
		return primaryAttack;
	}

	public int getSecondaryAttack() {
		return secondaryAttack;
	}
	
}
