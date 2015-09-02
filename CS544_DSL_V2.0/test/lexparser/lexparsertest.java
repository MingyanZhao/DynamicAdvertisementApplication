package lexparser;

import static org.junit.Assert.assertTrue;
import gen.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.gui.TreeViewer;
import org.junit.Test;

import app.ApplicationMain;
import ast.*;
import utility.LexparserFactory;

public class lexparsertest
{
	private ParserRuleContext tree;
	private MovingPictureParser parser;
	private ASTNodeCreator creator;
	private ASTNode ast;
	private String codeString;
	
	@Test
	public void minimalprogram()
	{
		String s = " show [background.jpg] at (0,0)";
//		doParse(s);
//		showTree();
		doCodeGen(s);
	}
	
	@Test
	public void minimalprogram1()
	{
		String s = " show [panda.jpg] at (100,100) named p1 "
				+ " p1 move to (200,100) then move to (200,200) then move to (100,200) then disappear ";
//		doParse(s);
//		showTree();
		doCodeGen(s);
	}
	
	@Test
	public void twopicprogram()
	{
		String s = " show [monkey.jpg] at (100,100) named p1 "
				+ " show [rabit.jpg] at (500,500) named p2 "
				+ " p1 move to (200,100) then move to (200,200) then move to (100,200) then disappear"
				+ " p2 move to (200,100) then move to (200,200) then move to (100,200) "
				;
//		doParse(s);
//		showTree();
		doCodeGen(s);
	}
	
	@Test
	public void jumptest()
	{
		String s = " show [monkey.jpg] at (100,100) named p1 "
				+ " p1 move to (200,100) then jump to (400,400)"
				;
//		doParse(s);
//		showTree();
		doCodeGen(s);
	}
	
	@Test
	public void onepicprogram()
	{
		String s = 
				" show [panda.jpg] at (500,500) named p2 "
				+ " p2 move to (200,100) then move to (200,200) then move to (100,200) "
				;
		doCodeGen(s);
	}
	
	@Test
	public void patroltest()
	{
		String s = 
				" show [rabit.jpg] at (100,100) named p1 "
				+ " p1 move to (300,100) then patrol between (300,500)"
				;
		doCodeGen(s);
	}
	
	@Test
	public void mutilcomponent()
	{
		String s = 
				  " show [panda3.jpg] at (100,100) named p1 "
				+ " show [monkey1.jpg] at (800,800) named p2 "
				+ " p1 move to (300,100) then patrol between (500,100) "
				+ " p2 move to (300,100) then patrol between (200,150) "
				+ " if p1 hit p2 , move to (400, 220) then jump to (400, 500)  "
				;
		doCodeGen(s);
	}
	
	@Test
	public void hitboundrytest()
	{
		String s = 
				" show [panda2.jpg] at (100,100) named p1 "
				+ " p1 move to (1000,200) "
				+ " if p1 hit right, move to (200,800) "
				+ " if p1 hit down, move to (200,200) "
				;
		doCodeGen(s);
	}
	
	@Test
	public void mirrortest()
	{
		String s = 
				" show [panda2.jpg] at (100,100) named p1 "
				+ " show [monkey1.jpg] at (800,800) named p2 "
				+ " show [rabit.jpg] at (400,400) named p3"
				+ " p1 move to (800,300) "
				+ " p2 move to (300,100) then patrol between (500,100) "
				+ " p3 move to (300,100) then patrol between (200,150) "
				+ " if p1 hit boundry, mirror reflection "
				+ " if p2 hit p3, move to (400,220) then jump to (100,100) "
				;
//		doParse(s);
//		showTree();
		doCodeGen(s);
	}
	
	@Test
	public void timeconditiontest()
	{
		String s = 
				" show [rabit.gif] at (100,100) named p1 after 00:10 "
				;
		doCodeGen(s);
	}
	
	@Test
	public void futurenbehaviortest()
	{
		String s = 
				" show [picture1.jpg] at (100,100) named p1"
				+ " p1 move to (300,300) after 00:05 "
				;
//		doParse(s);
//		showTree();
		doCodeGen(s);
	}
	
	@Test
	public void untilbehaviortest()
	{
		String s = 
				" show [panda2.jpg] at (100,100) named p1"
				+ " p1 patrol between (300,300) until 00:05 "
				;
//		doParse(s);
//		showTree();
		doCodeGen(s);
	}
	
	@Test
	public void speedconfiguretest()
	{
		String s = 
				" show [panda2.jpg] at (100,100) named p1"
				+ " p1 patrol between (300,300) until 00:05 with speed 50 "
				;
//		doParse(s);
//		showTree();
		doCodeGen(s);
	}
	
	
	@Test
	public void chess()
	{
		String s = 
				  "show [brook1.jpg] at (0,0) named brook1 "
				+ "show [bknight1.jpg] at (55,0) named bknight1 "
				+ "show [bbishop1.jpg] at (110,0) named bbishop1 "
				+ "show [bqueen.jpg] at (165,0) named bqueen "
				+ "show [bking.jpg] at (220,0) named bking "
				+ "show [bbishop2.jpg] at (275,0) named bbishop2 "
				+ "show [bknight2.jpg] at (330,0) named bknight2 "
				+ "show [brook2.jpg] at (385,0) named brook2 "
				+ "show [bpawn1.jpg] at (0,55) named bpawn0 "
				+ "show [bpawn2.jpg] at (55,55) named bpawn1 "
				+ "show [bpawn1.jpg] at (110,55) named bpawn2 "
				+ "show [bpawn2.jpg] at (165,55) named bpawn3 "
				+ "show [bpawn1.jpg] at (220,55)  named bpawn4 "
				+ "show [bpawn2.jpg] at (275,55)  named bpawn5 "
				+ "show [bpawn1.jpg] at (330,55)  named bpawn6 "
				+ "show [bpawn2.jpg] at (385,55)  named bpawn7 "
				+ "show [wrook1.jpg] at (0,385) named wrook1 "
				+ "show [wknight1.jpg] at (55,385) named wknight1 "
				+ "show [wbishop1.jpg] at (110,385) named wbishop1 "
				+ "show [wqueen.jpg] at (165,385) named wqueen "
				+ "show [wking.jpg] at (220,385) named wking "
				+ "show [wbishop2.jpg] at (275,385) named wbishop2 "
				+ "show [wknight2.jpg] at (330,385) named wknight2 "
				+ "show [wrook2.jpg] at (385,385) named wrook2 "
				+ "show [wpawn1.jpg] at (0,330) named wpawn0 "
				+ "show [wpawn2.jpg] at (55,330) named wpawn1 "
				+ "show [wpawn1.jpg] at (110,330) named wpawn2 "
				+ "show [wpawn2.jpg] at (165,330) named wpawn3 "
				+ "show [wpawn1.jpg] at (220,330) named wpawn4 "
				+ "show [wpawn2.jpg] at (275,330) named wpawn5 "
				+ "show [wpawn1.jpg] at (330,330) named wpawn6 "
				+ "show [wpawn2.jpg] at (385,330) named wpawn7 "
				+ "show [440440.jpg] at (0,0) "
				+ "wpawn4 move to (220,220) after 00:02 with speed 60 "
				+ "bpawn3 move to (165,165) after 00:04 with speed 20 "
				+ "wpawn4 move to (165,165) after 00:05 with speed 20 "
				+ "bqueen move to (165,165) after 00:08 with speed 20 "
				+ "wbishop2 move to (165,275) after 00:10 with speed 30 "
				+ "bqueen move to (330,330) after 00:12 with speed 30 "
				+ "bpawn3 disappear after 00:06 "
				+ "wpawn4 disappear after 00:09 "
				+ "wpawn6 disappear after 00:13 "
				;
//		doParse(s);
//		showTree();
		doCodeGen(s);
	}
	
	
	
	
	
	/**
	 * This method performs the parse. If you want to see what the tree looks like, use
	 * 		<br><code>System.out.println(tree.toStringTree());<code></br>
	 * after calling this method.
	 * @param inputText the text to parse
	 */
	private void doParse(String inputText)
	{
		makeParser("program test "+ inputText);
		tree = parser.program();
		assertTrue(true);
	}
	
	private void makeParser(String inputText)
	{
		parser = LexparserFactory.makeParser(new ANTLRInputStream(inputText));
	}
	
	private void doAST(String inputText)
	{
		doParse(inputText);
		creator = new ASTNodeCreator();
		ast = tree.accept(creator);
		ASTNode node = ast.getChild(0);
		System.out.println("ASTCreator : " + ast.toString());
	}
	
	private void doCodeGen(String inputText)
	{
		doAST(inputText);
		RubyCodeGenerator codeGenerator = new RubyCodeGenerator();
		codeString = ast.accept(codeGenerator);
		System.out.println(codeString);
		
		FileOutputStream fos;
		try{

			fos = new FileOutputStream("src/ruby/" + codeGenerator.programName  + ".rub");
			fos.write(codeString.getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Call this routine to display the parse tree. Hit ENTER on the console to continue.
	 */
	private void showTree()
	{
		System.out.println(tree.toStringTree());
		List<String> ruleNames = Arrays.asList(parser.getRuleNames());
		TreeViewer tv = new TreeViewer(ruleNames, tree);
		JFrame frame = new JFrame("Parse Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tv);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        BufferedReader br = 
                new BufferedReader(new InputStreamReader(System.in));
        try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
