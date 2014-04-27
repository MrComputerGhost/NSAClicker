package com.sci.nsaclicker.gui;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.sci.engine.graphics.Font;
import com.sci.engine.graphics.Renderer;
import com.sci.engine.gui.Component;
import com.sci.engine.gui.GUI;
import com.sci.engine.gui.components.TextButton;
import com.sci.engine.gui.listeners.ClickListener;
import com.sci.engine.input.Mouse;
import com.sci.engine.util.ObjectPool;
import com.sci.engine.util.PoolHandler;
import com.sci.engine.util.Vector2I;
import com.sci.engine.util.Vector2IPool;
import com.sci.nsaclicker.NSAClicker;
import com.sci.nsaclicker.clicker.Clicker;
import com.sci.nsaclicker.clicker.Clickers;
import com.sci.nsaclicker.clicker.Clickers.ClickerDefinition;
import com.sci.nsaclicker.clicker.Clickers.Function;
import com.sci.nsaclicker.entity.FloatyText;
import com.sci.nsaclicker.util.ColorCache;

/**
 * LD29: Below the Surface - NSAClicker
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class GUIInGame extends GUI
{
	private TextButton stealByte;

	private ObjectPool<Data> dataPool;

	private List<Data> data;
	private int spawnTimer;

	private List<FloatyText> floatyTexts;

	public GUIInGame()
	{
		super(0, 0, 800, 600);
		this.stealByte = new TextButton(300, 60, 200, 30, "Steal Byte");
		this.stealByte.addListener(new ClickListener()
		{
			@Override
			public void onClicked(Component component)
			{
				NSAClicker.INSTANCE.steal(BigInteger.ONE);
				GUIInGame.this.data.add(GUIInGame.this.dataPool.checkOut());
				GUIInGame.this.floatyTexts.add(new FloatyText(Mouse.getX(), Mouse.getY(), "+1"));
			}
		});
		this.add(this.stealByte);

		this.floatyTexts = new ArrayList<FloatyText>();

		Clickers.map(new Function()
		{
			private int y = 0;

			@Override
			public void invoke(final ClickerDefinition def)
			{
				final Clicker clicker = NSAClicker.INSTANCE.getClicker(def.name);
				final BigInteger price = clicker.getPrice();
				final BuyButton buy = new BuyButton(250, 155 + this.y * 40, 300, 30, price.toString() + ": " + def.name + " " + def.bps.toString() + "b/s (0)", price);
				buy.addListener(new ClickListener()
				{
					@Override
					public void onClicked(Component component)
					{
						if(NSAClicker.INSTANCE.getBytes().compareTo(price) >= 0)
						{
							NSAClicker.INSTANCE.steal(price.negate());
							clicker.buy();
							buy.setText(clicker.getPrice().toString() + ": " + def.name + " " + def.bps.toString() + "b/s (" + clicker.getOwned().toString() + ")");
							buy.setMinPrice(clicker.getPrice());
						}
					}
				});
				GUIInGame.this.add(buy);

				if(this.y < 10)
					this.y++;
			}
		});

		this.data = new ArrayList<Data>();
		this.dataPool = new ObjectPool<Data>(new PoolHandler<Data>()
		{
			private Random rand = new Random();

			@Override
			public Data create()
			{
				Data t = new Data();
				this.clean(t);
				return t;
			}

			@Override
			public void clean(Data t)
			{
				t.setX(this.rand.nextBoolean() ? (5 + this.rand.nextInt(190)) : (600 + this.rand.nextInt(190)));
				t.setY(-10);
				t.setCharacter(this.randChar());
			}

			private char randChar()
			{
				if(this.rand.nextBoolean())
				{
					if(this.rand.nextBoolean())
					{
						return (char) (65 + this.rand.nextInt(90 - 65));
					}
					else
					{
						return (char) (97 + this.rand.nextInt(122 - 97));
					}
				}
				else
				{
					return (char) (48 + this.rand.nextInt(57 - 48));
				}
			}
		});
	}

	@Override
	public void update()
	{
		super.update();

		for(FloatyText f : this.floatyTexts)
			f.update();

		this.spawnTimer++;
		if(this.spawnTimer >= 7)
		{
			this.spawnTimer = 0;
			this.data.add(this.dataPool.checkOut());
		}

		for(int i = 0; i < this.data.size(); i++)
		{
			Data data = this.data.get(i);

			data.setY(data.getY() + 1);
			if(data.getY() > 600)
			{
				this.data.remove(data);
				data.releaseResources();
			}
		}
	}

	@Override
	public void render(Renderer renderer, int x, int y)
	{
		super.render(renderer, x, y);
		Font font = renderer.getFont();

		renderer.fillRect(200, 0, 10, 600, ColorCache.get(100, 100, 100));
		renderer.fillRect(590, 0, 10, 600, ColorCache.get(100, 100, 100));
		renderer.fillRect(210, 120, 590 - 200, 10, ColorCache.get(100, 100, 100));

		String str = "Stolen Bytes: " + NSAClicker.INSTANCE.getStolenBytes().toString();
		renderer.drawString(210 + ((580 - 210) / 2) - font.getStringWidth(str) / 2, 12, str);
		str = "BPS: " + NSAClicker.INSTANCE.getTotalBPS().toString();
		renderer.drawString(210 + ((580 - 210) / 2) - font.getStringWidth(str) / 2, 30, str);

		for(int i = 0; i < this.data.size(); i++)
		{
			Data data = this.data.get(i);
			renderer.drawString(data.getX(), data.getY(), "" + data.getCharacter());
		}

		for(FloatyText f : this.floatyTexts)
			renderer.render(0, 0, f);
	}

	private static class Data
	{
		private Vector2I position;
		private char character;

		public Data()
		{
			this.position = Vector2IPool.checkOut();
		}

		public void releaseResources()
		{
			Vector2IPool.checkIn(this.position);
			this.position = null;
		}

		public void setCharacter(char character)
		{
			this.character = character;
		}

		public void setX(int x)
		{
			this.position.setX(x);
		}

		public void setY(int y)
		{
			this.position.setY(y);
		}

		public int getX()
		{
			return this.position.getX();
		}

		public int getY()
		{
			return this.position.getY();
		}

		public char getCharacter()
		{
			return this.character;
		}
	}
}