package parsing;

import java.util.*;

public class Rule {
	private List<Symbol> symbols;
	private NonTerminal vValue;
	
    public Rule(NonTerminal vValue, List<Symbol> symbols) {
        this.vValue = vValue;
        this.symbols = symbols;
    }
	
    public NonTerminal getValue() {
        return vValue;
    }

    public void setValue(NonTerminal vValue) {
        this.vValue = vValue;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }


    public String toString() {
        String s = "";
        for (Symbol o : symbols) {
            s += " " + o;
        }
        return s;
    }

	

}
