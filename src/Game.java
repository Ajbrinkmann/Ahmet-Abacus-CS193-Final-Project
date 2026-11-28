import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Console;
import java.io.FileWriter;
import java.io.FileReader;
public class Game {
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
        //DIGITCOUNT:
        while (true) {
            try {
                digitCount = Integer.parseInt(IO.readln("Enter desired digit count: "));
                if (digitCount > 0) {
                break;
                }
                IO.println("Not an accepted digit count.");
            } catch (NumberFormatException n) {
                IO.println("Not a number.");
            }
        }

        //Assigning player name and numbers
        //Player Numbers will be kept as Strings because we need to check individual digits later. 
        //This also takes out the need for a number format check
        player1Name = IO.readln("Enter Player 1 Name: ");
        Console console = System.console();
        while (true) {
            char[] number = console.readPassword("Enter Player 1 Number (of " + digitCount + " digits) or 'r' for a random number: ");
            p2Target = new String(number);
            try {
                int num = Integer.parseInt(p2Target);
                if (p2Target.strip().length() == digitCount) {
                    break;
                } else {
                    IO.println("Wrong digit count, try again");
                }
            } catch (NumberFormatException n) {
                if (p2Target.equals("r")) {
                    p2Target = Integer.toString(generateRandom(digitCount));
                    break;
                } else {
                    IO.println("Invalid input. Try again");
                }
            }
        }

        player2Name = IO.readln("Enter Player 2 Name: ");
        while (true) {
            if (player2Name.equals(player1Name)) {
                IO.println("Name taken. Try a new one");
                player2Name = IO.readln("Enter Player 2 Name: ");
                continue;
            }
            break;
        }
        while (true) {
            char[] number = console.readPassword("Enter Player 2 Number (of " + digitCount + " digits) or 'r' for a random number: ");
            p1Target = new String(number);
            try {
                int num = Integer.parseInt(p1Target);
                if (p1Target.strip().length() == digitCount) {
                    break;
                } else {
                    IO.println("Wrong digit count, try again");
                }
            } catch (NumberFormatException n) {
                if (p1Target.equals("r")) {
                    p1Target = Integer.toString(generateRandom(digitCount));
                    break;
                }
            }
        }
    }

    //Used for Computer Opponent - simplifies random guess/number generation
    public int generateRandom(int digitCount) {
        int min = (int) Math.pow(10, digitCount - 1);
        int max = (int) Math.pow(10, digitCount) - 1;        

        //for 1 digit numbers - makes sure 0 is still allowed
        if (digitCount == 1) {
            min = 0;
        }
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public void main() {
        setup();
        playGame();
        String winner = endOfGame();
        updateGameLog(retrieveGameData(winner));
        (new Player(player1Name)).endOfGameUpdate();
        (new Player(player2Name)).endOfGameUpdate();
    }

    public void p1Turn() {   
        int plusCount = 0;
        int minusCount = 0;
        String guess = "";
        while (true) {
            guess = IO.readln(player1Name + "'s Guess: ");
            try {Integer.parseInt(guess);} catch (NumberFormatException n) {
                IO.println("Try a number this time");
                continue;
                }
            if (guess.length() != digitCount) {
                IO.println("Invalid digit count. Try again");
                continue;
            }
            break;
        }
        //"used" boolean array added to ensure duplicates in guess were not double counted
        boolean[] used = new boolean[digitCount];
        for (int i = 0; i < digitCount; i++) {
            if (guess.charAt(i) == p1Target.charAt(i)) {
                plusCount++;
                used[i] = true;
            }
        }
        for (int i = 0; i < digitCount; i++) {
            if (p1Target.indexOf(guess.charAt(i)) != -1 && !used[p1Target.indexOf(guess.charAt(i))]) {
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

    //Only difference between p1 and p2 turn is that p2 has random option
    public void p2Turn() {  
        int plusCount = 0;
        int minusCount = 0; 
        String guess = "";
        while (true) {
            guess = IO.readln(player2Name + "'s Guess (or 'r' for random guess/solo play): ");
            if (guess.equals("r")) {
                guess = Integer.toString(generateRandom(digitCount));
            } else {
                try {Integer.parseInt(guess);} catch (NumberFormatException n) {
                    IO.println("Try a number this time");
                    continue;
                    }
                if (guess.length() != digitCount) {
                    IO.println("Invalid digit count. Try again");
                    continue;
                }
            }
            break;
        }
        boolean[] used = new boolean[digitCount];
        for (int i = 0; i < digitCount; i++) {
            if (guess.charAt(i) == p2Target.charAt(i)) {
                plusCount++;
                used[i] = true;
            }
        }
        for (int i = 0; i < digitCount; i++) {
            if (p2Target.indexOf(guess.charAt(i)) != -1 && !used[p2Target.indexOf(guess.charAt(i))]) {
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

    //Displays "guess table" from README, helpful for users to organize thoughts
    public void displayGuesses(ArrayList<String> guesses) {
        IO.println("Previous Guesses:");
        for (int i = 0; i < guesses.size(); i++) {
            IO.println(guesses.get(i));
        }
        if (guesses.size() == 0) {
            IO.println("N/A");
        }
    }

    public String endOfGame() {
        String winner = null;
        if (p1WinCondition && p2WinCondition) {
            winner = "Tie";
            IO.println("Tie");
        }
        else if (p1WinCondition) {
            winner = player1Name;
            IO.println(player1Name + " Victory!");
        }
        else if (p2WinCondition) {
            winner = player2Name;
            IO.println(player2Name + " Victory!");
        }
        return winner;
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
    }

    public String[] retrieveGameData(String winner) {
        String[] output = new String[8];
        output[0] = "DigitCount: " + digitCount;
        output[1] = "Player 1: " + player1Name;
        output[2] = player1Name + "'s number: " + p2Target;
        output[3] = player1Name + " guess count: " + p1Guesses.size();
        output[4] = "Player 2: " + player2Name;
        output[5] = player2Name + "'s number: " + p1Target;
        output[6] = player2Name + " guess count: " + p2Guesses.size();
        output[7] = "Winner: " + winner;
        return output;
    }

    //GameData will come from retrieveGameData; parameter is not necessary but helps with organization
    public void updateGameLog(String[] gameData) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("GameLog.csv", true))) {
            pw.println(String.join(",", gameData));
        } catch (IOException e) {e.printStackTrace();}
    }
}