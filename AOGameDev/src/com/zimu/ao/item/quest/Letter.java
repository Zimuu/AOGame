package com.zimu.ao.item.quest;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Letter extends QuestItem {

	public Letter() throws SlickException {
		super("Letter", 0, 
				new Image("resource/image/item/letter.png"),
				new Image("resource/image/item/letter_label.jpg"), 
				new Image("resource/image/item/letter_desc.jpg"));
	}

}
