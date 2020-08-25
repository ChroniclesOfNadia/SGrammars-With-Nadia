package parsing;

import java.io.IOException;
import java.util.Scanner;

public class RunAssignment {
	public static void main(String [] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Would you like to:");
		System.out.println("1) Intiate the GUI interface, or");
		System.out.println("2) Read an input from a textfile?");
		int in = sc.nextInt();
		
		if(in==1) {
			SGrammarGUI gui = new SGrammarGUI();
			gui.setVisible(true);	
		}
		else if(in==2) {
			System.out.println("Would you like to use an existing file (y/n)");
			String ans=sc.next();
			if(ans.equals("y")) {
				System.out.println("Choose one of the follwing grammars:");
				System.out.println("1) Grammar1");
				System.out.println("2) Grammar2");
				int x= sc.nextInt();
				if(x==1) {
					String filename = "grammar1.txt";
					Parser p= new Parser(filename, ans);
		
				}else {
					String filename = "grammar2.txt";
					Parser p= new Parser(filename, ans);
				}
			}else {
				System.out.println("Please Enter file extension:");
				String filename= sc.next();
				Parser p= new Parser(filename, ans);
			}
			
			
		}
		
		
		sc.close();
		
	}

}
