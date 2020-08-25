package parsing;

import java.util.*;

public class NonTerminal extends Symbol{
	private Map<Terminal, Rule> firstSet= new HashMap<Terminal, Rule>();
	private boolean isStartVar;
	private List<Rule> rules = new ArrayList<Rule>();
	private Set<Terminal> followSet = new HashSet<Terminal>();
	private String name = "";
	
	public NonTerminal(String name) {
		this.name= name;
		isStartVar=false;
	}
	
	
	public List<Rule> getRules(){
		return rules;
	}
	
	public void addToRules(Rule rule) {
		rules.add(rule);
	}
	
	public boolean equals(Object o) {
		try {
			NonTerminal nT =(NonTerminal) o;
			return nT.getName().equals(name);
			
		}catch(Exception e) {
			return false;
		}
	}
	public int hashCode() {
		return name.hashCode();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Terminal> getFollowSet(){
		return followSet;
	}
	
	public void setFollowSet(Set<Terminal> followSet) {
		this.followSet = followSet;
	}
	
	public Map<Terminal, Rule> getFirstSet(){
		return firstSet;
	}
    public void setFirstSet(Map<Terminal, Rule> firstSet) {
        this.firstSet = firstSet;
    }
    public boolean isStartVar() {
        return isStartVar;
    }
    public void makeStartVar() {
        isStartVar = true;
    }
	

	@Override
	public boolean isNonTerminal() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isTerminal() {
		// TODO Auto-generated method stub
		return false;
	}
	public String toString() {
		return name;
	}

}
