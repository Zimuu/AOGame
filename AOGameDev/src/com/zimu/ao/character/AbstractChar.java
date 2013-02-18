package com.zimu.ao.character;

import java.util.List;

import com.zimu.ao.buff.Buff;
import com.zimu.ao.buff.Poison;
import com.zimu.ao.item.equipment.Equipment;

/**
 * 角色
 * 角色的参数,装备,状态等等
 * 目前实现的方法为角色的加血和加魔
 * @author zimu
 *
 */
public abstract class AbstractChar implements Char {

	protected String name;
	protected int experience;
	protected int level;
	protected int health;
	protected int maxHealth;
	protected int attack;
	protected int defence;
	protected int mana;
	protected int maxMana;
	protected List<Buff> buffs;
	
	protected Equipment armor;
	protected Equipment charm;
	protected Equipment primaryRing;
	protected Equipment secondaryWeaponRing;
	protected Equipment primaryWeapon;
	protected Equipment secondaryWeapon;
	protected Equipment helmet;
	protected Equipment boot;
	protected Equipment belt;
	
	@Override
	public int healHP(int hp) {
		if (health == maxHealth)
			return -1;
		health += hp;
		if (health >= maxHealth)
			health = maxHealth;
		return 1;
	}
	
	@Override
	public int healMP(int mp) {
		if (mana == maxMana)
			return -1;
		mana += mp;
		if (mana >= maxMana)
			mana = maxMana;
		return 1;
	}
	
	@Override
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
}
