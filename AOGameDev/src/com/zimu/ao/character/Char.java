package com.zimu.ao.character;

import com.zimu.ao.character.foe.Foe;
import com.zimu.ao.effect.Effect;

/**
 * 角色接口,负责角色的动作(包括战斗动作和日常动作)
 * @author zimu
 *
 */
public interface Char {

	public int attack(Effect effect, Foe target);
	
	public int healHP(int hp);
	
	public int healMP(int mp);
	
	public int detoxicate();

}
