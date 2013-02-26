package com.zimu.ao.item.equipment;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.character.AbstractChar;

public class BasicHelmet extends Equipment {

	public BasicHelmet() throws SlickException {
		super("BasicHelmet", 50, 
				new Image("resource/image/item/basichelmet.gif"),
				new Image("resource/image/item/basichelmet_label.jpg"), 
				new Image("resource/image/item/basichelmet_desc.jpg"));
		this.equipmentType = HELMET;
		this.armorDefence = 2;
		this.primaryAttack = 2;
		this.health = 5;
	}

	@Override
	public void equip(AbstractChar character) {
	}

}
