package app;

public class ActionCalculation
{
	float incrX;
	float incrY;
	
	public void straightIncrement(int startX, int startY, int desX, int desY)
	{
		float k;
		if(desX == startX)
		{
			if(desY < startY)
			{
				incrX = 0;
				incrY = -1;
			}
			else
			{
				incrX = 0;
				incrY = 1;
			}
		}
		else
		{
			k = (float)(desY-startY)/(desX-startX);
			if(k < 0)
			{
				k = -k;
			}
			incrX = (float) (1/Math.sqrt(k*k+1));
			incrY = (float) (k/Math.sqrt(k*k+1));
			
			if(desX < startX)
			{
				incrX = 0 - incrX;
			}
			
			if(desY < startY)
			{
				incrY = 0 - incrY;
			}
		}
	}
}