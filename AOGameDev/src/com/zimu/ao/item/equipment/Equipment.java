package com.zimu.ao.item.equipment;

import org.newdawn.slick.Image;

import com.zimu.ao.character.Char;
import com.zimu.ao.item.AbstractItem;


public abstract class Equipment extends AbstractItem {

	public Equipment(String name, int price, Image image, Image label,
			Image description) {
		super(name, price, image, label, description);
		this.type = TOOLS;
	}

	public abstract void equip(Char character);
	
}
