package com.zimu.ao.item.equipment;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.character.Char;

public class BasicArmor extends Equipment {

	public BasicArmor() throws SlickException {
		super("BasicArmor", 150, 
				new Image("resource/image/item/basicarmor.gif"),
				new Image("resource/image/item/basicarmor_label.jpg"), 
				new Image("resource/image/item/basicarmor_desc.jpg"));
	}

	@Override
	public void equip(Char character) {
	}

}
