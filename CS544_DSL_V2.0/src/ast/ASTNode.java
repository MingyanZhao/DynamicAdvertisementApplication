package ast;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

import ast.ASTVisitor;

public class ASTNode {

	public enum ASTNodeType {
		PROGRAM, SHOWPICTURE, BEHAVIORS, ONEWAYBEHAVIOR, ALTERNATIVEBEHAVIOR, 
		BASICMOVE, COORDIANTE, STRAIGHT, JUMP, HIT, PATROL,DISAPPEAR,  PICNAME, ID, BOUNDRY, TIMECONDITION,
		TIMESTAMP, TIMEPERIOD, FUTUREBEHAVIOR, AFTERTIMECONDITION, UNTILTIMECONDITION, SPEED, MIRRORREFLECT
	}
	
	public List<ASTNode> children;
	public ASTNodeType nodeType;
	public Token token;
	public ASTNode parent;
	public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
	
	/**
	 * Add a child to this node. By default, this is placed at the rightmost child
	 * @param child
	 */
	public void addChild(ASTNode child)
	{
		children.add(child);
		child.parent = this;
	}
	
	public ASTNode getChild(int i)
	{
		return children.get(i);
	}
	
	/**
	 * @return the number of children
	 */
	public int getChildCount()
	{
		return children.size();
	}
	
	public ASTNode()
	{
		this(null);
	}
	
	public ASTNode(ASTNode parent) 
	{
		children = new ArrayList<ASTNode>();
		this.parent = parent;
		token = null;
	}
	
	@Override
	public String toString()
	{
		return "(" + nodeType + nodeInfo() + ")";
	}
	
	protected String nodeInfo()
	{
		StringBuilder builder = new StringBuilder();
//		builder.append(" type=" + type);
		builder.append(token == null ? "" : ", token=" + token.getText());
//		builder.append(extraInfo());
		for (ASTNode n : children) {
			builder.append(" " + n);
		}
		return builder.toString();
	}
}
