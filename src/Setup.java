public class Setup {
    //SETUP
    private String player1Name;
    private String player2Name;
    private int digitCount;
    private String player1Number;
    private String player2Number;

    public void main() {
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
            player1Number = IO.readln("Enter Player 1 Number (of " + digitCount + " digits): ");
            if (player1Number.strip().length() == digitCount) {
                break;
            } else {
                IO.println("Wrong digit count, try again");
            }
        }

        //PLAYER2NUMBER:
        player2Name = IO.readln("Enter Player 2 Name: ");
        while (true) {
            player2Number = IO.readln("Enter Player 1 Number (of " + digitCount + " digits): ");
            if (player2Number.strip().length() == digitCount) {
                break;
            } else {
                IO.println("Wrong digit count, try again");
            }
        }
        IO.println(player1Name + " - " + player1Number);
        IO.println(player2Name + " - " + player2Number);
    }
}