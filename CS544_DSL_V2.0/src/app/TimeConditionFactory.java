package app;

import static utility.TimeConditionType.*;

import java.util.Timer;

import utility.TimeConditionType;

public class TimeConditionFactory {

	public static class ShowPictureCondition extends TimeCondition
	{
		Picture pic;
		
		public ShowPictureCondition(Picture p, TimeConditionType type, int min, int sec)
		{
			pic = p;
			timeConditionType = type;
			minute = min;
			second = sec;
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			pic.readPicture();
			pic.setVisible(true);
			pic.repaint();
			System.out.println(" should print ");
		}
		
		public static ShowPictureCondition makeShowPictureCondition(Picture p, TimeConditionType t, int m, int s)
		{
			return new ShowPictureCondition(p, t, m ,s);
		}
	}
	
	public static class ActionTimeConditinon extends TimeCondition
	{
		Picture pic;
		private long duration;
		private Action action;
		Timer durationTimer;
		
		public ActionTimeConditinon(long p)
		{
			setDuration(p);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			durationTimer.cancel();
			action.cancel();
		}
		
		public static ActionTimeConditinon makeActionTimeConditinon(long p)
		{
			return new ActionTimeConditinon(p);
		}

		public void setAction(Action act) {
			// TODO Auto-generated method stub
			action = act;
		}

		public long getDuration() {
			return duration;
		}

		public void setDuration(long duration) {
			this.duration = duration;
		}

		public void setDurationTimer(Timer dTimer) {
			// TODO Auto-generated method stub
			durationTimer = dTimer;
		}

	}
	
}
