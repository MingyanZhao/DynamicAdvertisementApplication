package gen;

import java.util.Stack;

import ast.ASTNodeFactory.*;
import ast.ASTNode;
import ast.ASTVisitor;
import static ast.ASTNode.ASTNodeType.*;

public class RubyCodeGenerator extends ASTVisitor<String>{

	public String code="";
	public String programName;
	private Stack<String> picidStact = new Stack<String>();
	
	public String visit(ProgramNode programNode)
	{
		programName = programNode.programName;
		for(int i = 0; i < programNode.getChildCount(); i++)
		{
			programNode.getChild(i).accept(this);
		}
		
		return code;
	}
	
	public String visit(ShowPictureNode showPicNode)
	{
		writeCode("show ");
		String picName = showPicNode.getPicName();
		String picID = showPicNode.getPicID();
		writeCode("'" +picName+ "'");
		writeParaSep();
		
		CoordinateNode coordinateNode = showPicNode.getcoordiante();
		coordinateNode.accept(this);
		writeParaSep();
		
		writeCode("'"+ picID +"'");
		writeParaSep();
		
		if(showPicNode.getTimeCondition() == null)
		{
			writeCodeNowTimeCondition();
		}
		else
		{
			showPicNode.getTimeCondition().accept(this);
		}
		
		writeNewline();
		return null;
	}
	
	private void writeParaSep() {
		// TODO Auto-generated method stub
		code = code + ","; 
	}

	public String visit(CoordinateNode coordinateNode)
	{
		writeCode(coordinateNode.x + ","+ coordinateNode.y);
		return null;
	}

	public String visit(OnewayBehaviorNode onewayBehaviorNode)
	{
		picidStact.push(onewayBehaviorNode.getID().IDName);
		onewayBehaviorNode.getMoveNode().accept(this);
		picidStact.pop();
		return null;
	}
	
	public String visit(BasicmoveNode basicMoveNode)
	{
		for(int i = 0; i < basicMoveNode.getChildCount(); i++)
		{
			basicMoveNode.getChild(i).accept(this);
//			writeNewline();
		}
		return null;
	}
	
	public String visit(StraightActionNode staightActionNode)
	{
		String picID = picidStact.peek();
		writeCode("moveto " + "'" + picID +"'" + ",");
		staightActionNode.getcoordiante().accept(this);
		writeParaSep();
		if(staightActionNode.getTimeCondition() != null)
		{
			staightActionNode.getTimeCondition().accept(this);
		}
		else
		{
			writeCodeNowTimeCondition();
		}
		
		if(staightActionNode.getSpeed() != null)
		{
			staightActionNode.getSpeed().accept(this);
		}
		else
		{
			writeSpeed();
		}
		
		writeNewline();
		return null;
	}
	
	private void writeSpeed() {
		writeParaSep();
		writeCode("0");
	}

	public String visit(DisappearNode disappearNode)
	{
		String picID = picidStact.peek();
		writeCode("disappear " + "'" + picID + "'");
		writeParaSep();
		if(disappearNode.getTimeCondition() != null)
		{
			disappearNode.getTimeCondition().accept(this);
		}
		else
		{
			writeCodeNowTimeCondition();
		}
		writeNewline();
		return null;
	}

	private void writeCodeNowTimeCondition() {
		writeCode("'" + "now" + "'");
		writeParaSep();
		writeCode("0");
		writeParaSep();
		writeCode("0");
	}
	
	public String visit(JumpNode jumpNode)
	{
		String picID = picidStact.peek();
		writeCode("jump " + "'" + picID + "'");
		writeParaSep();
		jumpNode.getcoordiante().accept(this);
		writeParaSep();
		if(jumpNode.getTimeCondition() != null)
		{
			jumpNode.getTimeCondition().accept(this);
		}
		else
		{
			writeCodeNowTimeCondition();
		}
		
		writeNewline();
		return null;
	}	
	
	public String visit(PatrolActionNode patrolNode)
	{
		String picID = picidStact.peek();
		writeCode("patrol " + "'" + picID + "'");
		writeParaSep();
		patrolNode.getcoordiante().accept(this);
		writeParaSep();
		if(patrolNode.getTimeCondition() != null)
		{
			patrolNode.getTimeCondition().accept(this);
		}
		else
		{
			writeCodeNowTimeCondition();
		}
		
		if(patrolNode.getSpeed() != null)
		{
			patrolNode.getSpeed().accept(this);
		}
		else
		{
			writeSpeed();
		}
		
		writeNewline();
		return null;
	}
	
	public String visit(AlternativeBehaviorNode alternativeNode)
	{
		IDNode idnode = alternativeNode.getID();
		picidStact.push(idnode.IDName);
		writeCode("alter " + "'" + idnode.IDName + "'");
		writeParaSep();
		alternativeNode.getcondition().accept(this);
		writeNewline();
		alternativeNode.getFollowing().accept(this);
		writeCode("endofalter " + "'" + idnode.IDName + "'");
		writeNewline();
		picidStact.pop();
		return null;
	}
	
	public String visit(HitNode hitNode)
	{
		writeCode("$hit");
		writeParaSep();
		ASTNode child = hitNode.getChild(0);
		if(child.nodeType == ID)
		{
			IDNode idnode = hitNode.getID();
			writeCode ("'" + idnode.IDName + "'");
		}
		else if(child.nodeType == BOUNDRY)
		{
			BoundryNode boundrynode = (BoundryNode)child;
			writeCode("'"+boundrynode.getBoundryType().toString()+"'");
		}
		
		return null;
	}
	
//	public String visit(TimeConditionNode timeConditionNode)
//	{
//		writeCode("'"+ timeConditionNode.getTimeConditionType().toString() + "'");
//		writeParaSep();
//		timeConditionNode.getTime().accept(this);
//		return null;
//	}
	
	public String visit(AfterTimeConditionNode afterTimeConditionNode)
	{
		writeCode("'"+ afterTimeConditionNode.getTimeConditionType().toString() + "'");
		writeParaSep();
		afterTimeConditionNode.getTime().accept(this);
		return null;
	}
	
	public String visit(UntilTimeConditionNode untilTimeConditionNode)
	{
		writeCode("'"+ untilTimeConditionNode.getTimeConditionType().toString() + "'");
		writeParaSep();
		untilTimeConditionNode.getTime().accept(this);
		return null;
	}
	
	public String visit(TimeStampNode timeStampNode)
	{
		int minute = timeStampNode.getMinute();
		int second = timeStampNode.getSecond();
		writeCode(minute);
		writeParaSep();
		writeCode(second);
		return null;
	}
	
	public String visit(TimePeriodNode timePeriodNode)
	{
		int second = timePeriodNode.getPeriod();
		writeCode(0);
		writeParaSep();
		writeCode(second);
		return null;
	}
	
	public String visit(SpeedNode speedNode)
	{
		int speed = speedNode.getSpeed();
		writeParaSep();
		writeCode(speed);
		return null;
	}
	
	public String visit(FutureBehaviorNode futureBehaviorNode)
	{
		picidStact.push(futureBehaviorNode.getID().IDName);

		writeNewline();
		futureBehaviorNode.getBasicMoveNode().accept(this);
		
		writeCode("futureconfig ");
		futureBehaviorNode.getTimeConditionNode().accept(this);
		
		picidStact.pop();
		return null;
	}
	
	public String visit(MirrorReflectionNode mirrorReflectionNode)
	{
		String picID = picidStact.peek();
		writeCode("mirrorreflect ");
		writeCode("'" + picID +"'");
		writeParaSep();
		if(mirrorReflectionNode.getTimeConditionNode() != null)
		{
			mirrorReflectionNode.getTimeConditionNode().accept(this);
		}
		else
		{
			writeCodeNowTimeCondition();
		}
		
		if(mirrorReflectionNode.getBasicMoveNode() != null)
		{
			mirrorReflectionNode.getBasicMoveNode().accept(this);
		}
		
		
		writeNewline();
		return null;
	}
	
	private void writeNewline() {
		// TODO Auto-generated method stub
		code = code + "\n"; 
	}

	private String writeCode(String string) {
		// TODO Auto-generated method stub
		code = code + string;
		return null;
	}
	
	private String writeCode(int intvalue)
	{
		code = code + Integer.toString(intvalue);
		return null;
	}
}
