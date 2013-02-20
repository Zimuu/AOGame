package com.zimu.ao.item.consumable;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.character.AbstractChar;

public class Apple extends Consumable {
	
	private final int health = 20;

	public Apple() throws SlickException {
		super("Apple", 4, 
				new Image("resource/image/item/apple.gif"),
				new Image("resource/image/item/apple_label.jpg"),
				new Image("resource/image/item/apple_desc.jpg"));
	}

	@Override
	public int consume(AbstractChar character) {
		return character.healHP(health);
	}
	
}
