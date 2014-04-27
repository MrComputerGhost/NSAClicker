package com.sci.nsaclicker.util;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import com.sci.engine.graphics.Color;

/**
 * LD29: Below the Surface - NSAClicker
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class ColorCache
{
	private static Map<Integer, WeakReference<Color>> cache = new HashMap<Integer, WeakReference<Color>>();

	private ColorCache()
	{
	}

	public static Color get(int red, int green, int blue)
	{
		return ColorCache.get(Color.getHex(red, green, blue));
	}

	public static Color get(int red, int green, int blue, int alpha)
	{
		return ColorCache.get(Color.getHex(red, green, blue, alpha));
	}

	public static Color get(int c)
	{
		Color color = null;

		if(ColorCache.cache.containsKey(c))
		{
			WeakReference<Color> ref = ColorCache.cache.get(c);

			if(ref == null)
			{
				color = new Color(c);
				ref = new WeakReference<Color>(color);
				ColorCache.cache.put(c, ref);
			}
			else
			{
				color = ref.get();

				if(color == null)
				{
					color = new Color(c);
					ref = new WeakReference<Color>(color);
					ColorCache.cache.put(c, ref);
				}
			}
		}
		else
		{
			color = new Color(c);
			WeakReference<Color> ref = new WeakReference<Color>(color);
			ColorCache.cache.put(c, ref);
		}

		return color;
	}
}