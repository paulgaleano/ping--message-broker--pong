package com.message.driven.ping.application.controller;

public enum CommandType {
	PING_MESSAGE;
	
	public String toString(){
		switch(this){
	    		case PING_MESSAGE :
	    			return "PING_MESSAGE";
	    }
	    
		return null;
	}
	
	public static boolean CommandValidator(String command){
		for(CommandType c : CommandType.values()){
			if(c.name().equals(command)){
				return true;
	        }
	    }

	    return false;
	}
}
