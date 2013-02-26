package com.zimu.ao.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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
		this.orgPrimaryAttack = 15;
		this.primaryAttack = orgPrimaryAttack;
		this.orgSecondaryAttack = 10;
		this.secondaryAttack = orgSecondaryAttack;
		this.orgPrimaryDefence = 5;
		this.primaryDefence = orgPrimaryDefence;
		this.orgArmorDefence = 2;
		this.armorDefence = orgArmorDefence;
		initEquipments();
	}
	
	private void initEquipments() {
		Equipment e = null;
		try {
			e = new NullEquipment();
		} catch (Exception exc) {}
		equipments.put(Equipment.ARMOR, e);
		equipments.put(Equipment.BOOT, e);
		equipments.put(Equipment.HELMET, e);
		equipments.put(Equipment.PRIMARYWEAPON, e);
		equipments.put(Equipment.SECONDARYWEAPON, e);
		equipments.put(Equipment.AMULET, e);
		equipments.put(Equipment.CHARM, e);
	}

}
