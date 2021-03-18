package com.elfocrash.enums;

/**
 * @author marcoviny
 */
public enum NickTypes
{
	ACCOUNTS("account_names"),
	NORMAL("normal_names"),
	SPECIAL("special_names");
	
	private final String nickGroupName;
	
	private NickTypes(String nick)
	{
		nickGroupName = nick;
	}
	
	public String getNickGroupString()
	{
		return nickGroupName;
	}
}
