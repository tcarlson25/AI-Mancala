import java.io.*;
import java.util.*;

class ClientParser {
  public static void parse(String cmd) {
    String[] tokens = cmd.split(" ");
    if(tokens.length < 1) {
      throw new IllegalArgumentException();
    }
    else if (tokens[0].equals("WELCOME")) {
      System.out.println(tokens[0]);
    }
    else if (tokens[0].equals("READY")) {
      System.out.println(tokens[0]);
    }
    else if (tokens[0].equals("OK")) {
      System.out.println(tokens[0]);
    }
    else if (tokens[0].equals("ILLEGAL")) {
      System.out.println(tokens[0]);
    }
    else if (tokens[0].equals("TIME")) {
      System.out.println(tokens[0]);
    }
    else if (tokens[0].equals("LOSER")) {
      System.out.println(tokens[0]);
    }
    else if (tokens[0].equals("WINNER")) {
      System.out.println(tokens[0]);
    }
    else if (tokens[0].equals("TIE")) {
      System.out.println(tokens[0]);
    }
    else if (tokens[0].equals("INFO")) {
      System.out.print(tokens[0 ]+ ": ");
      int numHouses = Integer.parseInt(tokens[1]);
      System.out.print(numHouses + " ");
      int numSeeds = Integer.parseInt(tokens[2]);
      System.out.print(numSeeds + " ");
      int timeInMS = Integer.parseInt(tokens[3]);
      System.out.print(timeInMS + " ");
      boolean goFirst = (tokens[4].equals("F"));
      System.out.print(goFirst + " ");
      boolean random = (tokens[5].equals("R"));
      System.out.println(random);
    }
    else if (tokens[0].equals("P")) {
        System.out.println(tokens[0]);
      } else {
    	int houseClicked = Integer.parseInt(tokens[0]);
    	int goAgainHouseClicked = -1;
    	System.out.print(houseClicked + " ");
    	if (tokens.length > 1) {
    		goAgainHouseClicked = Integer.parseInt(tokens[1]);
    		System.out.print(goAgainHouseClicked);
    	}
    	System.out.println("");
    }    
  }
}

class ServerParser {
	  public static void parse(String cmd) {
	    String[] tokens = cmd.split(" ");
	    if(tokens.length < 1) {
	      throw new IllegalArgumentException();
	    }
	    else if (tokens[0].equals("OK")) {
	      System.out.println(tokens[0]);
	    }
	    else if (tokens[0].equals("READY")) {
	        System.out.println(tokens[0]);
	    }
	    else if (tokens[0].equals("P")) {
	        System.out.println(tokens[0]);
	      } else {
	    	int houseClicked = Integer.parseInt(tokens[0]);
	    	int goAgainHouseClicked = -1;
	    	System.out.print(houseClicked + " ");
	    	if (tokens.length > 1) {
	    		goAgainHouseClicked = Integer.parseInt(tokens[1]);
	    		System.out.print(goAgainHouseClicked);
	    	}
	    	System.out.println();
	    }   
	  }
	}


class Solution {
  public static void main(String[] args) {
    //String fullcmd = "INFO 3 2 5 A R";
    
    //ClientParser.parse(fullcmd);
	  ClientParser.parse("WELCOME");
	  ClientParser.parse("INFO 4 1 5000 F S");
	  ServerParser.parse("READY");
	  ServerParser.parse("4 3");
	  ClientParser.parse("OK");
	  ServerParser.parse("P");
	  ClientParser.parse("P");
	  ServerParser.parse("OK");
	  ClientParser.parse("4 3");
	  ClientParser.parse("2");
	  ServerParser.parse("2");
	  ClientParser.parse("LOSER");
  }
}
