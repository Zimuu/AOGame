package com.zimu.ao.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.item.equipment.Equipment;
import com.zimu.ao.item.equipment.NullEquipment;

/**
 * 女主,尹凌星
 * @author zimu
 *
 */
public class LingXing extends AbstractChar {

	public LingXing() throws SlickException {
		this.image = new Image("resource/image/char/lingxing.jpg");
		this.name = "Yin LingXing";
		this.experience = 30;
		this.level = 2;
		this.currHealth = 100;
		this.orgHealth = 110;
		this.health = orgHealth;
		this.orgPrimaryAttack = 18;
		this.primaryAttack = orgPrimaryAttack;
		this.orgSecondaryAttack = 13;
		this.secondaryAttack = orgSecondaryAttack;
		this.orgPrimaryDefence = 6;
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
