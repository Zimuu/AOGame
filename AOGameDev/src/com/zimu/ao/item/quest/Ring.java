package com.zimu.ao.item.quest;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Ring extends QuestItem {

	public Ring() throws SlickException {
		super("Ring", 0, 
				new Image("resource/image/item/ring.png"),
				new Image("resource/image/item/ring_label.jpg"), 
				new Image("resource/image/item/ring_desc.jpg"));
	}

}