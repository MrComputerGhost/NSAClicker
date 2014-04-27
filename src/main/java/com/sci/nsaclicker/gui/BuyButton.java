package com.sci.nsaclicker.gui;

import java.math.BigInteger;
import com.sci.engine.gui.components.TextButton;
import com.sci.nsaclicker.NSAClicker;

/**
 * LD29: Below the Surface - NSAClicker
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class BuyButton extends TextButton
{
	protected BigInteger price;

	public BuyButton(int i, int j, int k, int l, String string, BigInteger price)
	{
		super(i, j, k, l, string);
		this.price = price;
	}

	@Override
	public void update()
	{
		super.update();
		this.setEnabled(NSAClicker.INSTANCE.getStolenBytes().compareTo(this.price) >= 0);
	}

	public void setMinPrice(BigInteger price2)
	{
		this.price = price2;
	}
}