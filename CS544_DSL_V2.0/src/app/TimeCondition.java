package app;

import java.util.TimerTask;

import utility.TimeConditionType;

public abstract class TimeCondition extends TimerTask{
	
	TimeConditionType timeConditionType;
	int minute;
	int second;
	
	public long getPeiord() {
		// TODO Auto-generated method stub
		return (minute*60 + second)*1000;
	}
	
}
