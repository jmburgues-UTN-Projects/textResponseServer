package com.company;

public class Response {

    private static final int WAITING = 0;
    private static final int NAME = 1;
    private static final int CYPHER = 2;

    private static final int EXIT = 9;
    private int state = WAITING;

    public String processInput(String theInput) {
        String theOutput = null;
        if (theInput != null && theInput.equalsIgnoreCase("x")) {
            theOutput = "Bye.";
            state = EXIT;
        } else if (state == WAITING) {
            theOutput = "\nWelcome to JMBS server. Type 'x' at any time to leave.\n<RopHierr>: What`s your name?";
            state = NAME;
        } else if (state == NAME) {
            theOutput = "\n<RopHierr>: Nice to meet you " + theInput + ". My name is RopHierr, I'm a cypher machine." +
                    "\n<RopHierr>: Type CYPHER followed by a message and I will encrypt it to you. Eg: 'Cypher Hello'" +
                    "\n<RopHierr>: Type DECIPHER followed by a message and I will decode it. Eg: 'Decypher Bye'.";
            state = CYPHER;
        } else if (state == CYPHER) {
            theOutput = "";
            if (theInput.startsWith("CYPHER ")) {
                theInput = theInput.replaceFirst("CYPHER ","");
                for (int i = 0; i < theInput.length(); i++) {
                    char cypheredChar = (char) (theInput.charAt(i) + 3);
                    theOutput += cypheredChar;
                }

                System.out.println("Client cyphered: " + theInput + " to: " + theOutput);
            }
            else if (theInput.startsWith("DECYPHER ")) {
                theInput = theInput.replaceFirst("DECYPHER ","");
                for (int i = 0; i < theInput.length(); i++) {
                    char cypheredChar = (char) (theInput.charAt(i) - 3);
                    theOutput += cypheredChar;
                }

                System.out.println("Client de-cyphered: " + theInput + " to: " + theOutput);
            }
            else{
                theOutput = "<RopHierr>: Invalid input. Try again with CYPHER <text> or DECYPHER <text> or X to leave.";
            }
        }
        return theOutput;

    }

    public int getState(){
        return state;
    }

    public static int getWAITING() {
        return WAITING;
    }

    public static int getNAME() {
        return NAME;
    }

    public static int getCYPHER() {
        return CYPHER;
    }
}