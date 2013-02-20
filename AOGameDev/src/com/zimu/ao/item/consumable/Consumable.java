package com.zimu.ao.item.consumable;

import org.newdawn.slick.Image;

import com.zimu.ao.character.AbstractChar;
import com.zimu.ao.item.AbstractItem;

/**
 * 可食用的物品
 * 抽象方法食用
 * @author zimu
 *
 */
public abstract class Consumable extends AbstractItem {

	public Consumable(String name, int price, Image image, Image label, Image description) {
		super(name, price, image, label, description);
		this.type = CONSUMABLE;
	}
	
	public abstract int consume(AbstractChar character);

}
