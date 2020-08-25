package parsing;

public class Terminal extends Symbol{
	private String name;
	

    public Terminal(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	
    public boolean equals(Object o) {
        try {
            Terminal t = (Terminal) o;
            return t.getName().equals(name);
        }
        catch (Exception e) {
            return false;
        }
    }

    public int hashCode() {
        return name.hashCode();
    }

	@Override
	public boolean isNonTerminal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTerminal() {
		// TODO Auto-generated method stub
		return true;
	}
    public String toString() {
        return name;
    }

}
