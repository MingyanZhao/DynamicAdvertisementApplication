package utility;

public enum TimeConditionType {
	
	AFTER, UNTIL, NOW
	;
	
	public static TimeConditionType getTimeConditionType(String timecondition) {
		// TODO Auto-generated method stub
		switch(timecondition)
		{
		case "after":
		case "AFTER":
			return AFTER;
		case "until":
		case "UNTIL":
			return UNTIL;
		case "now":
		case "NOW":
			return NOW;
		}
		return null;
	}
}
