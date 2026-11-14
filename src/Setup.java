import java.util.ArrayList;
public class Setup {
    //SETUP
    private String player1Name;
    private String player2Name;
    private int digitCount;
    private String p2Target;
    private String p1Target;
    ArrayList<String> p1Guesses = new ArrayList<String>();
    ArrayList<String> p2Guesses = new ArrayList<String>();
    boolean winCondition;

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
            p2Target = IO.readln("Enter Player 1 Number (of " + digitCount + " digits): ");
            if (p2Target.strip().length() == digitCount) {
                break;
            } else {
                IO.println("Wrong digit count, try again");
            }
        }

        //PLAYER2NUMBER:
        player2Name = IO.readln("Enter Player 2 Name: ");
        while (true) {
            p1Target = IO.readln("Enter Player 2 Number (of " + digitCount + " digits): ");
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
        winCondition = false;
        while (winCondition == false) {
            p1Turn();
            p2Turn();
        }
        print("Game Over.")
    }

    public void p1Turn() {   
        int plusCount = 0;
        int minusCount = 0;
        String guess = "";
        while (true) {
            guess = IO.readln(player1Name + "'s Guess: ");
            p1Guesses.add(guess);
            if (guess.length() != digitCount) {
                IO.println("Invalid Digit Count. Try Again");
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
        IO.println(plusCount + " +, " + minusCount + " -");
        if (plusCount == digitCount) {
            winCondition = true;
        }
    }

    public void p2Turn() {  
        int plusCount = 0;
        int minusCount = 0; 
        String guess = "";
        while (true) {
            guess = IO.readln(player2Name + "'s Guess: ");
            p2Guesses.add(guess);
            if (guess.length() != digitCount) {
                IO.println("Invalid Digit Count. Try Again");
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
        IO.println(plusCount + " +, " + minusCount + " -");
        if (plusCount == digitCount) {
            winCondition = true;
        }
    }
}