package com.zimu.ao.item.equipment;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.character.AbstractChar;
import com.zimu.ao.enums.EquipmentType;

public class BasicArmor extends Equipment {

	public BasicArmor() throws SlickException {
		super("BasicArmor", 150, 
				new Image("resource/image/item/basicarmor.gif"),
				new Image("resource/image/item/basicarmor_label.jpg"), 
				new Image("resource/image/item/basicarmor_desc.jpg"));
		this.equipmentType = EquipmentType.ARMOR;
		this.defence = 5;
		this.attack = 0;
		this.health = 10;
	}

	@Override
	public void equip(AbstractChar character) {
	}

}
