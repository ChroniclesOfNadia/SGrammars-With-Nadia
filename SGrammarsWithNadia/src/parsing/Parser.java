package parsing;

import java.util.*;
import java.io.*;

public class Parser {
	
	Set<NonTerminal> V = new HashSet<NonTerminal>();
	Set<Terminal> T = new HashSet<Terminal>();
	List<Rule> rules = new ArrayList<Rule>();
	NonTerminal startSymbol;
	boolean isSGrammar =true;
	String [][] parseTable;
	String test;
	//char [] testS;
	
	//input from text file
	public Parser(String filename, String ans) throws IOException{
		//SGrammarsWithNadia/src/parsing/grammar1.txt
		//SGrammarsWithNadia/src/parsing/grammar2.txt
		//File file = new File(filename);
		//File file = new File("SGrammarsWithNadia/src/parsing/grammar1.txt");
		Scanner sc;
		if(ans.equals("y")) {
			sc = new Scanner (getClass().getResourceAsStream(filename));
		}else{
			sc = new Scanner (filename);
		}
		while(sc.hasNext()) {
			String temp=sc.nextLine();
			if(temp.startsWith("Nonterminals")) {
				String sV = sc.nextLine();
				//System.out.println(sV);
				List<String> items = Arrays.asList(sV.split("\\s*,\\s*"));
				//System.out.println(items);
				for(int i=0; i<items.size(); i++) {
					if(items.get(i).equals("S")) {
						startSymbol= new NonTerminal("S");
						V.add(startSymbol);
						startSymbol.makeStartVar();
						//System.out.println(startSymbol);
					}else {
						NonTerminal Temp = new NonTerminal(items.get(i));
						V.add(Temp);	
					}
				}
				//System.out.println(V);
				
			}else if(temp.startsWith("Terminals")) {
				String sT = sc.nextLine();
				//System.out.println(sT);
				List<String> items = Arrays.asList(sT.split("\\s*,\\s*"));
				//System.out.println(items);
				for(int i=0; i<items.size(); i++) {
					Terminal Temp = new Terminal(items.get(i));
					T.add(Temp);
				}
				//System.out.println(T);
				
			}else if(temp.startsWith("String")) {
				test = sc.nextLine();
				//System.out.println(test);
				
				
			}else if(temp.startsWith("Rules")) {
				
				//String P = sc.nextLine();
				//System.out.println(P);
				while(sc.hasNext()) {
					String x = sc.nextLine();
					//System.out.println(x);
					List<String> items = Arrays.asList(x.split("\\s*->\\s*"));
					//System.out.println(items);
					NonTerminal Temp = new NonTerminal(items.get(0));
					int tempHash= Temp.hashCode();
					char cTemp= items.get(0).charAt(0);
					//System.out.println("Temp# :"+tempHash);
					int cTempHash = Character.hashCode(cTemp);
					//System.out.println("cTemp# :"+cTempHash);
					if(tempHash!=cTempHash) {
						isSGrammar= false;
					}
					List<Symbol> sList = new ArrayList<Symbol>();
					List<Symbol> sList2 = new ArrayList<Symbol>();
					List<String> items2 = Arrays.asList(items.get(1).split("\\s*\\s*"));
					//System.out.println(items2);
					
					for(int i=0; i<items2.size(); i++) {
						char c =items2.get(i).charAt(0);
	
						if(items2.get(i).equalsIgnoreCase("|")) {
								i++;
								int start = i;
								for(int j=start ; j<items2.size(); j++) {
									char c1 =items2.get(i).charAt(0);
		
									
									if(Character.isUpperCase(c1)) {
										NonTerminal symb = new NonTerminal(items2.get(i));
										sList2.add(symb);
									}else {
										Terminal symb = new Terminal(items2.get(i));
										sList2.add(symb);
									}
									
								}
							
							break;
						}
						if(Character.isUpperCase(c)) {
							NonTerminal symb = new NonTerminal(items2.get(i));
							sList.add(symb);
						}else {
							Terminal symb = new Terminal(items2.get(i));
							sList.add(symb);
						}
						
					}
					Rule ru = new Rule(Temp, sList);
					rules.add(ru);
	
					if(sList2.isEmpty()==false) {
						Rule ru2 = new Rule(Temp, sList2);
						rules.add(ru2);
	
					}

				}

			
			
			}
			for (NonTerminal n : V) {
				for (Rule rule : rules) {
					if (rule.getValue().getName().equals(n.getName())) {
						n.addToRules(rule);
	                }
	            }
	        }

		}
		/*
		for(int i = 0; i <rules.size();i++) {
			System.out.println("val: "+rules.get(i).getValue());
			System.out.println("Symb: "+rules.get(i).getSymbols());
		}
		*/
		
		getFirstSets();
		fillTable();
		if(isSGrammar==true) {
			System.out.println("The input grammar is a s-grammar :)");
		}else {
			System.out.println("The input grammar is not a s-grammar :(");
		}
		
		if(isStringValid(test)==true){
			System.out.println("The input string is a valid :)");
		}else {
			System.out.println("The input string is not valid :(");
		}
		
		
		sc.close();
	}
	
	
	//input from gui
	public Parser(Set<NonTerminal> V, Set<Terminal> T, List<Rule> rules, NonTerminal startSymbol) {
		this.V=V;
		this.T=T;
		this.rules=rules;
		this.startSymbol=startSymbol;
	}
	
	
	//calculate FirstSets
    public void getFirstSets() {
    	boolean changed= true;
    	while(changed) {
    		changed=false;
    		for (NonTerminal n : V) {
                for (Rule rule : n.getRules()) {
                	for (int i=0; i<rule.getSymbols().size(); i++) {
                        Symbol symbol = rule.getSymbols().get(i);
                        if (symbol.isTerminal() && n.getFirstSet().containsKey(symbol)) break;
                        if (symbol.isTerminal()  && !n.getFirstSet().containsKey((Terminal) symbol)) {
                            changed = true;
                            n.getFirstSet().put((Terminal) symbol, rule);
                            //test
                            //System.out.println((Terminal)symbol);
                            break;
                        }
                        else if (!symbol.equals(n) && symbol.isNonTerminal()) {
                            NonTerminal nSymbol = (NonTerminal) symbol;
                            for (Terminal t : nSymbol.getFirstSet().keySet()) {
                                if (!n.getFirstSet().containsKey(t)) {
                                    for (Terminal terminal : ((NonTerminal) symbol).getFirstSet().keySet()){
                                        n.getFirstSet().put(terminal, rule);
                                        //test
                                        //System.out.println((Terminal)symbol);
                                    }
                                    changed = true;
                                }
                            }
                        }
                	}//for
                
                }//for rule
            }//for nonT
    	}//while
    	
 
    	
    }//first
    
    //sGrammars don't really need follow sets since they don't include "epsilon"
    //so the following code is pretty useless
    public void getFollowSets() {
    	
        for (Rule rule : rules) {
            for (int i=0; i<rule.getSymbols().size() - 1; i++) {
                if (rule.getSymbols().get(i).isNonTerminal()) {
                    NonTerminal n = (NonTerminal) rule.getSymbols().get(i);
                    Symbol o = rule.getSymbols().get(i+1);
                    if (!o.isNonTerminal()) {
                        n.getFollowSet().add((Terminal) o);
                    }
                    else if (o.isNonTerminal()) {
                        for (Terminal terminal : ((NonTerminal) o).getFirstSet().keySet())
                        n.getFollowSet().add(terminal);
                    }
                }
            }
        }
    }
    //testing purposes
    public void printTable1() {
        System.out.print("\t\t\t|\t\t\t");
        for (Terminal terminal : T) {
            System.out.print(terminal + "\t\t\t|\t\t\t");
        }
        System.out.println();
        for (NonTerminal n : V) {
            System.out.print(n + "\t\t\t|\t\t\t");
            for (Terminal terminal : T) {
                if (n.getFirstSet().containsKey(terminal) || (n.getFollowSet().contains(terminal))) {
                    if (n.getFirstSet().containsKey(terminal)) {
                        System.out.print(n + " -> " + n.getFirstSet().get(terminal) + "\t\t\t|\t\t\t");
                    }
                    else {
                        System.out.print(n + " -> epsilon \t\t\t|\t\t\t");
                    }
                }
                else {
                    System.out.print("\t\t\t|\t\t\t");
                }
            }
            System.out.println();
        }

    }
    public void fillTable() {
    	int v= V.size()+1;
    	//System.out.println(v);
    	int t= T.size()+1;
    	//System.out.println(t);
    	parseTable= new String [v][t];
    	int i = 0;
    	int j= 0;
    	for(NonTerminal n : V) {
    		j=0;
    		for(Terminal terminal : T ) {
    			if(n.getFirstSet().containsKey(terminal)) {
    				if(parseTable[i][j]!=null) {
    					isSGrammar=false;
    					
    				}else {
    					parseTable[i][j]= n + " -> " + n.getFirstSet().get(terminal);
    				}
    			}
    			j++;
    			//System.out.println(j);
    			
    		}
    		i++;
    		//System.out.println(i);
    		
    		
    		
    	}

    	
    }
    //testing code
    public void printTable() {
    	
    	for(int i=0; i<parseTable.length; i++) {
			for(int j=0; j<parseTable[i].length; j++) {
				if(parseTable[i][j]!=null) {
				System.out.print("| " +parseTable[i][j]+ " |" );
				}else {
					System.out.print("| empty |");
				}
				
			}
			System.out.println();
		}
    }
	
    //is string valid
	public boolean isStringValid(String s) {
		boolean flag = true;
		boolean valid= false;
		List<String> sL = Arrays.asList(s.split("\\s*\\s*"));
		//System.out.println(sL);
		ArrayList<String> aL = new ArrayList<String>();
		ArrayList<String> a2 = new ArrayList<String>();
		for(int i=0; i<sL.size(); i++) {
			aL.add(sL.get(i));
		}
		int n=0;
		while(flag==true) {
			if(aL.equals(a2)) {
				valid= true;
				break;
			}
			else if(n==0) {
				Terminal temp = new Terminal( aL.get(0));
				if(startSymbol.getFirstSet().containsKey(temp)) {
					Rule r1 =  startSymbol.getFirstSet().get(temp);
					String x= r1.toString();
					List<String> sL2 = Arrays.asList(x.split("\\s"));
					for(int i =1; i < sL2.size(); i++) {
						a2.add(i-1, sL2.get(i));	
					}
					n++;
				}else {
					valid=false;
					break;
				}
				
			}else {
				char cTemp= a2.get(n).charAt(0);
				if(Character.isUpperCase(cTemp)) {
					NonTerminal nTemp =new NonTerminal(a2.get(n));
					Terminal tTemp =new Terminal(aL.get(n));
					boolean work= false;
					for(NonTerminal nT: V) {
						if (nT.equals(nTemp)) {
							if(nT.getFirstSet().containsKey(tTemp)) { 
								Rule r1 =  nT.getFirstSet().get(tTemp);
								String x= r1.toString();
								List<String> sL2 = Arrays.asList(x.split("\\s"));
								;
								a2.remove(n);
								for(int i =1; i < sL2.size(); i++) {
									a2.add(n+i-1, sL2.get(i));	
								}
								n++;
								work=true;
						}
						}
						
					}
					if(work==false) {
						valid=false;
						break;
					}
				
					
				}else if (Character.isLowerCase(cTemp)) {
					if(aL.get(n)==a2.get(n)) {
						n++;
					}else {
						valid=false;
						break;
					}
					
				}
				
			}
		}
		//System.out.println(aL);
		//System.out.println(a2);
		return valid;
	}
	
	public boolean sGrammarBool() {
		return isSGrammar;
	}
	
}
