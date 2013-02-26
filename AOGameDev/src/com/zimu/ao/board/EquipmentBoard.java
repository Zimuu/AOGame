package com.zimu.ao.board;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import com.zimu.ao.character.AbstractChar;
import com.zimu.ao.character.Player;
import com.zimu.ao.enums.Menu;
import com.zimu.ao.item.equipment.Equipment;
import com.zimu.ao.state.AOGame;

public class EquipmentBoard extends RenderableScreen {
	
	private final int NORMAL = 0;
	private final int SLOT = 1;
	private final int ITEM = 2;
	private final int ITEMS_PER_PAGE = 5;
	
	private Image body;
	private Map<Integer, Point> equipmentSlots;
	private Color green;
	
	private List<Equipment> equipments;
	
	private int state;
	private int stateCursor;
	private int charCursor;
	
	public EquipmentBoard(Player player) {
		this.player = player;
		green = new Color(0, 153, 51);
		try {
			body = new Image("resource/image/char/body.jpg");
		} catch (Exception e) {}
		equipmentSlots = new HashMap<Integer, Point>();
		initSlots();
	}
	
	private void initSlots() {
		equipmentSlots.put(Equipment.ARMOR, new Point(283, 200));
		equipmentSlots.put(Equipment.BOOT, new Point(235, 390));
		equipmentSlots.put(Equipment.AMULET, new Point(335, 135));
		equipmentSlots.put(Equipment.CHARM, new Point(365, 260));
		equipmentSlots.put(Equipment.PRIMARYWEAPON, new Point(195, 185));
		equipmentSlots.put(Equipment.SECONDARYWEAPON, new Point(385, 185));
		equipmentSlots.put(Equipment.HELMET, new Point(280, 80));
	}

	@Override
	public boolean actionPerformed(Input input) {
		if (!draw) return false;
		switch (state) {
			case NORMAL:
				if (input.isKeyPressed(Input.KEY_TAB)) {
					if (charCursor < player.getCharacters().size() - 1) charCursor++;
					else charCursor = 0;
					cursor = -1;
				} else if (input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_UP)) {
					if (cursor > Equipment.HELMET) cursor--;
				} else if (input.isKeyPressed(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_DOWN)) {
					if (cursor < Equipment.NULL - 1) cursor++;
				} else if (input.isKeyPressed(Input.KEY_SPACE)) {
					if (cursor != -1) {
						state = SLOT;
						stateCursor = 0;
					}
				} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
					draw = false;
					setChanged();
					notifyObservers(Menu.valueOf(0));
				}
				break;
			case SLOT:
				if (input.isKeyPressed(Input.KEY_UP)) {
					stateCursor = 0;
				} else if (input.isKeyPressed(Input.KEY_DOWN)) {
					stateCursor = 1;
				} else if (input.isKeyPressed(Input.KEY_SPACE)) {
					AbstractChar c = player.getCharacters().get(charCursor);
					if (stateCursor == 0) {
						state = ITEM;
						equipments = player.getEquipments(cursor);
					} else if (stateCursor == 1) {
						player.unequip(cursor, c);
						state = NORMAL;
						stateCursor = 0;
					}
				} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
					state = NORMAL;
					stateCursor = 0;
				}
				break;
			case ITEM:
				if (input.isKeyPressed(Input.KEY_UP)) {
					if (stateCursor >= 1) stateCursor--;
				} else if (input.isKeyPressed(Input.KEY_DOWN)) {
					if (stateCursor < equipments.size() - 1) stateCursor++;
				} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
					stateCursor = 0;
					state = NORMAL;
				} else if (input.isKeyPressed(Input.KEY_SPACE)) {
					if (equipments.size() == 0) return true;
					player.equip(equipments.get(stateCursor), player.getCharacters().get(charCursor));
					equipments = player.getEquipments(cursor);
					stateCursor = 0;
					state = NORMAL;
				}
				if (stateCursor <= ITEMS_PER_PAGE) topCursor = 0;
				else topCursor = stateCursor - ITEMS_PER_PAGE;
		}
		return true;
	}
	
	@Override
	public void render(Graphics g, Point point) {
		Color orgColor = g.getColor();
		int x = point.x;
		int y = point.y;

		g.setColor(Color.white);
		g.fillRect(x, y, AOGame.WIDTH, AOGame.HEIGHT);
		drawStatus(g, x, y);
		drawEquipments(g, x, y);
		if (state == ITEM)
			drawItems(g, x, y);
		
		g.setColor(orgColor);
	}
	
	private void drawStatus(Graphics g, int x, int y) {
		g.setColor(Color.black);
		for (int i = 0; i < player.getCharacters().size(); i++) {
			if (charCursor == i) g.setColor(Color.black);
			else g.setColor(Color.gray);
			g.drawString(player.getCharacters().get(i).getName(), x + 20 + (i * 150), y + 15);
		}
		g.setColor(Color.black);
		AbstractChar c = player.getCharacters().get(charCursor);

		g.drawImage(c.getHealthImage(), x + 10, y + 50);
		g.drawImage(c.getPrimaryAttackImage(), x + 10, y + 100);
		g.drawImage(c.getSecondaryAttackImage(), x + 10, y + 150);
		g.drawImage(c.getPrimaryDefenceImage(), x + 10, y + 200);
		g.drawImage(c.getArmorDefenceImage(), x + 10, y + 250);

		g.drawString(c.getCurrHealth() + "/", x + 50, y + 57);
		
		int healthDiff = c.getHealth() - c.getOrgHealth();
		int primaryAttackDiff = c.getPrimaryAttack() -c.getOrgPrimaryAttack();
		int secondaryAttackDiff = c.getSecondaryAttack() - c.getOrgSecondaryAttack();
		int primaryDefenceDiff = c.getPrimaryDefence() - c.getOrgPrimaryDefence();
		int armorDefenceDiff = c.getArmorDefence() - c.getOrgArmorDefence();
		
		if (healthDiff < 0) g.setColor(Color.red);
		else if (healthDiff > 0) g.setColor(green);
		else g.setColor(Color.black);
		g.drawString(String.valueOf(c.getHealth()), x + 90, y + 57);
		if (primaryAttackDiff < 0) g.setColor(Color.red);
		else if (primaryAttackDiff > 0) g.setColor(green);
		else g.setColor(Color.black);
		g.drawString(String.valueOf(c.getPrimaryAttack()), x + 55, y + 107);
		if (secondaryAttackDiff < 0) g.setColor(Color.red);
		else if (secondaryAttackDiff > 0) g.setColor(green);
		else g.setColor(Color.black);
		g.drawString(String.valueOf(c.getSecondaryAttack()), x + 55, y + 157);
		if (primaryDefenceDiff < 0) g.setColor(Color.red);
		else if (primaryDefenceDiff > 0) g.setColor(green);
		else g.setColor(Color.black);
		g.drawString(String.valueOf(c.getPrimaryDefence()), x + 55, y + 207);
		if (armorDefenceDiff < 0) g.setColor(Color.red);
		else if (armorDefenceDiff > 0) g.setColor(green);
		else g.setColor(Color.black);
		g.drawString(String.valueOf(c.getArmorDefence()), x + 55, y + 257);
	}
	
	private void drawEquipments(Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.drawImage(body, x + 180, y + 50);
		
		
		Map<Integer, Equipment> equipments = player.getCharacters().get(charCursor).getEquipments();
		Set<Integer> keys = equipments.keySet();
		for (Integer type : keys) {
			Point point = equipmentSlots.get(type);
			g.drawImage(equipments.get(type).getImage(), x + point.x, y + point.y);
			if (cursor == type) g.setColor(green);
			else g.setColor(Color.black);
			g.drawRect(x + point.x - 1, y + point.y - 1, 34, 34);
		}
		if (state == SLOT) {
			g.setColor(Color.white);
			Point point = equipmentSlots.get(cursor);
			g.fillRect(point.x + 40, point.y + 35, 90, 40);
			g.setColor(Color.black);
			g.drawRect(point.x + 40, point.y + 35, 80, 40);
			g.drawString("Equip", point.x + 55 , point.y + 36);
			g.drawString("Unequipe", point.x + 44 , point.y + 55);
			if (stateCursor == 0)
				g.drawRect(point.x + 42, point.y + 37, 76, 18);
			else if (stateCursor == 1)	
				g.drawRect(point.x + 42, point.y + 57, 76, 16);
		}
	}
	
	private void drawItems(Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.drawRect(x + 450, y + 100, 180, 240);
		for (int i = topCursor, j = 0; j < ITEMS_PER_PAGE + 1; i++, j++) {
			if (i >= equipments.size()) break;
			Equipment eq = null;
			try {
				eq = equipments.get(i);
			} catch (Exception e) {
				break;
			}
			if (eq == null) break;
			g.drawImage(eq.getImage(), x + 452, y + 100 + (j * 40));
			g.drawImage(eq.getLabel(), x + 485, y + 100 + (j * 40));
			if (stateCursor == i) {
				g.drawRect(x + 450, y + 100 + (j * 55), 180, 40);
			}
		}
	}
	
	@Override
	protected void init() {
		cursor = -1;
		state = NORMAL;
	}

}
