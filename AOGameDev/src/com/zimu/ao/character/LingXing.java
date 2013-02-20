package com.zimu.ao.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.enums.EquipmentType;
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
		this.orgAttack = 18;
		this.attack = orgAttack;
		this.orgDefence = 8;
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
