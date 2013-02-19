package com.zimu.ao.item;

import org.newdawn.slick.Image;

/**
 * 物品类
 * 一个物品包括图标,名称,介绍,以及价格
 * @author zimu
 *
 */
public abstract class AbstractItem {

	public static final int CONSUMABLE = 0;
	public static final int TOOLS = 1;
	public static final int QUESTITEM = 2;
	
	protected Image image;
	protected int price;
	protected String name;
	protected Image label;
	protected Image description;
	protected int type;
	
	public AbstractItem(String name, int price, Image image, Image label, Image description) {
		this.image = image;
		this.name = name;
		this.price = price;
		this.label = label;
		this.description = description;
	} 
	
	public int getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Image getLabel() {
		return label;
	}

	public Image getDescription() {
		return description;
	}
	
	public int getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Item))
			return false;
		AbstractItem item = (AbstractItem) obj;
		return item.name.equals(this.name);
	}
	
}
