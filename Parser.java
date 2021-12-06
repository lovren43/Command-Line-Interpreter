package osAssignment1;

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
