package osAssignment1;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Terminal {
	Parser parser;
	String currentPath="";
	String[] splitedCurrentPath;
	// Implement each command in a method, for example:
	public Terminal() {
		currentPath=System.getProperty("user.dir");
	}
	public String pwd(){
		return System.getProperty("user.dir");
	}
	public void cd() {
		currentPath= System.getProperty("user.home");
		System.setProperty( "user.dir", currentPath );
		System.out.println(currentPath);
	}
	
	public void cd(String args) {
		if (args == "..") {
			currentPath = new File(System.getProperty("user.dir")).getParent();
			System.setProperty("user.dir", currentPath);
			System.out.println(currentPath);
		} else {
			if (args.contains(":")) {
				currentPath = args;
				System.setProperty("user.dir", currentPath);
				System.out.println(currentPath);
			} else {
				currentPath += "\\" + args;
				System.setProperty("user.dir", currentPath);
				System.out.println(currentPath);
			}
		}

	}

	// ...
	// This method will choose the suitable command method to be called
	public void chooseCommandAction(String input){
	}
	public void echo(String userInput) {
		System.out.println(userInput);
	}
	public String[] ls() {
		File f= new File(currentPath);
		String[] pathContents=f.list();
		Arrays.sort(pathContents);
		return pathContents;
        
	}
	public void ls_r() {
		File f= new File(currentPath);
		String[] pathContents=f.list();
		Arrays.sort(pathContents, Collections.reverseOrder());
        System.out.printf("\n%s\n\n",Arrays.toString(pathContents));
	}

	public void mkdir(String arguments[]) {
		String tempCurrentPath = currentPath;
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i].contains(":")) {
				tempCurrentPath = arguments[i];
				File file = new File(tempCurrentPath);
				boolean creationStatus = file.mkdir();
				if (creationStatus) {
					System.out.println("done");
				} else {
					System.out.println("failed");
				}
			} else {
				tempCurrentPath += "\\" + arguments[i];
				File file = new File(tempCurrentPath);
				boolean creationStatus = file.mkdir();
				if (creationStatus) {
					System.out.println("done");
				} else {
					System.out.println("failed");
				}

			}
		}
		System.setProperty("user.dir", currentPath);
	}
	
	
	public void rmdir(String s) {
		if (s=="*") {
			String tempCurrentPath=currentPath;
			String separator="\\";
			String[] splitedPath=currentPath.split(Pattern.quote(separator));
			List<String> list = new ArrayList<String>(Arrays.asList(splitedPath)); 
			int counter=list.size()-1;
			for (int i=0; i<splitedPath.length; i++) {
				File f =new File(tempCurrentPath);
				String[] contents=f.list();
				tempCurrentPath=f.getParent();
				if (contents.length==0) {
					f.delete();
					list.remove(counter);
				}
				counter--;
			}
			currentPath="";
			for (int i=0; i<list.size(); i++) {
				if (i==list.size()-1) {
					currentPath+=list.get(i);
				}
				else {
					currentPath+=list.get(i)+"\\";
				}
			}
			System.setProperty("user.dir", currentPath);
			
		}
		else {
			if (s.contains(":")) {
				currentPath=s;
				File f=new File(currentPath);
				String[] list=f.list();
				if (list.length<1) {
					currentPath=f.getParent();
					f.delete();
				}
			}
			else {
				currentPath+="//"+s;
				File f=new File(currentPath);
				String[] list=f.list();
				if (list.length<1) {
					currentPath=f.getParent();
					f.delete();
				}
			}
			System.setProperty("user.dir", currentPath);
		}
	}

	public void cp(String nameOfFile1, String nameOfFile2) throws IOException {
		FileInputStream in = new FileInputStream(nameOfFile1);
		FileOutputStream out = new FileOutputStream(nameOfFile2);

		try {

			int n;
			while ((n = in.read()) != -1) {
				out.write(n);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	
	public void rm(String fileName) {
		String tempCurrentPath=currentPath+"//"+fileName;
		File f= new File(tempCurrentPath); 
		f.delete();
	}
	public void cat(String[] filesName) throws FileNotFoundException {
		for (int i=0; i<2; i++) {
			Scanner input = new Scanner(new File(currentPath+"\\"+filesName[i]));

			while (input.hasNextLine())
			{
			   System.out.println(input.nextLine());
			}
		}
	}
	public void touch(String s) throws IOException {
		String separator="\\";
		String[] splitedPath=s.split(Pattern.quote(separator));
		for (int i=0; i<splitedPath.length-1; i++) {
			currentPath+="\\"+splitedPath[i];
		}
		File f=new File(currentPath,splitedPath[splitedPath.length-1]);
		f.createNewFile();
		System.setProperty("user.dir", currentPath);
	}
	
	
	class Parser {
		String commandName;
		String[] args;

		// This method will divide the input into commandName and args
		// where "input" is the string command entered by the user
		public boolean parse(String input){
			try {
				args=input.split(" ");
				commandName=args[0];
				return true;
			} 
			catch(Exception e ) {
				return false;
			}
		}

		public String getCommandName(){
			return args[0];
		}

		public String[] getArgs(){
			return args;
		}
	}
	
	public static void main(String[] args) throws IOException{
		Terminal t=new Terminal();
		System.out.println(t.pwd());
		t.cd("..");
		
	}
}
