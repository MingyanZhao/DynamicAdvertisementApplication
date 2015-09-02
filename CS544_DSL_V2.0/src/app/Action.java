package app;

import java.util.TimerTask;

import app.TimeConditionFactory.ActionTimeConditinon;
import utility.TimeConditionType;
import static utility.TimeConditionType.*;

public abstract class Action extends TimerTask{

	private int destinationX;
	private int destinationY;
	private int startX;
	private int startY;
	public ActionType actionType;
	private boolean isrunning = false;
	protected ActionCalculation incr = new ActionCalculation();
	private long actionDelay;
	public ActionTimeConditinon drurationEvent = null;
	public TimeConditionType timeConditionType = NOW;
	private long speed = 10;
	
	public static enum ActionType{
		STRAIGHT, DISAPPEAR, JUMP, PATROL, MIRROR
	}
	
	public void setDestination(int desx, int desy)
	{
		destinationX = desx;
		destinationY = desy;
	}
	
	public void setStart(int startx, int starty)
	{
		setStartX(startx);
		setStartY(starty);
	}
	
	public int getDesX()
	{
		return destinationX;
	}
	
	public int getDesY()
	{
		return destinationY;
	}
	
	public void setRunningState(boolean b)
	{
		isrunning = b;
	}
	
	public boolean getRunningState()
	{
		return isrunning;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}
	
	public void updateIncr(int startx, int starty)
	{
		incr.straightIncrement(startx, starty, destinationX, destinationY);
	}
	
	public ActionTimeConditinon configureTimeCondition(TimeConditionType timeconditiontype, long period)
	{
		timeConditionType = timeconditiontype;
		switch(timeConditionType)
		{
		case AFTER:
			setActionDelay(period);
			break;
		case UNTIL:
			drurationEvent = new ActionTimeConditinon(period);
			break;
		default:
			break;
		}
		return drurationEvent;
	}

	public long getActionDelay() {
		return actionDelay;
	}

	public void setActionDelay(long actionDelay) {
		this.actionDelay = actionDelay;
	}

	public long getSpeed() {
		return (int)10 - speed/10;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}
}
