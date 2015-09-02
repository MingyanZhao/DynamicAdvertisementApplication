package app;

import java.util.ArrayList;

public class ConditionEvent {

	public ConditionType conditionType;
	private ArrayList<Action> followingActionList = new ArrayList<Action>();
	
	public static enum ConditionType {
		HIT
	}

	public boolean judge() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addFollowingAction(Action action)
	{
		followingActionList.add(action);
	}
	
	
}
