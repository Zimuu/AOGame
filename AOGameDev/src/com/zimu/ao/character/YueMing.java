package com.zimu.ao.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.enums.EquipmentType;
import com.zimu.ao.item.equipment.Equipment;
import com.zimu.ao.item.equipment.NullEquipment;

/**
 * 男主,陆月铭
 * @author zimu
 *
 */
public class YueMing extends AbstractChar {

	public YueMing() throws SlickException {
		this.image = new Image("resource/image/char/yueming.jpg");
		this.name = "Lu YueMing";
		this.experience = 50;
		this.level = 1;
		this.currHealth = 90;
		this.orgHealth = 100;
		this.health = orgHealth;
		this.orgAttack = 15;
		this.attack = orgAttack;
		this.orgDefence = 5;
		this.defence = orgDefence;
		initEquipments();
	}
	
	private void initEquipments() {
		Equipment e = new NullEquipment();
		equipments.put(EquipmentType.ARMOR, e);
		equipments.put(EquipmentType.HELMET, e);
		equipments.put(EquipmentType.PRIMARYWEAPON, e);
		equipments.put(EquipmentType.SECONDARYWEAPON, e);
		equipments.put(EquipmentType.AMULET, e);
		equipments.put(EquipmentType.CHARM, e);
	}

}
