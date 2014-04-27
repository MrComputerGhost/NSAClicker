package com.sci.nsaclicker;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.sci.engine.SciGame;
import com.sci.engine.graphics.Font;
import com.sci.engine.graphics.Font.CharCase;
import com.sci.engine.graphics.JFrameDisplay;
import com.sci.engine.graphics.Renderer;
import com.sci.engine.graphics.Texture;
import com.sci.engine.gui.GUI;
import com.sci.nsaclicker.clicker.Clicker;
import com.sci.nsaclicker.clicker.Clickers;
import com.sci.nsaclicker.clicker.Clickers.ClickerDefinition;
import com.sci.nsaclicker.clicker.Clickers.Function;
import com.sci.nsaclicker.gui.GUIInGame;
import com.sci.nsaclicker.util.Utils;

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
	private Map<String, Clicker> clickers;

	@Override
	public void init(Renderer renderer)
	{
		try
		{
			int width = 6;
			int height = 8;
			char[] characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ    0123456789-.!?:/()".toCharArray();
			Texture fontTexture = Texture.load(Utils.getResource("textures/font.png"));
			Font font = new Font(width, height, characters, fontTexture);
			font.setCharCase(CharCase.UPPER);
			renderer.setFont(font);

			this.clickers = new HashMap<String, Clicker>();
			Clickers.load();
			Clickers.map(new Function()
			{
				@Override
				public void invoke(ClickerDefinition def)
				{
					NSAClicker.this.clickers.put(def.name, new Clicker(def.bps, def.initialPrice));
				}
			});
			
			this.gui = new GUIInGame();
			this.bytes = BigInteger.ZERO;
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

		Set<String> names = this.clickers.keySet();
		for(String name : names)
		{
			Clicker clicker = this.clickers.get(name);
			clicker.update();
		}
	}

	@Override
	public void render(Renderer renderer)
	{
		renderer.render(0, 0, this.gui);
	}

	@Override
	public void shutdown()
	{

	}

	public BigInteger getTotalBPS()
	{
		BigInteger bps = BigInteger.ZERO;

		Set<String> names = this.clickers.keySet();
		for(String name : names)
		{
			Clicker clicker = this.clickers.get(name);
			bps = bps.add(clicker.getBPS());
		}

		return bps;
	}

	public BigInteger getBytes()
	{
		return this.bytes;
	}

	public void steal(BigInteger bytes)
	{
		this.bytes = this.bytes.add(bytes);
	}

	public BigInteger getStolenBytes()
	{
		return this.bytes;
	}

	public Clicker getClicker(String string)
	{
		return this.clickers.get(string);
	}
}