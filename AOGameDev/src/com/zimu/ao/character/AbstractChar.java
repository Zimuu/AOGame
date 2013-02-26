package com.zimu.ao.character;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.buff.Buff;
import com.zimu.ao.buff.Poison;
import com.zimu.ao.item.equipment.Equipment;
import com.zimu.ao.item.equipment.NullEquipment;

/**
 * 角色
 * 角色的参数,装备,状态等等
 * 目前实现的方法为角色的加血和加魔
 * @author zimu
 *
 */
public abstract class AbstractChar {
	
	protected Image image;
	protected Image healthImage;
	protected Image primaryAttackImage;
	protected Image secondaryAttackImage;
	protected Image primaryDefenceImage;
	protected Image armorDefenceImage;

	protected String name;
	protected int experience;
	protected int level;
	
	protected int currHealth;
	protected int orgHealth;
	protected int health;
	
	protected int orgPrimaryAttack;
	protected int primaryAttack;
	protected int orgSecondaryAttack;
	protected int secondaryAttack;
	protected int orgPrimaryDefence;
	protected int primaryDefence;
	protected int orgArmorDefence;
	protected int armorDefence;
	
	protected List<Buff> buffs;
	
	protected Map<Integer, Equipment> equipments;
	
	public AbstractChar() throws SlickException {
		healthImage = new Image("resource/image/char/health.jpg");
		primaryAttackImage = new Image("resource/image/char/primaryAttack.jpg");
		secondaryAttackImage = new Image("resource/image/char/secondaryAttack.png");
		primaryDefenceImage = new Image("resource/image/char/primaryDefence.jpg");
		armorDefenceImage = new Image("resource/image/char/armorDefence.jpg");
		equipments = new HashMap<Integer, Equipment>();
	}
	
	public Equipment equip(Equipment e) {
		Equipment curr_e = equipments.get(e.getEquipmentType());
		if (curr_e == null)
			return null;
		
		health = health - curr_e.getHealth() + e.getHealth();
		primaryAttack = primaryAttack - curr_e.getPrimaryAttack() + e.getPrimaryAttack();
		secondaryAttack = secondaryAttack - curr_e.getSecondaryAttack() + e.getSecondaryAttack();
		primaryDefence = primaryDefence - curr_e.getPrimaryDefence() + e.getPrimaryDefence();
		armorDefence = armorDefence - curr_e.getArmorDefence() + e.getArmorDefence();
		
		equipments.put(e.getEquipmentType(), e);
		
		return curr_e;
	}
	
	public Equipment unequip(int equipmentType) {
		Equipment e = equipments.get(equipmentType);
		
		health -= e.getHealth();
		primaryAttack -= e.getPrimaryAttack();
		secondaryAttack -= e.getSecondaryAttack();
		primaryDefence -= e.getPrimaryDefence();
		armorDefence -= e.getArmorDefence();
		
		try {
			equipments.put(equipmentType, new NullEquipment());
		} catch (SlickException e1) { }
		
		return e;
	}
	
	public int healHP(int hp) {
		if (currHealth == health)
			return -1;
		currHealth += hp;
		if (currHealth >= health)
			currHealth = health;
		return 1;
	}
	
	public int detoxicate() {
		boolean poisoned = false;
		for (int i = 0; i < buffs.size(); i++) {
			poisoned = true;
		}
		if (!poisoned) {
			buffs.add(new Poison());
			return -1;
		}
		return 1;
	}

	public String getName() {
		return name;
	}

	public int getExperience() {
		return experience;
	}

	public int getLevel() {
		return level;
	}
	
	public int getCurrHealth() {
		return currHealth;
	}
	
	public int getOrgHealth() {
		return orgHealth;
	}

	public int getHealth() {
		return health;
	}

	public Image getPrimaryAttackImage() {
		return primaryAttackImage;
	}

	public Image getSecondaryAttackImage() {
		return secondaryAttackImage;
	}

	public Image getPrimaryDefenceImage() {
		return primaryDefenceImage;
	}

	public Image getArmorDefenceImage() {
		return armorDefenceImage;
	}

	public int getOrgPrimaryAttack() {
		return orgPrimaryAttack;
	}

	public int getPrimaryAttack() {
		return primaryAttack;
	}

	public int getOrgSecondaryAttack() {
		return orgSecondaryAttack;
	}

	public int getSecondaryAttack() {
		return secondaryAttack;
	}

	public int getOrgPrimaryDefence() {
		return orgPrimaryDefence;
	}

	public int getPrimaryDefence() {
		return primaryDefence;
	}

	public int getOrgArmorDefence() {
		return orgArmorDefence;
	}

	public int getArmorDefence() {
		return armorDefence;
	}

	public List<Buff> getBuffs() {
		return buffs;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Image getHealthImage() {
		return healthImage;
	}
	
	public Map<Integer, Equipment> getEquipments() {
		return equipments;
	}
	
}
