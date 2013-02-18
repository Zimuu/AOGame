package com.zimu.ao.item.consumable;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.character.Char;

public class SuperApple extends Consumable {
	
	private final int health = 35;
	private final int mana = 10;

	public SuperApple() throws SlickException {
		super("SupperApple", 10, 
				new Image("resource/image/item/superapple.gif"),
				new Image("resource/image/item/superapple_label.jpg"),
				new Image("resource/image/item/superapple_desc.jpg"));
	}

	@Override
	public int consume(Char character) {
		int res = character.healHP(health);
		res += character.healMP(mana);
		if (res == -2)
			return -1;
		return 1;
	}
	
}
