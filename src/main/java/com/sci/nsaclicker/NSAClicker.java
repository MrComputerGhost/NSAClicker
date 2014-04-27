package com.sci.nsaclicker;

import com.sci.engine.SciGame;
import com.sci.engine.graphics.Display;
import com.sci.engine.graphics.Font;
import com.sci.engine.graphics.Font.CharCase;
import com.sci.engine.graphics.JFrameDisplay;
import com.sci.engine.graphics.Renderer;
import com.sci.engine.graphics.Texture;

/**
 * LD29: Below the Surface - NSAClicker
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class NSAClicker extends SciGame
{
	public static void main(String[] args)
	{
		new NSAClicker(new JFrameDisplay("NSAClicker", 800, 600)).start();
	}

	public NSAClicker(Display display)
	{
		super(display);
	}

	@Override
	public void init(Renderer renderer)
	{
		try
		{
			int width = 6;
			int height = 8;
			char[] characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ    0123456789-.!?/%$\\=*+,;:()&#\"'".toCharArray();
			Texture fontTexture = Texture.load(Utils.getResource("textures/font.png"));
			Font font = new Font(width, height, characters, fontTexture);
			font.setCharCase(CharCase.UPPER);
			renderer.setFont(font);
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}

	@Override
	public void update()
	{

	}

	@Override
	public void render(Renderer renderer)
	{
		
	}

	@Override
	public void shutdown()
	{

	}
}