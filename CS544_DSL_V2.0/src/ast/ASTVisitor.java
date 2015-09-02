package ast;

import ast.ASTNodeFactory.*;


public class ASTVisitor<T> {

	
	public T visit(ASTNode node) { return visitChildren(node); }

	public T visit(ProgramNode node) { return visitChildren(node); }
	public T visit(ShowPictureNode node) { return visitChildren(node); }
	public T visit(PictureNameNode node) { return visitChildren(node);}
	public T visit(CoordinateNode node) { return visitChildren(node);}
	public T visit(BasicmoveNode node) { return visitChildren(node);}
	public T visit(StraightActionNode node) { return visitChildren(node);}
	public T visit(OnewayBehaviorNode node)  { return visitChildren(node);}
	public T visit(DisappearNode node)  { return visitChildren(node);}
	public T visit(JumpNode node) {return visitChildren(node);}
	public T visit(PatrolActionNode node) {return visitChildren(node);}
	public T visit(AlternativeBehaviorNode node)  {return visitChildren(node);}
	public T visit(HitNode node) {return visitChildren(node);}
//	public T visit(TimeConditionNode node) {return visitChildren(node);}
	public T visit(AfterTimeConditionNode node) {return visitChildren(node);}
	public T visit(UntilTimeConditionNode node) {return visitChildren(node);}
	public T visit(TimeStampNode node) {return visitChildren(node);}
	public T visit(TimePeriodNode node) {return visitChildren(node);}
	public T visit(FutureBehaviorNode node) {return visitChildren(node);}
	public T visit(SpeedNode node) {return visitChildren(node);}
	public T visit(MirrorReflectionNode node) {return visitChildren(node);}
	
	public T visitChildren(ASTNode node) 
	{
		if(node != null)
		{
			for (ASTNode child : node.children) {
				child.accept(this);
			}
		}
		return null;
	}
}
