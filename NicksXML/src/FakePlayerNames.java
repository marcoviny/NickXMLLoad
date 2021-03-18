package com.elfocrash.roboto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.elfocrash.enums.NickTypes;
import com.elfocrash.roboto.model.NickGroups;
import com.elfocrash.roboto.model.NickFake;
import com.l2jserver.gameserver.engines.DocumentParser;

/**
 * @author marcoviny
 *
 */
public class FakePlayerNames extends DocumentParser
{
	public static final Logger _log = Logger.getLogger(FakePlayerNames.class.getName());
	protected final Map<String, NickGroups> _nicks = new HashMap<>();
	
	protected FakePlayerNames()
	{
		load();
	}
	
	@Override
	public void load()
	{
		_nicks.clear();
		parseDatapackFile("data/fakes/Names.xml");
		_log.log(Level.INFO, "FakePlayerNames: " + _nicks.size() + " nick groups load.");
	}
	
	@Override
	protected void parseDocument()
	{
		Node n = getCurrentDocument().getFirstChild();
		for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
		{
			if (d.getNodeName().equals("group"))
			{
				String groupName = parseString(d.getAttributes(), "groupName");
				List<NickFake> list = new ArrayList<>();
				for (Node r = d.getFirstChild(); r != null; r = r.getNextSibling())
				{
					if (r.getNodeName().equals("string"))
					{
						NamedNodeMap attrs = r.getAttributes();
						
						int id = parseInt(attrs, "id");
						String nick = parseString(attrs, "nick");
						
						list.add(new NickFake(id, nick));
					}
				}
				_nicks.put(groupName, new NickGroups(groupName, list));
			}
		}
	}

	public String getRandomAccountName()
	{
		return getNickGroup(NickTypes.ACCOUNTS).getRandomAccount();
	}
	
	public String getRandomNormalName()
	{
		return getNickGroup(NickTypes.NORMAL).getRandomNick();
	}
	
	public String getRandomSpecialName()
	{
		return getNickGroup(NickTypes.SPECIAL).getRandomNick();
	}
	
	public NickGroups getNickGroup(final NickTypes nickType)
	{
		if (!_nicks.containsKey(nickType.getNickGroupString()))
		{
			_log.warning("FakePlayerNames Invalid Nick Group");
			return (NickGroups) Collections.emptyList();
		}
		return _nicks.get(nickType.getNickGroupString());
	}
	
	public NickGroups getNickGroup(final String nickGroup)
	{
		if (!_nicks.containsKey(nickGroup))
		{
			_log.warning("FakePlayerNicks Invalid Nick Group");
			return (NickGroups) Collections.emptyList();
		}
		return _nicks.get(nickGroup);
	}
	
	public static FakePlayerNames getInstance()
	{
		return SingletonHolder._INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final FakePlayerNames _INSTANCE = new FakePlayerNames();
	}
}
