package com.sci.nsaclicker;

import java.math.BigInteger;
import com.sci.engine.SciGame;
import com.sci.engine.graphics.Font;
import com.sci.engine.graphics.Font.CharCase;
import com.sci.engine.graphics.JFrameDisplay;
import com.sci.engine.graphics.Renderer;
import com.sci.engine.graphics.Texture;
import com.sci.engine.gui.GUI;

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
		NSAClicker.INSTANCE.start();
	}

	public static final NSAClicker INSTANCE = new NSAClicker();

	private NSAClicker()
	{
		super(new JFrameDisplay("NSAClicker", 800, 600));
	}

	private GUI gui;

	private BigInteger bytes;

	@Override
	public void init(Renderer renderer)
	{
		try
		{
			int width = 6;
			int height = 8;
			char[] characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ    0123456789-.!?:".toCharArray();
			Texture fontTexture = Texture.load(Utils.getResource("textures/font.png"));
			Font font = new Font(width, height, characters, fontTexture);
			font.setCharCase(CharCase.UPPER);
			renderer.setFont(font);

			this.gui = new GUIInGame();
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}

	@Override
	public void update()
	{
		this.gui.update();
	}

	@Override
	public void render(Renderer renderer)
	{
		this.gui.render(renderer, 0, 0);
	}

	@Override
	public void shutdown()
	{

	}

	public void steal(BigInteger bytes)
	{
		this.bytes = this.bytes.add(bytes);
	}
}