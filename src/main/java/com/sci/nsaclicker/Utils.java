package com.sci.nsaclicker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * LD29: Below the Surface - NSAClicker
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class Utils
{
	private Utils(){}
	
	public static InputStream getResource(String string) throws FileNotFoundException
	{
		return new FileInputStream("src/main/resources/" + string);
	}
}