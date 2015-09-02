package app;
import java.util.Timer;
import java.util.TimerTask;

import utility.BoundryType;
import utility.TimeConditionType;
import static app.Action.ActionType.*;
import static utility.TimeConditionType.*;

public class ActionFactory
{

//	public static enum AcctionType{
//		STRAIGHT, DISAPPEAR, JUMP, PATROL
//	}
	
//	public static class StraightMove extends TimerTask 
	public static class StraightMove extends Action
	{
		private Picture pic;
		float incrX;
		float incrY;
		
		public StraightMove(Picture ji, int desx, int desy, TimeConditionType timeConditionType, long period)
		{
			pic = ji;
			actionType = STRAIGHT;
			setDestination(desx,desy);
			incr.straightIncrement(pic.cxint, pic.cyint, getDesX(), getDesY());
			configureTimeCondition(timeConditionType, period);
			setSpeed(10);
		}

		public StraightMove(Picture ji, int startx, int starty, int desx,
				int desy, TimeConditionType timeConditionType, long period) {
			pic = ji;
			actionType = STRAIGHT;
			setStart(startx, starty);
			setDestination(desx,desy);
			incr.straightIncrement(getStartX(), getStartY(), getDesX(), getDesY());
			configureTimeCondition(timeConditionType, period);
			setSpeed(10);
		}

		public void run()
		{
			if(((pic.cxfloat - getDesX()) <= 1 && (pic.cxfloat - getDesX()) >= -1)
		    		&& ((pic.cyfloat - getDesY()) <= 1 && (pic.cyfloat - getDesY()) >= -1))
		    {
		    	pic.stoptimer();
		    	pic.nextAction();
		    }
			
			pic.updatePosition(incr);
		    pic.repaint();
		}
		
	}
	
	public static class Disappear extends Action 
	{
		private Picture pic;
		
		public Disappear(Picture pi, TimeConditionType timeConditionType, long period)
		{
			pic = pi;
			actionType = DISAPPEAR;
			setSpeed(0);
			configureTimeCondition(timeConditionType,period);
		}
		
		public void run()
		{
			pic.stoptimer();
			pic.disappear();
			pic.repaint();
		}
	}
	
	public static class Jump extends Action
	{
		private Picture pic;
		
		public Jump(Picture pi, int desx, int desy, TimeConditionType timeConditionType, long period)
		{
			pic = pi;
			actionType = JUMP;
			setDestination(desx,desy);
			configureTimeCondition(timeConditionType,period);
			setSpeed(0);
		}
		
		public void run()
		{
			pic.stoptimer();
			pic.updatePosition(getDesX(), getDesY());
		    pic.repaint();
		}
	}
	
	public static class Patrol extends Action
	{
		private Picture pic;
		
		public Patrol(Picture pi, int desx, int desy, TimeConditionType timeConditionType, long period)
		{
			pic = pi;
			actionType = PATROL;
//			destinationX = desx;
//			destinationY = desy;
			setStart(pic.cxint, pic.cyint);
			setDestination(desx,desy);
			incr.straightIncrement(pic.cxint, pic.cyint, getDesX(), getDesY());
			configureTimeCondition(timeConditionType,period);
			setSpeed(10);
		}
		
		public Patrol(Picture pi, int startx, int starty, int desx, int desy, TimeConditionType timeConditionType, long period)
		{
			pic = pi;
			actionType = PATROL;
			setStart(startx, starty);
			setDestination(desx, desy);
			incr.straightIncrement(getStartX(), getStartY(), getDesX(), getDesY());
			setSpeed(10);
		}
		
		public void run()
		{
			if(((pic.cxfloat - getDesX()) <= 1 && (pic.cxfloat - getDesX()) >= -1)
		    		&& ((pic.cyfloat - getDesY()) <= 1 && (pic.cyfloat - getDesY()) >= -1))
		    {
				incr.incrX = 0 - incr.incrX;
				incr.incrY = 0 - incr.incrY;
				int tempx = getDesX();
				int tempy = getDesY();
				setDestination(getStartX(), getStartY());
				setStart(tempx, tempy);
		    }
			pic.updatePosition(incr);
		    pic.repaint();
		}
	}
	
	
	public static class Mirror extends Action 
	{
		private Picture pic;
		private boolean hasCalculated = false;
		
		public Mirror(Picture pi, int desx, int desy, TimeConditionType timeConditionType, long period)
		{
			pic = pi;
			actionType = MIRROR;
			setStart(pic.cxint, pic.cyint);
			setDestination(desx, desy);
			incr.straightIncrement(getStartX(), getStartY(), getDesX(), getDesY());
		}
		
		public Mirror(Picture pi, int startX, int startY, int desX, int desY,
				TimeConditionType timeConditionType, long period) {
			
			pic = pi;
			actionType = MIRROR;
			setStart(startX, startY);
			setDestination(desX, desY);
			incr.straightIncrement(getStartX(), getStartY(), getDesX(), getDesY());
		}

		public void run()
		{
			if(hasCalculated == false)
			{
				BoundryType hitside = pic.hitSide;
				int mirrorX = 0;
				int mirrorY = 0;
				switch(hitside)
				{
				case LEFT:
					mirrorX = 3 * pic.cxint - pic.getCurrentDesX();
					setDestination(mirrorX, pic.getCurrentDesY());
					pic.setCurrentDesX(mirrorX);
					incr.straightIncrement(pic.cxint, pic.cyint, mirrorX, pic.getCurrentDesY());
					break;
				case RIGHT:
					mirrorX = pic.cxint - (pic.getCurrentDesX() - pic.cxint);
					setDestination(mirrorX, pic.getCurrentDesY());
					pic.setCurrentDesX(mirrorX);
					incr.straightIncrement(pic.cxint, pic.cyint, mirrorX, pic.getCurrentDesY());
					break;
				case UP:
					mirrorY = 3 * pic.cyint - pic.getCurrentDesY();
					setDestination(pic.getCurrentDesX(), mirrorY);
					pic.setCurrentDesY(mirrorY);
					incr.straightIncrement(pic.cxint, pic.cyint, pic.getCurrentDesX(), mirrorY);
					break;
				case DOWN:
					mirrorY = 2* pic.cyint - pic.getCurrentDesY();
					setDestination(pic.getCurrentDesX(), mirrorY);
					pic.setCurrentDesY(mirrorY);
					incr.straightIncrement(pic.cxint, pic.cyint, pic.getCurrentDesX(), mirrorY);
					break;
				}
				hasCalculated = true;
			}
			
			if(((pic.cxfloat - getDesX()) <= 1 && (pic.cxfloat - getDesX()) >= -1)
		    		&& ((pic.cyfloat - getDesY()) <= 1 && (pic.cyfloat - getDesY()) >= -1))
		    {
		    	pic.stoptimer();
		    	pic.nextAction();
		    }
			
			pic.updatePosition(incr);
		    pic.repaint();
		    
		}
	}
	
	
	public class rotate extends Action 
	{
		public void run()
		{
		    System.out.println("in rotate ");
		}
	}
}