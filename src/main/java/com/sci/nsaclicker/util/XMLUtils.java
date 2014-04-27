package com.sci.nsaclicker.util;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 * LD29: Below the Surface - NSAClicker
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class XMLUtils
{
	private XMLUtils()
	{
	}

	public static Document parse(InputStream in)
	{
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(in);
			doc.getDocumentElement().normalize();
			return doc;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}