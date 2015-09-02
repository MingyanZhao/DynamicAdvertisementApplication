package utility;

import app.ApplicationMain;

public class DebugOutput {
	
	public static void Debug(String s)
	{
		if(ApplicationMain.Debug == true)
		{
			System.out.println(s);
		}
	}
}
