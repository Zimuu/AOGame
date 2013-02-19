package com.zimu.ao.item.quest;

import org.newdawn.slick.Image;

import com.zimu.ao.item.AbstractItem;

public abstract class QuestItem extends AbstractItem {

	public QuestItem(String name, int price, Image image, Image label,
			Image description) {
		super(name, price, image, label, description);
		this.type = QUESTITEM;
	}


}
