package com.sci.nsaclicker.clicker;

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
	protected final BigInteger initialPrice;
	protected BigInteger owned;
	protected BigInteger price;
	protected BigInteger bps;
	protected int timer;

	public Clicker(final BigInteger individualBps, final BigInteger initialPrice)
	{
		this.individualBps = individualBps;
		this.initialPrice = initialPrice;
		this.price = initialPrice;
		this.bps = BigInteger.ZERO;
		this.owned = BigInteger.ZERO;

		this.timer = Clicker.RANDOM.nextInt(NSAClicker.INSTANCE.getTargetTPS());
	}

	private BigInteger pow(BigInteger base, BigInteger exp)
	{
		BigInteger result = BigInteger.ONE;
		while(exp.signum() > 0)
		{
			if(exp.testBit(0))
				result = result.multiply(base);
			base = base.multiply(base);
			exp = exp.shiftRight(1);
		}
		return result;
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

		// y = price
		// x = owned
		// x^2 + 15

		this.price = this.pow(BigInteger.valueOf(2), this.owned);
		this.price = this.price.add(this.initialPrice);
	}

	public BigInteger getPrice()
	{
		return this.price;
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