package com.elfocrash.roboto.model;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.util.Rnd;

/**
 * @author Marcoviny
 */
public class NickGroups
{
	public static final Logger _log = Logger.getLogger(NickGroups.class.getName());
	
	private final String _groupName; // Nao usar Static
	private final List<NickFake> _nicksList; // List of nodes	- Nao usar Static
	
	public NickGroups(String name, List<NickFake> nicks)
	{
		_groupName = name;
		_nicksList = nicks;
	}
	
	public String getGroupName()
	{
		return _groupName;
	}
	
	public List<NickFake> getNicksList()
	{
		return _nicksList;
	}
	
	public int getNicksCount()
	{
		return _nicksList.size();
	}
	
	public String getRandomStringName()
	{
		final int randomSize = Rnd.get(3, 11);
		String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder name = new StringBuilder();
		
		Random rnd = new Random();
		while (name.length() < randomSize)
		{
			int index = (int) (rnd.nextFloat() * LETTERS.length());
			name.append(LETTERS.charAt(index));
		}
		return name.toString();
	}
	
	private boolean nameAlreadyExists(String name)
	{
		// return (CharNameTable.getInstance().getIdByName(name) > 0);
		// return CharNameTable.getInstance().doesCharNameExistInArray(name);
		
		if ((name == null) || name.isEmpty() || (L2World.getInstance().getPlayer(name) != null))
		{
			return true;
		}
		return false;
	}
	
	private String MSG()
	{
		return "NickGroups: " + _groupName + " isEmpty() or size(0).";
	}
	
	public String getRandomAccount()
	{
		if (_nicksList.size() <= 0)
		{
			_log.warning(MSG());
			return getRandomStringName();
		}
		
		return _nicksList.get(Rnd.get(_nicksList.size())).getNick(); // Rnd.get (n) n not Inclusive
	}
	
	// public synchronized String getRandomNick() COMODIFICATION ERROR	
	public String getRandomNick()
	{
		if (_nicksList.size() <= 0)
		{
			_log.warning(MSG());
			return getRandomStringName();
		}
		
		int random = Rnd.get(getNicksCount()); // Rnd.get (n) n not Inclusive
		String newName = _nicksList.get(random).getNick();
		
		while (nameAlreadyExists(newName))
		{
			newName = _nicksList.get(new Integer(random)).getNick();
		}
		
		_nicksList.remove(random);
		return newName;
	}
}
