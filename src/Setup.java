import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Console;
public class Setup {
    //SETUP
    private String player1Name;
    private String player2Name;
    private int digitCount;
    private String p2Target;
    private String p1Target;
    ArrayList<String> p1Guesses = new ArrayList<String>();
    ArrayList<String> p2Guesses = new ArrayList<String>();
    boolean p1WinCondition;
    boolean p2WinCondition;

    public void setup() {
        player1Name = IO.readln("Enter Player 1 Name: ");
        //DIGITCOUNT:
        while (true) {
            try {
                digitCount = Integer.parseInt(IO.readln("Enter desired digit count: "));
                if (digitCount > 0) {
                break;
                }
                IO.println("Not an accepted digit count.");
            } catch (NumberFormatException n) {
                IO.println("Not an accepted digit count.");
            }
        }
        //PLAYER1NUMBER:
        //Player Numbers will be kept as Strings because we need to check individual digits later. 
        //This also takes out the need for a number format check
        while (true) {
            Console console = System.console();
            char[] number = console.readPassword("Enter Player 1 Number (of " + digitCount + " digits): ");
            p2Target = new String(number);
            //p2Target = IO.readln("Enter Player 1 Number (of " + digitCount + " digits): ");
            if (p2Target.strip().length() == digitCount) {
                break;
            } else {
                IO.println("Wrong digit count, try again");
            }
        }

        //PLAYER2NUMBER:
        player2Name = IO.readln("Enter Player 2 Name: ");
        while (true) {
            Console console = System.console();
            char[] number = console.readPassword("Enter Player 2 Number (of " + digitCount + " digits): ");
            p1Target = new String(number);
            //p1Target = IO.readln("Enter Player 2 Number (of " + digitCount + " digits): ");
            if (p1Target.strip().length() == digitCount) {
                break;
            } else {
                IO.println("Wrong digit count, try again");
            }
        }
        //FOR CHECKING; DELETE BEFORE FINAL VERSION:
        // IO.println(player1Name + " - " + player1Number);
        // IO.println(player2Name + " - " + player2Number);
    }

    public void main() {
        setup();
        playGame();
    }

    public void p1Turn() {   
        int plusCount = 0;
        int minusCount = 0;
        String guess = "";
        while (true) {
            guess = IO.readln(player1Name + "'s Guess: ");
            if (guess.length() != digitCount) {
                IO.println("Invalid Digit Count. Try Again");
                continue;
            }
            try {Integer.parseInt(guess);} catch (NumberFormatException n) {
                IO.println("Try a number this time");
                continue;
                }
            break;
        }
        for (int i = 0; i < digitCount; i++) {
            if (guess.charAt(i) == p1Target.charAt(i)) {
                plusCount++;
            } else if (p1Target.indexOf(guess.charAt(i)) != -1) {
                minusCount++;
            }
        }
        String output = plusCount + " +, " + minusCount + " -";
        p1Guesses.add(guess + " : " +  output);
        IO.println(output);
        if (plusCount == digitCount) {
            p1WinCondition = true;
        }
    }

    public void p2Turn() {  
        int plusCount = 0;
        int minusCount = 0; 
        String guess = "";
        while (true) {
            guess = IO.readln(player2Name + "'s Guess: ");
            if (guess.length() != digitCount) {
                IO.println("Invalid Digit Count. Try Again");
                continue;
            }
            try {Integer.parseInt(guess);} catch (NumberFormatException n) {
                IO.println("Try a number this time");
                continue;
                }
            break;
        }
        for (int i = 0; i < digitCount; i++) {
            if (guess.charAt(i) == p2Target.charAt(i)) {
                plusCount++;
            } else if (p2Target.indexOf(guess.charAt(i)) != -1) {
                minusCount++;
            }
        }
        String output = plusCount + " +, " + minusCount + " -";
        p2Guesses.add(guess + " : " +  output);
        IO.println(output);
        if (plusCount == digitCount) {
            p2WinCondition = true;
        }
    }

    public void displayGuesses(ArrayList<String> guesses) {
        IO.println("Previous Guesses:");
        for (int i = 0; i < guesses.size(); i++) {
            IO.println(guesses.get(i));
        }
        if (guesses.size() == 0) {
            IO.println("N/A");
        }
    }

    public void endOfGame() {
        if (p1WinCondition && p2WinCondition) {
            IO.println("Tie");
        }
        else if (p1WinCondition) {
            IO.println(player1Name + " Victory!");
        }
        else if (p2WinCondition) {
            IO.println(player2Name + " Victory!");
        }
        //try ()
    }

    public void playGame() {
        while (!p1WinCondition && !p2WinCondition) {
            IO.println("\n" + player1Name + "'s turn");
            displayGuesses(p1Guesses);
            p1Turn();
            IO.println("\n" + player2Name + "'s turn");
            displayGuesses(p2Guesses);
            p2Turn();
        }
        endOfGame();
    }

}