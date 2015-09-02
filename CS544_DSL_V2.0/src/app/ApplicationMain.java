package app;

import static org.junit.Assert.assertTrue;
import gen.RubyCodeGenerator;

import java.awt.Color;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.jruby.embed.EmbedEvalUnit;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

import utility.LexparserFactory;
import app.*;
import ast.ASTNode;
import ast.ASTNodeCreator;

import java.util.Scanner;
import java.util.Timer;

import lexparser.MovingPictureParser;

public class ApplicationMain {
    
	public static boolean Debug = false;
//    public static boolean Debug = true;
	
	private static final String filename = "ruby/ruby_java_engin.rb";
    private BufferedImage bi;
    public static String programName;
	private static ParserRuleContext tree;
	private static MovingPictureParser parser;
	private static ASTNodeCreator creator;
	private static ASTNode ast;
	private static String codeString;
    
	private static String fileContents = null;
	
    private ApplicationMain() throws IOException {
        System.out.println("[" + getClass().getName() + "]");
        ScriptingContainer container = new ScriptingContainer(LocalVariableBehavior.PERSISTENT);
        EmbedEvalUnit unit = container.parse(PathType.CLASSPATH, filename);
        container.put("@applet", ApplicationManager.getAppletInstance());
        if(Debug == false)
        {
        	container.put("@programname", "./rubydsl/" + programName + ".rub");
        }
        else
        {
        	container.put("@programname", "./ruby/" +  programName);
        }
        Object ret = unit.run();
        container.getVarMap().clear();
    }

    public static void main(String[] args) throws IOException {
    	
    	if(Debug == false)
    	{
	    	System.out.println(" Welcome!");
	    	System.out.println(" Please enter program name:");
	    	Scanner scanner = new Scanner(System.in);
	    	programName = scanner.nextLine();
//	    	programName = programName + ".rub";
	    	//read the input file containing the actual Dijkstra code
			try
			{
				fileContents = new Scanner(new File("zmydsl/" + programName + ".zmy")).useDelimiter("\\A").next();
			}
			catch(Exception e)
			{
				System.out.println("Either no input file was specified or it could not be read.");
				System.exit(1);
			}
	    	
			docompile();
    	}
    	else
    	{
    		programName = "test.rub";
    	}
    	
    	

//		doParse(fileContents);
    	new ApplicationMain(); 
        ApplicationManager.getAppletInstance().setupFrame();
        ApplicationManager.getAppletInstance().actionsStart();
    }
    
	private static void docompile() {
		// TODO Auto-generated method stub
		parser = LexparserFactory.makeParser(new ANTLRInputStream(fileContents));
		tree = parser.program();
		creator = new ASTNodeCreator();
		ast = tree.accept(creator);
		ASTNode node = ast.getChild(0);
		System.out.println("ASTCreator : " + ast.toString());
		RubyCodeGenerator codeGenerator = new RubyCodeGenerator();
		codeString = ast.accept(codeGenerator);
		
		FileOutputStream fos;
		try{

			fos = new FileOutputStream("./rubydsl/" + codeGenerator.programName  + ".rub");
			fos.write(codeString.getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}