package com.discord.samplebot;

import java.util.ArrayList;
import java.util.List;

public class Member {
	private List<String> members = new ArrayList<String>();
	
	public Boolean register(String newMember) {
		if (members.contains(newMember)) return false;
		members.add(newMember);
		return true;
	}
	public Boolean remove(String delMember) {
		if (! members.contains(delMember)) return false;
		members.remove(delMember);
		return true;
	}
	
	public List<String> getMembers(){
		return members;
	}
	
	
}
