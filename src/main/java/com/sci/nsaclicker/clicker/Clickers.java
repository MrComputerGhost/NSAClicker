package com.sci.nsaclicker.clicker;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.sci.nsaclicker.util.Utils;
import com.sci.nsaclicker.util.XMLUtils;

/**
 * LD29: Below the Surface - NSAClicker
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class Clickers
{
	private static final List<ClickerDefinition> clickers = new ArrayList<ClickerDefinition>();

	public static void load()
	{
		try
		{
			Document doc = XMLUtils.parse(Utils.getResource("clickers.xml"));

			if(doc.getDocumentElement().getNodeName().equals("clickers"))
			{
				NodeList nodes = doc.getDocumentElement().getChildNodes();

				for(int i = 0; i < nodes.getLength(); i++)
				{
					Node node = nodes.item(i);

					if(node.getNodeType() == Node.ELEMENT_NODE)
					{
						Element element = (Element) node;

						switch (node.getNodeName())
						{
						case "clicker":
							Clickers.clickers.add(new ClickerDefinition(element.getAttribute("name"), new BigInteger(element.getAttribute("initialPrice")), new BigInteger(element.getAttribute("bps"))));
							break;
						}
					}
				}
			}
			else
			{
				throw new AssertionError();
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static void map(Function f)
	{
		for(ClickerDefinition cd : Clickers.clickers)
			f.invoke(cd);
	}

	public static interface Function
	{
		public void invoke(ClickerDefinition def);
	}

	public static class ClickerDefinition
	{
		public final String name;
		public final BigInteger initialPrice;
		public final BigInteger bps;

		public ClickerDefinition(final String name, final BigInteger initialPrice, final BigInteger bps)
		{
			this.name = name;
			this.initialPrice = initialPrice;
			this.bps = bps;
		}

		@Override
		public String toString()
		{
			return name;
		}
	}
}