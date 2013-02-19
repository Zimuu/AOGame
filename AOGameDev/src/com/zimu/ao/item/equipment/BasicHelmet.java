package com.zimu.ao.item.equipment;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.character.Char;

public class BasicHelmet extends Equipment {

	public BasicHelmet() throws SlickException {
		super("BasicHelmet", 50, 
				new Image("resource/image/item/basichelmet.gif"),
				new Image("resource/image/item/basichelmet_label.jpg"), 
				new Image("resource/image/item/basichelmet_desc.jpg"));
	}

	@Override
	public void equip(Char character) {
	}

}
