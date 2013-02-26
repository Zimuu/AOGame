package com.zimu.ao.board;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.zimu.ao.character.AbstractChar;
import com.zimu.ao.character.Player;
import com.zimu.ao.enums.Menu;
import com.zimu.ao.state.AOGame;

public class CharBoard extends RenderableScreen {

	private Color green;
	
	public CharBoard(Player player) {
		this.player = player;
		green = new Color(0, 153, 51);
	}
	
	public boolean actionPerformed(Input input) {
		if (!draw) return false;
		if (input.isKeyPressed(Input.KEY_TAB)) {
			if (cursor < player.getCharacters().size() - 1) cursor++;
			else cursor = 0;
		} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			cursor = 0;
			draw = false;
			setChanged();
			notifyObservers(Menu.valueOf(0));
		}
		return true;
	}
	
	public void render(Graphics g, Point point) {
		Color orgColor = g.getColor();
		int x = point.x;
		int y = point.y;
		
		g.setColor(Color.white);
		g.fillRect(x, y, AOGame.WIDTH, AOGame.HEIGHT);
		g.setColor(Color.black);
		for (int i = 0; i < player.getCharacters().size(); i++) {
			if (player.getCharacters().get(i) == null) break;
			if (cursor == i) g.setColor(Color.black);
			else g.setColor(Color.gray);
			g.drawString(player.getCharacters().get(i).getName(), x + 20 + (i * 150), y + 15);
		}
		g.setColor(Color.black);
		
		AbstractChar c = player.getCharacters().get(cursor);
		g.drawImage(c.getImage(), x + 50, y + 50);
		g.drawString("Name    " + c.getName(), x + 300, y + 60);
		g.drawString("Level   " + c.getLevel(), x + 300, y + 100);
		g.drawString("Exp     " + c.getExperience(), x + 300, y + 140);

		g.drawImage(c.getHealthImage(), x + 50, y + 230);
		g.drawImage(c.getPrimaryAttackImage(), x + 50, y + 280);
		g.drawImage(c.getSecondaryAttackImage(), x + 350, y + 280);
		g.drawImage(c.getPrimaryDefenceImage(), x + 50, y + 330);
		g.drawImage(c.getArmorDefenceImage(), x + 350, y + 330);
		
		
		g.drawString("Health   " + c.getCurrHealth() + "/", x + 100, y + 235);
		g.drawString("Primary Attack", x + 100, y + 285);
		g.drawString("Secondary Attack", x + 390, y + 285);
		g.drawString("Primary Defence  ", x + 100, y + 335);
		g.drawString("Armor Defence  ", x + 390, y + 335);
		
		int healthDiff = c.getHealth() - c.getOrgHealth();
		int primaryAttackDiff = c.getPrimaryAttack() -  c.getOrgPrimaryAttack();
		int secondaryAttackDiff = c.getSecondaryAttack() -  c.getOrgSecondaryAttack();
		int primaryDefenceDiff = c.getPrimaryDefence() - c.getOrgPrimaryDefence();
		int armorDefenceDiff = c.getArmorDefence() - c.getOrgArmorDefence();
		
		if (healthDiff < 0) g.setColor(Color.red);
		else if (healthDiff > 0) g.setColor(green);
		else g.setColor(Color.black);
		g.drawString(String.valueOf(c.getHealth()), x + 225, y + 235);
		if (primaryAttackDiff < 0) g.setColor(Color.red);
		else if (primaryAttackDiff > 0) g.setColor(green);
		else g.setColor(Color.black);
		g.drawString(String.valueOf(c.getPrimaryAttack()), x + 265, y + 285);
		if (secondaryAttackDiff < 0) g.setColor(Color.red);
		else if (secondaryAttackDiff > 0) g.setColor(green);
		else g.setColor(Color.black);
		g.drawString(String.valueOf(c.getSecondaryAttack()), x + 555, y + 285);
		if (primaryDefenceDiff < 0) g.setColor(Color.red);
		else if (primaryDefenceDiff > 0) g.setColor(green);
		g.drawString(String.valueOf(c.getPrimaryDefence()), x + 265, y + 335);
		if (armorDefenceDiff < 0) g.setColor(Color.red);
		else if (armorDefenceDiff > 0) g.setColor(green);
		g.drawString(String.valueOf(c.getArmorDefence()), x + 555, y + 335);
		
		g.setColor(orgColor);
	}
	
	@Override
	protected void init() {
		cursor = 0;
	}

}
