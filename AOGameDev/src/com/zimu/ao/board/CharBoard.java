package com.zimu.ao.board;

import java.awt.Point;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.zimu.ao.character.AbstractChar;

public class CharBoard {

	private AbstractChar[] characters;
	private int curr_char;
	
	public CharBoard() {

	}
	
	public void switchChar() {
		curr_char++;
		if (curr_char >= characters.length) curr_char = 0;
	}
	
	public void setChar(AbstractChar[] character) {
		int length = 0;
		for (; length < character.length; length++)
			if (character[length] == null) break;
		this.characters = Arrays.copyOf(character, length);
	}
	
	public void render(Graphics g, Point point) {
		Color orgColor = g.getColor();
		g.setColor(Color.white);
		g.fillRect(point.x + 20, point.y + 20, 600, 400);
		g.setColor(Color.black);
		for (int i = 0; i < characters.length; i++) {
			if (curr_char == i) g.setColor(Color.black);
			else g.setColor(Color.gray);
			g.drawString(characters[i].getName(), point.x + 30, point.y + 30 + (i * 32));
		}
		g.setColor(Color.black);
		
		int baseX = point.x + 150;
		int baseY = point.y + 50;
		AbstractChar c = characters[curr_char];
		g.drawImage(c.getImage(), baseX, baseY);
		g.drawString("Name    " + c.getName(), baseX + 250, baseY + 10);
		g.drawString("Level   " + c.getLevel(), baseX + 250, baseY + 40);
		g.drawString("Exp     " + c.getExperience(), baseX + 250, baseY + 70);

		g.drawImage(c.getHealthImage(), baseX, baseY + 180);
		g.drawString("Health   " + c.getCurrHealth() + "/" + c.getHealth(), baseX + 50, baseY + 185);
		int health = c.getHealth() - c.getOrgHealth();
		if (health < 0) {
			g.setColor(Color.red);
			g.drawString(" - " + Math.abs(health), baseX + 200, baseY + 185);
		} else if (health > 0) {
			g.setColor(new Color(0, 153, 51));
			g.drawString(" + " + health, baseX + 200, baseY + 185);			
		}
		g.setColor(Color.black);
		

		g.drawImage(c.getAttackImage(), baseX, baseY + 230);
		g.drawString("Attack   " + c.getOrgAttack(), baseX + 50, baseY + 235);
		int attack = c.getAttack() -  c.getOrgAttack();
		if (attack < 0) {
			g.setColor(Color.red);
			g.drawString(" - " + Math.abs(attack), baseX + 155, baseY + 235);
		} else if (attack > 0) {
			g.setColor(new Color(0, 153, 51));
			g.drawString(" + " + attack, baseX + 155, baseY + 235);			
		}
		g.setColor(Color.black);
		
		g.drawImage(c.getDefenceImage(), baseX, baseY + 280);
		g.drawString("Defence  " + c.getOrgDefence(), baseX + 50, baseY + 285);
		int defence = c.getDefence() - c.getOrgDefence();
		if (defence < 0) {
			g.setColor(Color.red);
			g.drawString(" - " + Math.abs(defence), baseX + 155, baseY + 285);
		} else if (defence > 0) {
			g.setColor(new Color(0, 153, 51));
			g.drawString(" + " + defence, baseX + 155, baseY + 285);			
		}
		
		g.setColor(orgColor);
	}

}
