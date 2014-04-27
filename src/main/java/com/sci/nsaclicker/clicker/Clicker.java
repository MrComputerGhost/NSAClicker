package com.sci.nsaclicker.clicker;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import com.sci.nsaclicker.NSAClicker;

/**
 * LD29: Below the Surface - NSAClicker
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class Clicker
{
	private static final Random RANDOM = new Random();

	protected final BigInteger individualBps;
	protected final BigDecimal initialPrice;
	protected BigInteger owned;
	protected BigDecimal price;
	protected BigInteger bps;
	protected int timer;

	public Clicker(final BigInteger individualBps, final BigInteger initialPrice)
	{
		this.individualBps = individualBps;
		this.initialPrice = new BigDecimal(initialPrice);
		this.price = new BigDecimal(initialPrice);
		this.bps = BigInteger.ZERO;
		this.owned = BigInteger.ZERO;

		this.timer = Clicker.RANDOM.nextInt(NSAClicker.INSTANCE.getTargetTPS());
	}

	public void update()
	{
		this.timer++;
		if(this.timer >= NSAClicker.INSTANCE.getTargetTPS())
		{
			this.timer = 0;
			NSAClicker.INSTANCE.steal(this.bps);
		}
	}

	public void buy()
	{
		this.owned = this.owned.add(BigInteger.ONE);
		this.bps = this.owned.multiply(this.individualBps);
		this.price = this.price.multiply(BigDecimal.valueOf((double) 1.2)).add(this.initialPrice);
	}

	public BigInteger getPrice()
	{
		return this.price.toBigInteger();
	}

	public BigInteger getOwned()
	{
		return this.owned;
	}

	public BigInteger getBPS()
	{
		return this.bps;
	}
}