package app;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import static app.Action.ActionType.*;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;

import utility.TimeConditionType;

public class ApplicationManager{

	static String backGroundImageFileName = "pics/background.jpg";
	private static ApplicationManager instance =  null;
	public ArrayList<Picture> picList = new ArrayList<Picture>();
	private Map<String,  Picture> pictures;
	public static JFrame f = new JFrame("Image");
	private static int followingActionFlag = 0;
	private int runningTimeCounter = 0;
	private long startTimeStamp = 0;
	
	public ApplicationManager() {
		// TODO Auto-generated constructor stub
		setPictures(new HashMap<String, Picture>());
//		Timer RunningTimer = new Timer();
//		RunningTimer.schedule(new RunningTime(), 0, 1000);
	}

	public void setupFrame() {
		// TODO Auto-generated method stub
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        f.setLayout(null);  
        f.setBounds(0, 0, 500, 500);  
        f.setBackground(new Color(100, 0, 0)); 
        instance.buildUI(f);
        f.setSize(600, 600);
        f.setVisible(true);
	}

	public void creatPicObject(String picname, String picID, String timecondition, int min, int sec)
	{
		TimeConditionType timeConditionType = TimeConditionType.getTimeConditionType(timecondition);
		Picture pic = Picture.makePicture(picname, picID, timeConditionType, min, sec);
		picList.add(pic);
		getPictures().put(picID, pic);
		System.out.println(" print image name in java = " + pic.imageFileName + " w = " + pic.w + " h = " + pic.h);
	}
	
	public void actionConfig(String picID, int desx, int desy)
	{
		Picture pic = getPictures().get(picID);
	}	
	
	public void straightActionConfig(String picID, int desx, int desy, String timecondition, long period, int speed)
	{
		TimeConditionType timeConditionType = TimeConditionType.getTimeConditionType(timecondition);
		
		Picture pic = getPictures().get(picID);
		System.out.println(" action config picid = "+ picID);
		pic.addAction(pic, desx, desy, STRAIGHT, timeConditionType, period, speed);
	}
	
	public void startPosition(String picID, int startx, int starty)
	{
		Picture pic = getPictures().get(picID);
		pic.setStartPosition(startx,starty);
	}
	
	public void speedConfig(String picID, int speed)
	{
		Picture pic = getPictures().get(picID);
	}
	
	public void startTime(String picID, String timeConditionType, int minute, int second)
	{
		Picture pic = getPictures().get(picID);
		pic.setShowTime(timeConditionType, minute, second);
	}
	
	public void buildUI(JFrame f) {

		for(int i = 0; i < picList.size(); i++)
		{
			f.add(picList.get(i));
			Picture p = picList.get(i);
			p.repaint();
		}
	}
    
	/**
	 * @return the instance
	 */
	public static ApplicationManager getAppletInstance()
	{
		if (instance == null) {
			instance = new ApplicationManager();
		}
		return instance;
	}

	public void actionsStart() {
		// TODO Auto-generated method stub
		if (instance == null) {
			return;
		}
		startTimeStamp = System.currentTimeMillis();
		
		System.out.println("Acction start at "+ startTimeStamp);
		
		for(int i = 0; i < instance.picList.size(); i++)
		{
			Picture pic = instance.picList.get(i);
			pic.actionStart(pic.getAcctionList());
		}
	}
	
	
	public void mirrorreflect(String picID, String timecondition, long period)
	{
		Picture pic = getPictures().get(picID);
		int desx = -1;
		int desy = -1;
		TimeConditionType timeConditionType = TimeConditionType.getTimeConditionType(timecondition);
		pic.addAction(pic, desx, desy, MIRROR, timeConditionType, period, 0);
	}
	
	public void jumpActionConfig(String picID, int desX, int desY, String timecondition, long period)
	{
		TimeConditionType timeConditionType = TimeConditionType.getTimeConditionType(timecondition);
		Picture pic = getPictures().get(picID);
		pic.addAction(pic, desX, desY , JUMP, timeConditionType, period, 0);
	}
	
	public void disappearActionConfig(String picID, String timecondition, long period)
	{
		TimeConditionType timeConditionType = TimeConditionType.getTimeConditionType(timecondition);
		Picture pic = getPictures().get(picID);
		pic.addAction(pic, 0, 0 , DISAPPEAR, timeConditionType, period, 0);
	}
	
	public void patrolActionConfig(String picID, int desX, int desY, String timecondition, long period, int speed)
	{
		TimeConditionType timeConditionType = TimeConditionType.getTimeConditionType(timecondition);
		Picture pic = getPictures().get(picID);
		pic.addAction(pic, desX, desY, PATROL, timeConditionType, period, speed);
	}
	
	public void alterbehaviorConfig(String mainPicID, int conditiontype, String condition)
	{
		Picture mainpic = getPictures().get(mainPicID);
		mainpic.addCondition(conditiontype, condition);
		followingActionFlag = 1;
	}

	public void endofalterConfig(String mainPicID)
	{
		followingActionFlag = 0;
	}
	
	public void removeComponet(Component comp)
	{
		f.remove(comp);
	}
	
	public static int getfollowingActionFlag()
	{
		return followingActionFlag;
	}

	public Map<String,  Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Map<String,  Picture> pictures) {
		this.pictures = pictures;
	}
	
	public class RunningTime extends TimerTask
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			runningTimeCounter = runningTimeCounter + 1;
		}
	}
	
	public int getRunningTime()
	{
		return runningTimeCounter;
	}
	
	public long getstartTimeStamp()
	{
		return startTimeStamp;
	}
}
