package ast;

import static ast.ASTNode.ASTNodeType.*;

import java.sql.Time;

import utility.BoundryType;
import utility.TimeConditionType;

public class ASTNodeFactory {

	//--------------------- ProgramNode -----------------------
	/**
	 * program -> ProgramNode(ASTNode+, programName)
	 */
	public static class ProgramNode extends ASTNode
	{
		public final String programName;
		
		public ProgramNode(String programName) 
		{ 
			super(); 
			nodeType = PROGRAM;
			this.programName = programName; 
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		/**
		 * Factory method for the ProgramNode 
		 * @param programName
		 * @return the new node with no parent.
		 */
		public static ProgramNode makeProgramNode(String programName)
		{
			return new ProgramNode(programName);
		}
	}
	
	//--------------------- ShowPictureNode -----------------------
	public static class ShowPictureNode extends ASTNode
	{
		
		public ShowPictureNode() 
		{ 
			super(); 
			nodeType = SHOWPICTURE;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static ShowPictureNode makeShowPictureNode()
		{
			return new ShowPictureNode();
		}

		public String getPicName() {
			// TODO Auto-generated method stub
			String picname = ((PictureNameNode)getChild(0)).pictureName;
			return picname;
		}

		public CoordinateNode getcoordiante() {
			// TODO Auto-generated method stub
			return (CoordinateNode)getChild(1);
		}

		public String getPicID() {
			// TODO Auto-generated method stub
			if(getChildCount() <= 2)
				return null;
			else
			{
				return ((IDNode)getChild(2)).IDName;
			}
		}

		public ASTNode getTimeCondition() {
			// TODO Auto-generated method stub
			if(getChildCount() <= 3)
				return null;
			else
			{
				return getChild(3);
			}
		}
	}
	
	//--------------------- ShowPictureNode -----------------------
	public static class IDNode extends ASTNode
	{
		public final String IDName;
		
		public IDNode(String IDName) 
		{ 
			super(); 
			nodeType = ID;
			this.IDName = IDName; 
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static IDNode makeIDNode(String IDName)
		{
			return new IDNode(IDName);
		}
	}
	
	//--------------------- ShowPictureNode -----------------------
	public static class CoordinateNode extends ASTNode
	{
		public final int x;
		public final int y;
		
		public CoordinateNode(int coordianteX, int coordinateY) 
		{ 
			super();
			this.x = coordianteX;
			this.y = coordinateY;
			nodeType = COORDIANTE;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static CoordinateNode makeCoordinateNode(int x, int y)
		{
			return new CoordinateNode(x,y);
		}
	}
	
	//--------------------- AlternativeBehaviorNode -----------------------
	public static class AlternativeBehaviorNode extends ASTNode
	{
		
		public AlternativeBehaviorNode() 
		{ 
			super();
			nodeType = ALTERNATIVEBEHAVIOR;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static AlternativeBehaviorNode makeAlternativeBehaviorNode()
		{
			return new AlternativeBehaviorNode();
		}

		public CoordinateNode getcoordiante() {
			// TODO Auto-generated method stub
			return (CoordinateNode)getChild(0);
		}
		
		public IDNode getID() {
			// TODO Auto-generated method stub
			return (IDNode)getChild(0);
		}

		public ASTNode getcondition() {
			// TODO Auto-generated method stub
			return getChild(1);
		}

		public ASTNode getFollowing() {
			// TODO Auto-generated method stub
			return getChild(2);
		}
	}
	
	//--------------------- OnewayBehaviorNode -----------------------
	public static class OnewayBehaviorNode extends ASTNode
	{
		
		public OnewayBehaviorNode() 
		{ 
			super();
			nodeType = ONEWAYBEHAVIOR;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static OnewayBehaviorNode makeOnewayBehaviorNode()
		{
			return new OnewayBehaviorNode();
		}

		public IDNode getID() {
			// TODO Auto-generated method stub
			return (IDNode)getChild(0);
		}

		public BasicmoveNode getMoveNode() {
			// TODO Auto-generated method stub
			return (BasicmoveNode)getChild(1);
		}
	}
	
	//--------------------- OnewayBehaviorNode -----------------------
	public static class BasicmoveNode extends ASTNode
	{
		
		public BasicmoveNode() 
		{ 
			super();
			nodeType = BASICMOVE;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static BasicmoveNode makeBasicmoveNode()
		{
			return new BasicmoveNode();
		}
	}
	
	//--------------------- StraightActionNode -----------------------
	public static class StraightActionNode extends ASTNode
	{
		
		public StraightActionNode() 
		{ 
			super();
			nodeType = STRAIGHT;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static StraightActionNode makeStraightActionNode()
		{
			return new StraightActionNode();
		}
		
		public CoordinateNode getcoordiante() {
			// TODO Auto-generated method stub
			return (CoordinateNode)getChild(0);
		}

		public ASTNode getTimeCondition() {
			// TODO Auto-generated method stub
			if(getChildCount() < 2)
			{
				return null;
			}
			return getChild(1);
		}

		public ASTNode getSpeed() {
			// TODO Auto-generated method stub
			if(getChildCount() < 3)
			{
				return null;
			}
			return getChild(2);
		}
	}
	
	//--------------------- StraightActionNode -----------------------
	public static class JumpNode extends ASTNode
	{
		public JumpNode() 
		{ 
			super();
			nodeType = JUMP;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static JumpNode makeJumpNode()
		{
			return new JumpNode();
		}

		public CoordinateNode getcoordiante() {
			// TODO Auto-generated method stub
			return (CoordinateNode)getChild(0);
		}
		
		public ASTNode getTimeCondition() {
			// TODO Auto-generated method stub
			if(getChildCount() < 2)
			{
				return null;
			}
			return getChild(1);
		}
	}

	
	//--------------------- HitNode -----------------------
	public static class HitNode extends ASTNode
	{
		public HitNode() 
		{ 
			super();
			nodeType = HIT;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static HitNode makeHitNode()
		{
			return new HitNode();
		}

		public IDNode getID() {
			// TODO Auto-generated method stub
			return (IDNode)getChild(0);
		}

		public BoundryNode getBoundry() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	//--------------------- PatrolActionNode -----------------------
	public static class PatrolActionNode extends ASTNode
	{
		
		public PatrolActionNode() 
		{ 
			super();
			nodeType = PATROL;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static PatrolActionNode makePatrolActionNode()
		{
			return new PatrolActionNode();
		}

		public CoordinateNode getcoordiante() {
			// TODO Auto-generated method stub
			return (CoordinateNode)getChild(0);
		}
		
		public ASTNode getTimeCondition() {
			// TODO Auto-generated method stub
			if(getChildCount() < 2)
			{
				return null;
			}
			return getChild(1);
		}

		public ASTNode getSpeed() {
			// TODO Auto-generated method stub
			if(getChildCount() < 3)
			{
				return null;
			}
			return getChild(2);
		}
	}
	
	//--------------------- PatrolActionNode -----------------------
	public static class DisappearNode extends ASTNode
	{
		
		public DisappearNode() 
		{ 
			super();
			nodeType = DISAPPEAR;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static DisappearNode makeDisappearNode()
		{
			return new DisappearNode();
		}
		
		public ASTNode getTimeCondition() {
			// TODO Auto-generated method stub
			if(getChildCount() < 1)
			{
				return null;
			}
			return getChild(0);
		}
	}
	
	//--------------------- PictureNameNode -----------------------
	public static class PictureNameNode extends ASTNode
	{
		public final String pictureName;
		
		public PictureNameNode(String picname) 
		{ 
			super();
			nodeType = PICNAME;
			pictureName = picname;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static PictureNameNode makePictureNameNode(String picname)
		{
			return new PictureNameNode(picname);
		}
	}
	
	//--------------------- PictureNameNode -----------------------
	public static class BoundryNode extends ASTNode
	{
		
//		public static enum BoundryType
//		{
//			LEFT, RIGHT, UP, DOWN , ANY
//		}
		
		private BoundryType boudryType;
		
		public BoundryNode(BoundryType boundry) 
		{ 
			super();
			nodeType = BOUNDRY;
			boudryType = boundry;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static BoundryNode makeBoundryNode(BoundryType boundryType)
		{
			return new BoundryNode(boundryType);
		}
		
		public BoundryType getBoundryType()
		{
			return boudryType;
		}
	}
	
//	//--------------------- PictureNameNode -----------------------
//	public static class TimeConditionNode extends ASTNode
//	{
//		private TimeConditionType timeConditionType;
//		
//		public TimeConditionNode(TimeConditionType type) 
//		{ 
//			super();
//			nodeType = TIMECONDITION;
//			setTimeConditionType(type);
//		}
//		
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
//			
//		public static TimeConditionNode makeTimeConditionNode(TimeConditionType type)
//		{
//			return new TimeConditionNode(type);
//		}
//
//		public TimeConditionType getTimeConditionType() {
//			return timeConditionType;
//		}
//
//		public void setTimeConditionType(TimeConditionType timeConditionType) {
//			this.timeConditionType = timeConditionType;
//		}
//
//		public ASTNode getTime() {
//			// TODO Auto-generated method stub
//			return getChild(0);
//		}
//	}
	
	//--------------------- PictureNameNode -----------------------
	public static class AfterTimeConditionNode extends ASTNode
	{
		private TimeConditionType timeConditionType;
		
		public AfterTimeConditionNode(TimeConditionType type) 
		{ 
			super();
			nodeType = AFTERTIMECONDITION;
			setTimeConditionType(type);
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static AfterTimeConditionNode makeTimeConditionNode(TimeConditionType type)
		{
			return new AfterTimeConditionNode(type);
		}

		public TimeConditionType getTimeConditionType() {
			return timeConditionType;
		}

		public void setTimeConditionType(TimeConditionType timeConditionType) {
			this.timeConditionType = timeConditionType;
		}

		public ASTNode getTime() {
			// TODO Auto-generated method stub
			return getChild(0);
		}
	}
	//--------------------- PictureNameNode -----------------------
	public static class UntilTimeConditionNode extends ASTNode
	{
		private TimeConditionType timeConditionType;
		
		public UntilTimeConditionNode(TimeConditionType type) 
		{ 
			super();
			nodeType = UNTILTIMECONDITION;
			setTimeConditionType(type);
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static UntilTimeConditionNode makeUntilTimeConditionNode(TimeConditionType type)
		{
			return new UntilTimeConditionNode(type);
		}

		public TimeConditionType getTimeConditionType() {
			return timeConditionType;
		}

		public void setTimeConditionType(TimeConditionType timeConditionType) {
			this.timeConditionType = timeConditionType;
		}

		public ASTNode getTime() {
			// TODO Auto-generated method stub
			return getChild(0);
		}
	}
	
	//--------------------- PictureNameNode -----------------------
	public static class TimeStampNode extends ASTNode
	{
		TimeConditionType timeConditionType;
		private int minute;
		private int second;
		
		public TimeStampNode(int minute2, int second2) {
			// TODO Auto-generated constructor stub
			super();
			nodeType = TIMESTAMP;
			minute = minute2;
			second = second2;
		}

		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static TimeStampNode makeTimeStampNode(int minute2, int second2)
		{
			return new TimeStampNode(minute2, second2);
		}

		public int getMinute() {
			return minute;
		}

		public void setMinute(int min) {
			this.minute = min;
		}

		public int getSecond() {
			return second;
		}

		public void setSeccond(int sec) {
			this.second = sec;
		}


	}
	
	//--------------------- PictureNameNode -----------------------
	public static class TimePeriodNode extends ASTNode
	{
		TimeConditionType timeConditionType;
		private int period;
		
		public TimePeriodNode(int sec){
			// TODO Auto-generated constructor stub
			super();
			nodeType = TIMEPERIOD;
			setPeriod(sec);
		}

		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static TimePeriodNode makeTimePeriodNode(int sec)
		{
			return new TimePeriodNode(sec);
		}

		public int getPeriod() {
			return period;
		}

		public void setPeriod(int period) {
			this.period = period;
		}

	}
	
	public static class FutureBehaviorNode extends ASTNode
	{
		
		public FutureBehaviorNode()
		{
			super();
			nodeType = FUTUREBEHAVIOR;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static FutureBehaviorNode makeFutureBehaviorNode()
		{
			return new FutureBehaviorNode();
		}

		public IDNode getID() {
			// TODO Auto-generated method stub
			return (IDNode)getChild(0);
		}

		public BasicmoveNode getBasicMoveNode() {
			// TODO Auto-generated method stub
			return (BasicmoveNode)getChild(1);
		}
		
		public ASTNode getTimeConditionNode()
		{
			return getChild(2);
		}
	}
	
	//--------------------- PatrolActionNode -----------------------
	public static class SpeedNode extends ASTNode
	{
		private int speed;
		
		public SpeedNode(int s) 
		{ 
			super();
			nodeType = SPEED;
			speed = s;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static SpeedNode makeSpeedNode(int speed)
		{
			return new SpeedNode(speed);
		}
		
		public int getSpeed() {
			return speed;
		}

		public void setSpeed(int speed) {
			this.speed = speed;
		}
	}
	
	//--------------------- PatrolActionNode -----------------------
	public static class MirrorReflectionNode extends ASTNode
	{
		public MirrorReflectionNode() 
		{ 
			super();
			nodeType = MIRRORREFLECT;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		public static MirrorReflectionNode makeMirrorReflectionNode()
		{
			return new MirrorReflectionNode();
		}
		
		public ASTNode getTimeConditionNode()
		{
			if(getChildCount() < 1)
			{
				return null;
			}
			else
			{
				return getChild(0);
			}
		}

		public ASTNode getBasicMoveNode() {
			if(getChildCount() == 0)
			{
				return null;
			}
			else if (getChildCount() == 1)
			{
				if(getChild(0).nodeType == TIMECONDITION)
				{
					return null;
				}
				else
				{
					return getChild(0);
				}
			}
			else if(getChildCount() > 1)
			{
				return getChild(1);
			}
			return null;
		}
	}
}
