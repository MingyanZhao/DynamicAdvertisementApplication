package utility;

public enum BoundryType
{
	LEFT, RIGHT, UP, DOWN , ANYBOUNDRY;

	public static BoundryType getBoundryType(String condition) {
		// TODO Auto-generated method stub
		switch(condition)
		{
		case "ANYBOUNDRY":
			return ANYBOUNDRY;
		case "LEFT":
			return LEFT;
			
		case "RIGHT":
			return RIGHT;
			
		case "UP":
			return UP;
			
		case "DOWN":
			return DOWN;
		}
		
		return null;
	}
}
