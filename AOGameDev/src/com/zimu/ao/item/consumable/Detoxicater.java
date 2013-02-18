package com.zimu.ao.item.consumable;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.character.Char;

public class Detoxicater extends Consumable {
	
	private final int health = 20;

	public Detoxicater() throws SlickException {
		super("detoxicater", 10, 
				new Image("resource/image/item/detoxicater.gif"),
				new Image("resource/image/item/detoxicater_label.gif"),
				new Image("resource/image/item/detoxicater_desc.gif"));
	}

	@Override
	public int consume(Char character) {
		return character.healHP(health);
	}
	
}
