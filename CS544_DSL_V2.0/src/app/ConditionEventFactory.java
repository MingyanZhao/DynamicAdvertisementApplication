package app;

import static app.ConditionEvent.ConditionType.*;
import utility.BoundryType;
import static utility.BoundryType.*;

public class ConditionEventFactory {
	
	public static class Hit extends ConditionEvent
	{
		Picture mainPic;
		Picture subPic = null;
		BoundryType boudnryCondition = null;
		
		public Hit(Picture mainp, Picture subp)
		{
			conditionType = HIT;
			mainPic = mainp;
			subPic = subp;
		}
		
		public Hit(Picture mainp, BoundryType boundryType) {
			// TODO Auto-generated constructor stub
			conditionType = HIT;
			mainPic = mainp;
			boudnryCondition = boundryType;
		}

		public boolean judge()
		{
			BoundryType hitSide;
			
			if(subPic != null)
			{
				if((mainPic.cxint > subPic.cxint + subPic.w)
					|| (mainPic.cxint + mainPic.w < subPic.cxint))
					return false;
				else if ((mainPic.cyint > subPic.cyint + subPic.h)
						|| (mainPic.cyint + mainPic.h < subPic.cyint))
					return false;
				else
				{
					if((mainPic.cxint + mainPic.w > subPic.cxint + 1)
							|| (mainPic.cxint + mainPic.w > subPic.cxint -1))
					{
						mainPic.hitSide = RIGHT;
					}
					else if((mainPic.cxint < subPic.cxint + subPic.cxint + 1)
							|| (mainPic.cxint < subPic.cxint + subPic.cxint - 1))
					{
						mainPic.hitSide = LEFT;
					}
					else if((mainPic.cyint + mainPic.h < subPic.cyint +1 )
							|| (mainPic.cyint + mainPic.h < subPic.cyint -1))
					{
						mainPic.hitSide = DOWN;
					}
					else if((mainPic.cyint > subPic.cyint + subPic.h + 1)
							|| (mainPic.cyint > subPic.cyint + subPic.h - 1))
					{
						mainPic.hitSide = UP;
					}
					
					return true;
				}
			}
			else if(boudnryCondition != null)
			{
				switch(boudnryCondition)
				{
				case LEFT:
					if(mainPic.cxint <= 0)
					{
						mainPic.hitSide = LEFT;
						return true;
					}
					break;
				case RIGHT:
					if(mainPic.cxint + mainPic.w >= 600)
					{
						mainPic.hitSide = RIGHT;
						return true;
					}
					break;
				case UP:
					if(mainPic.cyint <= 0)
					{
						mainPic.hitSide = UP;
						return true;
					}
					break;
				case DOWN:
					if(mainPic.cyint + mainPic.h >= 600)
					{
						mainPic.hitSide = DOWN;
						return true;
					}
					break;
				case ANYBOUNDRY:
					if(mainPic.cxint <= 0)
					{
						mainPic.hitSide = LEFT;
						return true;
					}
					else if(mainPic.cxint + mainPic.w >= 600)
					{
						mainPic.hitSide = RIGHT;
						return true;
					}
					else if (mainPic.cyint <= 0)
					{
						mainPic.hitSide = UP;
						return true;
					}
					else if (mainPic.cyint + mainPic.h >= 600)
					{
						mainPic.hitSide = DOWN;
						return true;
					}
					break;
				}
			}
			
			return false;
			
		}
		
		public static Hit makeHitCondition(Picture mainp, Picture subp)
		{
			return new Hit(mainp, subp);
		}
		
		public static Hit makeHitCondition(Picture mainp, BoundryType boundryType) {
			// TODO Auto-generated method stub
			return  new Hit(mainp, boundryType);
		}
	}
}
