package com.zimu.ao.item.equipment;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.character.AbstractChar;

public class NullEquipment extends Equipment {

	public NullEquipment() throws SlickException {
		super("NullEquipment", 50, new Image("resource/image/item/nullitem.jpg"), null, null);
		this.equipmentType = NULL;
	}

	@Override
	public void equip(AbstractChar character) {}

}
