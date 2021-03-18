package com.elfocrash.roboto.model;

/**
 * @author marcoviny
 *
 */
public class NickFake
{
	private final int _id;
	private final String _nick;
	
	public NickFake(int id, String nick)
	{
		_id = id;
		_nick = nick;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public String getNick()
	{
		return _nick;
	}
}
