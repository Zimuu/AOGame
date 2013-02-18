package com.zimu.ao.controller;

import org.newdawn.slick.Sound;

/**
 * 音乐控制器
 * 负责背景音乐和游戏声效的控制.
 * @author zimu
 *
 */
public class MusicController {
	
	private Sound[] Sounds;
	private int curr_sound;
	
	public MusicController() {
		try {
			setMusic();
		} catch (Exception e) {}
	}
	
	private void setMusic() throws Exception {
		Sounds = new Sound[10];
		//Sounds[0] = new Sound("resource/music/huanqin.ogg");
		//Sounds[0].loop();
	}
	
	public void musicSwitch() {
		try {
			if (Sounds[curr_sound].playing())
				Sounds[curr_sound].stop();
			else
				Sounds[curr_sound].loop();
		} catch (Exception e) {}
	}

}
