package com.zimu.ao.npc;

import java.awt.Point;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class NPC {

	private Image image;
	
	private String[] dialogs;
	
	public NPC(Image image, String...dialogs) {
		this.image = image;
		this.dialogs = dialogs;
	}
	
	public Image getImage() {
		return image;
	}
	
	public String getDialog() {
		return dialogs[new Random().nextInt(dialogs.length)];
	}
	
	public static void render(Graphics g, Point point, String dialog) {
		int baseX = point.x + 150;
		int baseY = point.y + 120;
		Color orgColor = g.getColor();
		g.setColor(Color.white);
		g.fillRect(baseX, baseY, 300, 150);
		g.setColor(Color.black);
		g.drawString(dialog, baseX + 20, baseY + 50);
		g.setColor(orgColor);
	}

}
