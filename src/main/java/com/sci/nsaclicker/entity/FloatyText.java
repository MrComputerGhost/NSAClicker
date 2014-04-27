package com.sci.nsaclicker.entity;

import java.util.Random;
import com.sci.engine.graphics.Renderer;
import com.sci.engine.interfaces.Renderable;
import com.sci.engine.interfaces.Updatable;

/**
 * LD29: Below the Surface - NSAClicker
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class FloatyText implements Updatable, Renderable
{
	private static final Random random = new Random();

	private int age;
	private int maxAge;
	private int x, y;
	private String c;
	private boolean markedForRemoval;

	public FloatyText(int x, int y, String string)
	{
		this.x = x;
		this.y = y;
		this.c = string;
		this.maxAge = FloatyText.random.nextInt(50) + 50;
	}

	@Override
	public void render(Renderer renderer, int x, int y)
	{
		renderer.drawString(this.x, this.y, this.c);
	}

	@Override
	public void update()
	{
		if(this.age < this.maxAge)
			this.age++;
		else
			this.markedForRemoval = true;

		this.y--;
		this.x += FloatyText.random.nextBoolean() ? 1 : -1;
	}

	public boolean isMarkedForRemoval()
	{
		return this.markedForRemoval;
	}
}