package app;
import javax.imageio.ImageIO;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.*;
import javax.swing.JPanel;

import app.Action.ActionType;
import app.ActionFactory.*;
import static app.ActionFactory.*;
import app.ConditionEventFactory.Hit;
import app.TimeConditionFactory.ActionTimeConditinon;
import app.TimeConditionFactory.ShowPictureCondition;
import utility.BoundryType;
import utility.DebugOutput;
import utility.TimeConditionType;
import static utility.TimeConditionType.*;

public class Picture extends JPanel {
//public class Picture extends Component {
	public String imageFileName;
	public String pictureID;
    BufferedImage bi;
    int w=0, h=0;//the width and the height of the picture
    int cxint=0, cyint=0;//the current position of the picture, be different after movements
    float cxfloat = 0, cyfloat = 0;//float value of the current position of the picture
    int startX;//the start position of the picture
    int startY;//the start position of the picture
    private ArrayList<Timer> timerList = new ArrayList<Timer>();//timer list 
    private ArrayList<ActionType> actionTypeList = new ArrayList<ActionType>();//actiontype list
    private ArrayList<Action> acctionList = new ArrayList<Action>();//action list
    private int actionIndex = 0;
    ApplicationManager am = ApplicationManager.getAppletInstance();
    private ArrayList<ConditionEvent> conditionList = new ArrayList<ConditionEvent>();
    private ArrayList<ArrayList<Action>> followingActionList = new ArrayList<ArrayList<Action>>();
	private Stack<ArrayList<Action>> nextActionListStack = new Stack<ArrayList<Action>>();
	public ShowTime showTime;
	public HitPoint hitPoint = new HitPoint();
	public BoundryType hitSide = null;
	private int currentDesX;
	private int currentDesY;
	
	
    public void setPictureName(String imageFileNameFromRuby)
    {
    	imageFileName = imageFileNameFromRuby;
    }
    
	public class ShowTime
	{
		int minute = 0;
		int second = 0;
		TimeConditionType type = NOW;
		
		public ShowTime(String t, int min, int sec)
		{
			type = TimeConditionType.getTimeConditionType(t);
			minute = min;
			second = sec;
		}
	}
    
	public Picture(String imageFileName, String picID)
	{
		this.imageFileName = imageFileName;
		this.pictureID = picID;
//		this.setVisible();
//		if(timeConditionType == NOW)
//		{
//	        readPincture(imageFileName, imageSrc);
//		}
//		else
//		{
//			ShowPictureCondition Condition = ShowPictureCondition.makeShowPictureCondition(timeConditionType, min, sec);
//			Timer timer = new Timer();
//            timer.schedule(Condition, 0, Condition.getPeiord());
//		}
	}

	public void readPicture() {
		URL imageSrc = null;
		try {
             imageSrc = ((new File("pics/"+imageFileName)).toURI()).toURL();
        } catch (MalformedURLException e) {
        }
        
        try {
			bi = ImageIO.read(imageSrc);
	        w = bi.getWidth(null);
	        h = bi.getHeight(null);
	    } catch (IOException e) {
	        System.out.println("Image could not be read");
	//        System.exit(1);
	    }
	}
	
	
    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }
    
    public void setStartPosition(int startPositionX, int startPositionY)
    {
    	startX = startPositionX;
    	startY = startPositionY;
    	cxint = startPositionX;
    	cyint = startPositionY;
    	cxfloat = startPositionX;
    	cyfloat = startPositionY;
    	this.setBounds(startX, startY, w, h);
    }
	
    public void updatePosition(ActionCalculation incr)
    {
    	cxfloat = cxfloat + incr.incrX;
    	cyfloat = cyfloat + incr.incrY;
    	cxint = (int)cxfloat;
    	cyint = (int)cyfloat;
    	this.setBounds(cxint, cyint, w, h);
    	checkCondition();
    }
    
    private void checkCondition() {
		// TODO Auto-generated method stub
    	boolean result = false;
    	
    	for(int i = 0; i < conditionList.size(); i++)
    	{
    		result = conditionList.get(i).judge();
        	if(result == true)
        	{
        		ArrayList<Action> list = followingActionList.get(i);
        		
        		if(list.get(0).getRunningState() == false)
        		{
        			list.get(0).setRunningState(true);
        			nextActionListStack.push(list);
        			stoptimer();
        			Debug(" start following  " + list.toString());
        			list.get(0).updateIncr(cxint, cyint);
        			actionIndex = 0;
        			actionStart(list);
//        			Timer timer = new Timer();
//	            	timerList.add(timer);
//	                timer.schedule(list.get(0), 0, list.get(0).getSpeed());
	                return;
        		}
        		else
        		{
        			return;
        		}
        	}
    	}
	}

	private void Debug(String string) {
		// TODO Auto-generated method stub
		DebugOutput.Debug(string);
	}


	public void actionStart(ArrayList<Action> acctionList)
    {
    	if (acctionList.isEmpty() == true)
    	{
    		return;
    	}
    	Timer timer = new Timer();
    	timerList.add(timer);
    	Debug(acctionList.get(0).timeConditionType+ " type   ");
    	Action action = acctionList.get(0);
    	if(action.timeConditionType == AFTER)
    	{
    		timerSchedule(timer, action, AFTER);
    	}
    	else if(action.timeConditionType == UNTIL)
    	{
    		ActionTimeConditinon drurationEvent = action.drurationEvent;
    		drurationEvent.setAction(action);
    		
    		Debug("duration = " + action.drurationEvent.getDuration());
    		Timer durationTimer = new Timer();
    		drurationEvent.setDurationTimer(durationTimer);
    		durationTimer.schedule(action.drurationEvent, action.drurationEvent.getDuration());
    		timerSchedule(timer, action, UNTIL);
    	}
    	else
    	{
    		timerSchedule(timer, action, NOW);
    	}

        acctionList.get(0).setRunningState(true);
        nextActionListStack.push(acctionList);
        System.out.println(this.pictureID + " start action it has " + acctionList.size()+ " acctions ");
    }
    
	private void timerSchedule(Timer timer, Action action , TimeConditionType type) {
		// TODO Auto-generated method stub
		long delay;
		if(type == AFTER)
		{
			long currentTimeStamp = System.currentTimeMillis() - am.getstartTimeStamp();
		
			System.out.println("currentTimeStamp = " + currentTimeStamp);
			
			delay = action.getActionDelay() - currentTimeStamp;
			
			System.out.println("delay = " + delay);
		}
		else
		{
			delay = action.getActionDelay();
		}
		
		switch(action.actionType)
		{
		case STRAIGHT:
	    	this.setCurrentDesX(action.getDesX());
	    	this.setCurrentDesY(action.getDesY());
			timer.schedule(action, delay, action.getSpeed());
			break;
		case DISAPPEAR:
	    	this.setCurrentDesX(action.getDesX());
	    	this.setCurrentDesY(action.getDesY());
			timer.schedule(action, delay);
			break;
		case JUMP:
	    	this.setCurrentDesX(action.getDesX());
	    	this.setCurrentDesY(action.getDesY());
			timer.schedule(action, delay);
			break;
		case PATROL:
	    	this.setCurrentDesX(action.getDesX());
	    	this.setCurrentDesY(action.getDesY());
			timer.schedule(action, delay, action.getSpeed());
			break;
		case MIRROR:
			timer.schedule(action, delay, action.getSpeed());
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(bi,
				0, 0, w, h,
	            null);
	    }

	public void stoptimer() {
		timerList.get(0).cancel();
		timerList.remove(0);
		acctionList.get(actionIndex).setRunningState(false);
	}
	
	public void nextAction()
	{
		actionIndex++;
		ArrayList<Action> list = nextActionListStack.peek();
		
		if(list.size() > actionIndex)
		{
			Action action = list.get(actionIndex);
			Timer timer = new Timer();
			timerList.add(timer);
			switch(action.timeConditionType)
			{
			case AFTER:
//	    		long period = action.getActionDelay();
//	    		timer.schedule(action, period, 10);
	    		timerSchedule(timer, action, AFTER);
				break;
			case UNTIL:
	    		ActionTimeConditinon drurationEvent = action.drurationEvent;
	    		drurationEvent.setAction(action);
	    		Timer durationTimer = new Timer();
	    		drurationEvent.setDurationTimer(durationTimer);
	    		durationTimer.schedule(action.drurationEvent, action.drurationEvent.getPeiord());
//	    		timer.schedule(action, 0, 10);
	    		timerSchedule(timer, action, UNTIL);
				break;
			case NOW:
//				timer.schedule(action, 0, action.getSpeed());
				timerSchedule(timer, action, NOW);
				break;
			}

//			timer.schedule(action,0, action.getSpeed());
//			Timer timer = new Timer();
//			switch(list.get(actionIndex).actionType)
//			{
//			case STRAIGHT:
//				timerList.add(timer);
//				timer.schedule(list.get(actionIndex),0, 10);
//				break;
//			case DISAPPEAR:
//				timerList.add(timer);
//				timer.schedule(list.get(actionIndex), 0);
//				break;
//			case JUMP:
//				timerList.add(timer);
//				timer.schedule(list.get(actionIndex), 0);
//				break;
//			case PATROL:
//				timerList.add(timer);
//				timer.schedule(list.get(actionIndex),0, 10);
//				break;
//			}
			list.get(actionIndex).setRunningState(true);
		}
		else
		{
			nextActionListStack.pop();
		}
	}

	public void addAction(Picture pic, int desx, int desy, ActionType acctionType, TimeConditionType timeConditionType, long period, int speed) {
		// TODO Auto-generated method stub
		System.out.println("picname = " + pic.pictureID + "  add action x = " + desx + " y = " + desy);
		
		ArrayList<Action> list;
		
		if(ApplicationManager.getAppletInstance().getfollowingActionFlag() == 1)
		{
			if(followingActionList.isEmpty() == true)
			{
				return;
			}
			else
			{
				list = followingActionList.get(followingActionList.size()-1);
			}
		}
		else
		{
			list = acctionList;
		}
		
		switch(acctionType)
		{
		case STRAIGHT:
			StraightMove actiontimer;
			if(list.isEmpty() != true)
			{
				Action lastActionTimer = list.get(list.size()-1);
				actiontimer = new ActionFactory.StraightMove(pic, lastActionTimer.getDesX(), lastActionTimer.getDesY(), desx, desy, timeConditionType, period);
			}
			else
			{
				actiontimer = new ActionFactory.StraightMove(pic, desx, desy, timeConditionType, period);
			}
			actiontimer.setSpeed(speed);
			list.add(actiontimer);
			break;
			
		case DISAPPEAR:
			Action disappeaAcction = new ActionFactory.Disappear(pic, timeConditionType, period);
			list.add(disappeaAcction);
			break;
			
		case JUMP:
			Action jumpAcction = new ActionFactory.Jump(pic, desx, desy, timeConditionType, period);
			list.add(jumpAcction);
			break;
			
		case PATROL:
			Action patrolAcction;
			if(list.isEmpty() == true)
			{
				patrolAcction = new ActionFactory.Patrol(pic, desx, desy, timeConditionType, period);
			}
			else
			{
				Action lastAction = list.get(list.size()-1);
				patrolAcction = new ActionFactory.Patrol(pic, lastAction.getDesX(), lastAction.getDesY() , desx, desy , timeConditionType, period);

			}
			list.add(patrolAcction);
			patrolAcction.setSpeed(speed);
			break;
		case MIRROR:
			Action mirrorAction;
			if(list.isEmpty() == true)
			{
				mirrorAction = new ActionFactory.Mirror(pic, desx, desy, timeConditionType, period);
			}
			else
			{
				Action lastAction = list.get(list.size()-1);
				mirrorAction = new ActionFactory.Mirror(pic, lastAction.getDesX(), lastAction.getDesY() , desx, desy , timeConditionType, period);

			}
			list.add(mirrorAction);
			mirrorAction.setSpeed(speed);
			break;
		}
	}

	public void addCondition(int conditionType, String condition)
	{
		followingActionList.add(new ArrayList<Action>());
		
		switch(conditionType)
		{
		case 0://HIT condition
			BoundryType c = BoundryType.getBoundryType(condition);
			if(c != null)
			{
				Hit hit = Hit.makeHitCondition(this, c);
				conditionList.add(hit);
			}
			else
			{
				Picture p = ApplicationManager.getAppletInstance().getPictures().get(condition);
				Hit hit = Hit.makeHitCondition(this, p);
				conditionList.add(hit);
			}

			break;
		}
	}
	
	
	private boolean conditionIsBoundry(String condition) {
		// TODO Auto-generated method stub
		
		if(condition == "ANYBOUNDRY" || condition == "RIGHT" || condition == "LEFT" || condition == "UP" 
				|| condition == "DOWN")
			return true;
		else
			return false;
	}

	
	public void disappear() {
		// TODO Auto-generated method stub
		acctionList.removeAll(acctionList);
		timerList.removeAll(timerList);
		nextActionListStack.removeAll(followingActionList);
        w = 0;
        h = 0;
        bi.flush();
        am.removeComponet(this);
	}

	public void updatePosition(int x, int y) {
		// TODO Auto-generated method stub
		cxint = x;
		cxint = y;
		this.setBounds(cxint, cyint, w, h);
	}


	public void setShowTime(String timeConditionType, int minute, int second) {
		
		showTime = new ShowTime(timeConditionType, minute, second);
		
	}
	
	public static Picture makePicture(String imageFileName, String picID, TimeConditionType timeConditionType, int min, int sec)
	{
		Picture pic = new Picture(imageFileName, picID);
		if(timeConditionType == NOW)
		{
			pic.readPicture();
			pic.setVisible(true);
			pic.repaint();
		}
		else
		{
			pic.readPicture();
			pic.setVisible(false);
			ShowPictureCondition Condition = ShowPictureCondition.makeShowPictureCondition(pic, timeConditionType, min, sec);
			Timer timer = new Timer();
            timer.schedule(Condition, Condition.getPeiord());
		}
		
		return pic;
		
	}

	public void sethitPoint(int hitx, int hity)
	{
		hitPoint.hitPointX = hitx;
		hitPoint.hitPointY = hity;
	}
	
	public int getHitPointX()
	{
		return hitPoint.hitPointX;
	}
	
	public int getHitPointY()
	{
		return hitPoint.hitPointY;
	}
	
	public static class HitPoint{
		private int hitPointX;
		private int hitPointY;
		
		public HitPoint()
		{
			hitPointX = -1;
			hitPointY = -1;
		}
	}
	
	public ArrayList<Action> getAcctionList()
	{
		return acctionList;
	}

	public int getCurrentDesX() {
		return currentDesX;
	}

	public void setCurrentDesX(int currentDesX) {
		this.currentDesX = currentDesX;
	}

	public int getCurrentDesY() {
		return currentDesY;
	}

	public void setCurrentDesY(int currentDesY) {
		this.currentDesY = currentDesY;
	}
	
}
