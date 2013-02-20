package com.zimu.ao.character;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.zimu.ao.buff.Buff;
import com.zimu.ao.buff.Poison;
import com.zimu.ao.enums.EquipmentType;
import com.zimu.ao.item.equipment.Equipment;

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
	protected Image attackImage;
	protected Image defenceImage;

	protected String name;
	protected int experience;
	protected int level;
	
	protected int currHealth;
	protected int orgHealth;
	protected int health;
	
	protected int orgAttack;
	protected int attack;
	
	protected int orgDefence;
	protected int defence;
	protected List<Buff> buffs;
	
	protected Map<EquipmentType, Equipment> equipments;
	
	public AbstractChar() throws SlickException {
		healthImage = new Image("resource/image/char/health.jpg");
		attackImage = new Image("resource/image/char/attack.jpg");
		defenceImage = new Image("resource/image/char/defence.jpg");
		equipments = new HashMap<EquipmentType, Equipment>();
	}
	
	public Equipment equip(Equipment e) {
		Equipment curr_e = equipments.get(e.getEquipmentType());
		if (curr_e == null)
			return null;
		health -= curr_e.getHealth();
		attack -= curr_e.getAttack();
		defence -= curr_e.getDefence();
		
		health += e.getHealth();
		attack += e.getAttack();
		defence += e.getDefence();
		return curr_e;
	}
	
	public Equipment unequip(Equipment e) {
		Equipment curr_e = equipments.get(e.getEquipmentType());
		health -= curr_e.getHealth();
		attack -= curr_e.getAttack();
		defence -= curr_e.getDefence();		
		return curr_e;
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
	
	public int getOrgDefence() {
		return orgDefence;
	}

	public int getDefence() {
		return defence;
	}

	public int getOrgAttack() {
		return orgAttack;
	}

	public int getAttack() {
		return attack;
	}

	public List<Buff> getBuffs() {
		return buffs;
	}
	
	public Map<EquipmentType, Equipment> getEquipments() {
		return equipments;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Image getHealthImage() {
		return healthImage;
	}
	
	public Image getAttackImage() {
		return attackImage;
	}
	
	public Image getDefenceImage() {
		return defenceImage;
	}
	
}
