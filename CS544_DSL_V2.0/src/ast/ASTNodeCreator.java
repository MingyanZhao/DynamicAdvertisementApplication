package ast;

import java.util.ArrayList;

import utility.BoundryType;
import utility.TimeConditionType;
import static utility.TimeConditionType.*;
import ast.ASTNodeFactory.*;
import lexparser.*;

public class ASTNodeCreator extends MovingPictureBaseVisitor<ASTNode>{

	public ASTNode visitProgram(MovingPictureParser.ProgramContext ctx)
	{
		String programName = ctx.ID().getText();
		final ProgramNode program = ProgramNode.makeProgramNode(programName);
		for (int i = 2; i < ctx.getChildCount() - 1; i++) {
			ASTNode node = ctx.getChild(i).accept(this);
//			if(node.nodeType == ASTNodeType.SHOWPICTURE || node.nodeType == ASTNodeType.BEHAVIORS)
			program.addChild(node);
		}
		return program;
	}
	
	public ASTNode visitShowpicture(MovingPictureParser.ShowpictureContext ctx)
	{
		PictureNameNode PicNameNode = (PictureNameNode)ctx.getChild(1).accept(this);
		final ShowPictureNode showPicNode = ShowPictureNode.makeShowPictureNode();
		showPicNode.addChild(PicNameNode);
		
		CoordinateNode coordinateNode = (CoordinateNode)ctx.getChild(2).accept(this);
		showPicNode.addChild(coordinateNode);
		
		if(ctx.NAMEED() != null)
		{
			IDNode idnode = IDNode.makeIDNode(ctx.ID().toString());
			showPicNode.addChild(idnode);
		}
		
		if(ctx.timecondition() != null)
		{
			ASTNode timeConditionNode = ctx.timecondition().accept(this);
			showPicNode.addChild(timeConditionNode);
		}
		
		return showPicNode;
	}
	
	public ASTNode visitPicname(MovingPictureParser.PicnameContext ctx)
	{
		String picName = ctx.getChild(1).toString() + "." + ctx.getChild(3).toString();
		final PictureNameNode showPicNode = PictureNameNode.makePictureNameNode(picName);
		
		return showPicNode;
	}
	
	public ASTNode visitCoordinate(MovingPictureParser.CoordinateContext ctx)
	{
		String x = ctx.INTEGER().get(0).getText();
		String y = ctx.INTEGER().get(1).getText();
		CoordinateNode coordiantNode = CoordinateNode.makeCoordinateNode(Integer.parseInt(x), Integer.parseInt(y)); 
		return coordiantNode;
	}
	
	public ASTNode visitOnewaybehavior(MovingPictureParser.OnewaybehaviorContext ctx)	
	{
		OnewayBehaviorNode onewayBehaviorNode = OnewayBehaviorNode.makeOnewayBehaviorNode();
		IDNode idnode = IDNode.makeIDNode(ctx.ID().toString());
		onewayBehaviorNode.addChild(idnode);
		
		ASTNode node = ctx.basicmove().accept(this);
		onewayBehaviorNode.addChild(node);
		
		return onewayBehaviorNode;
	}
	
	public ASTNode visitAlternativebehavior(MovingPictureParser.AlternativebehaviorContext ctx)
	{
		AlternativeBehaviorNode alternativeNode = AlternativeBehaviorNode.makeAlternativeBehaviorNode();
		IDNode idnode = IDNode.makeIDNode(ctx.ID().toString());
		alternativeNode.addChild(idnode);
		
		HitNode hitnode = (HitNode)ctx.hit().accept(this);
		alternativeNode.addChild(hitnode);
		
		if(ctx.basicmove() != null)
		{
			ASTNode node = ctx.basicmove().accept(this);
			alternativeNode.addChild(node);
		}
		else if(ctx.mirrorreflection() != null)
		{
			ASTNode node = ctx.mirrorreflection().accept(this);
			alternativeNode.addChild(node);
		}
		
		return alternativeNode;
	}
	
	public ASTNode visitBasicmove(MovingPictureParser.BasicmoveContext ctx)	
	{
		BasicmoveNode basicmoveNode = BasicmoveNode.makeBasicmoveNode(); 
		for(int i = 0; i < ctx.getChildCount(); i = i + 2)
		{
			ASTNode node = ctx.getChild(i).accept(this);
			basicmoveNode.addChild(node);
		}
		return basicmoveNode;
	}
	
	public ASTNode visitStraight(MovingPictureParser.StraightContext ctx)
	{
		ASTNode coordiantNode = ctx.coordinate().accept(this);
		
		StraightActionNode straightNode = StraightActionNode.makeStraightActionNode();
		straightNode.addChild(coordiantNode);
		
		if(ctx.timecondition() != null)
		{
			ASTNode timeconditionnode = ctx.timecondition().accept(this);
			straightNode.addChild(timeconditionnode);
		}
		
		if(ctx.speed() != null)
		{
			ASTNode speedNode = ctx.speed().accept(this);
			straightNode.addChild(speedNode);
		}
		
		return straightNode;
	}
	
	public ASTNode visitDisappear(MovingPictureParser.DisappearContext ctx)
	{
		DisappearNode disappearNode = DisappearNode.makeDisappearNode();
		
		if(ctx.timecondition() != null)
		{
			ASTNode timeconditionnode = ctx.timecondition().accept(this);
			disappearNode.addChild(timeconditionnode);
		}
		
		return disappearNode;
	}
	
	public ASTNode visitPatrol(MovingPictureParser.PatrolContext ctx)
	{
		ASTNode coordiantNode = ctx.coordinate().accept(this);
		
		PatrolActionNode patrolNode = PatrolActionNode.makePatrolActionNode();
		patrolNode.addChild(coordiantNode);		
		
		if(ctx.timecondition() != null)
		{
			ASTNode timeconditionnode = ctx.timecondition().accept(this);
			patrolNode.addChild(timeconditionnode);
		}
		
		if(ctx.speed() != null)
		{
			ASTNode speedNode = ctx.speed().accept(this);
			patrolNode.addChild(speedNode);
		}
		
		return patrolNode;
	}
	
	public ASTNode visitHit(MovingPictureParser.HitContext ctx)
	{
		HitNode hitNode = HitNode.makeHitNode();
		if(ctx.ID() != null)
		{
			IDNode idnode = IDNode.makeIDNode(ctx.ID().toString());
			hitNode.addChild(idnode);
		}
		else if(ctx.boundry() != null)
		{
			BoundryNode boundryNode = (BoundryNode)ctx.boundry().accept(this);
			hitNode.addChild(boundryNode);
		}
		return hitNode;
	}
	
	public ASTNode visitJump(MovingPictureParser.JumpContext ctx)
	{
		ASTNode coordiantNode = ctx.coordinate().accept(this);
		JumpNode jumpNode = JumpNode.makeJumpNode();
		jumpNode.addChild(coordiantNode);
		
		if(ctx.timecondition() != null)
		{
			ASTNode timeconditionnode = ctx.timecondition().accept(this);
			jumpNode.addChild(timeconditionnode);
		}
				
		return jumpNode;
	}
	
	public ASTNode visitBoundry(MovingPictureParser.BoundryContext ctx)
	{
		BoundryNode boundryNode;
		
		if(ctx.LEFT() != null)
		{
			boundryNode = BoundryNode.makeBoundryNode(BoundryType.LEFT);
		}
		else if (ctx.RIGHT() != null)
		{
			boundryNode = BoundryNode.makeBoundryNode(BoundryType.RIGHT);
		}
		else if (ctx.UP() != null)
		{
			boundryNode = BoundryNode.makeBoundryNode(BoundryType.UP);
		}
		else if (ctx.DOWN() != null)
		{
			boundryNode = BoundryNode.makeBoundryNode(BoundryType.DOWN);
		}
		else
		{
			boundryNode = BoundryNode.makeBoundryNode(BoundryType.ANYBOUNDRY);
		}
		
		return boundryNode;
	}
	
	public ASTNode visitTimecondition(MovingPictureParser.TimeconditionContext ctx)
	{
//		ASTNode timeConditionNode = TimeConditionNode.makeTimeConditionNode(TimeConditionType.getTimeConditionType(ctx.getChild(0).toString()));
//		ASTNode timeNode = ctx.getChild(1).accept(this);
//		timeConditionNode.addChild(timeNode);
		ASTNode timeConditionNode = ctx.getChild(0).accept(this);
		return timeConditionNode;
	}
	
	public ASTNode visitAftertimecondition(MovingPictureParser.AftertimeconditionContext ctx)
	{
		ASTNode aftertimeConditionNode = AfterTimeConditionNode.makeTimeConditionNode(TimeConditionType.getTimeConditionType(ctx.getChild(0).toString()));
		ASTNode timeNode = ctx.getChild(1).accept(this);
		aftertimeConditionNode.addChild(timeNode);
		return aftertimeConditionNode;
	}
	
	public ASTNode visitUntiltimecondition(MovingPictureParser.UntiltimeconditionContext ctx)
	{
		ASTNode untilTimeConditionNode = UntilTimeConditionNode.makeUntilTimeConditionNode(UNTIL);
		ASTNode timeNode = ctx.getChild(1).accept(this);
		untilTimeConditionNode.addChild(timeNode);
		return untilTimeConditionNode;
	}
	
	public ASTNode visitTimestamp(MovingPictureParser.TimestampContext ctx)
	{
		ASTNode timeStampNode = null;
		String timeStamp = ctx.TIMESTAMP().getText();
		System.out.println("timeStamp.substring(1, 3) = " + timeStamp.substring(0, 2) + "  timeStamp.substring(3, 4) = " + timeStamp.substring(3, 5));
		int minute = Integer.parseInt(timeStamp.substring(0, 2));
		int second = Integer.parseInt(timeStamp.substring(3, 5));
		timeStampNode = TimeStampNode.makeTimeStampNode(minute,second);
		return timeStampNode;
	}
	
	public ASTNode visitTimeperiod(MovingPictureParser.TimeperiodContext ctx)
	{
		ASTNode timePeriodNode;
		timePeriodNode = TimePeriodNode.makeTimePeriodNode(
					Integer.parseInt(ctx.INTEGER().getText()));
		return timePeriodNode;
	}
	
//	public ASTNode visitFuturebehavior(MovingPictureParser.FuturebehaviorContext ctx)
//	{
//		ASTNode futureBehaviorNode;
//		futureBehaviorNode = FutureBehaviorNode.makeFutureBehaviorNode();
//		
//		IDNode idnode = IDNode.makeIDNode(ctx.ID().toString());
//		futureBehaviorNode.addChild(idnode);
//		
//		ASTNode basicmovenode = ctx.basicmove().accept(this);
//		futureBehaviorNode.addChild(basicmovenode);
//		
//		ASTNode timeconditionnode = ctx.timecondition().accept(this);
//		futureBehaviorNode.addChild(timeconditionnode);
//		
//		return futureBehaviorNode;
//	}
	
	public ASTNode visitSpeed(MovingPictureParser.SpeedContext ctx)
	{
		ASTNode speedNode;
		speedNode = SpeedNode.makeSpeedNode(
					Integer.parseInt(ctx.INTEGER().getText()));
		return speedNode;
	}
	
	public ASTNode visitMirrorreflection(MovingPictureParser.MirrorreflectionContext ctx)
	{
		ASTNode mirrorNode;
		mirrorNode = MirrorReflectionNode.makeMirrorReflectionNode();
		return mirrorNode;
	}
}
