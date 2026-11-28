import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
public class Player {
    private double winRate;
    private int mostCommonNumber;
    private String playerName;
    private double averageGuessCount;
    private int gamesPlayed;

    public Player(String playerName) {
        this.playerName = playerName;
        this.mostCommonNumber = -1;
        this.winRate = 0;
        this.gamesPlayed = 0;
        this.averageGuessCount = 0;
    }

    //Program required an empty constructor to run; never called
    public Player() {
    }

    public String[] retrieveStats() {
        double winCount = 0;
        double guessCount = 0;
        int[] usedNumbers = new int[10];
        try (BufferedReader br = new BufferedReader(new FileReader("GameLog.csv"))) {
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] gameStats = line.split(",");
                if (gameStats[1].equals("Player 1: " + playerName)) {
                    gamesPlayed++;
                    guessCount = Integer.parseInt(gameStats[3].substring(14 + playerName.length()));
                    String givenNumber = gameStats[2].substring(11 + playerName.length());
                    for (int i = 0; i < Integer.parseInt(gameStats[0].substring(12)); i++) {
                        //This is using char unicode math
                        usedNumbers[givenNumber.charAt(i) - 48]++;
                    }
                }

                if (gameStats[4].equals("Player 2: " + playerName)) {
                    gamesPlayed++;
                    guessCount = Integer.parseInt(gameStats[6].substring(14 + playerName.length()));
                    String givenNumber = gameStats[5].substring(11 + playerName.length());
                    for (int i = 0; i < Integer.parseInt(gameStats[0].substring(12)); i++) {
                        usedNumbers[givenNumber.charAt(i) - 48]++;
                    }
                }
                
                if (gameStats[7].equals("Winner: " + playerName) || gameStats[7].equals("Tie")) {
                    winCount++;
                } 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException n) {
            IO.println("NUMBER FORMAT BAD");
        }

        //These are all the final calculations after the gamelog has been read
        winRate = winCount / gamesPlayed;
        averageGuessCount = guessCount / gamesPlayed;
        int max = 0;
        for (int i = 0; i < 10; i++) {
            max = Math.max(max, usedNumbers[i]);
        }
        for (int i = 0; i < 10; i++) {
            //If there is a tie in most common number, the first one in ascending order will be chosen
            if (usedNumbers[i] == max) {
                mostCommonNumber = i;
                break;
            }
        }

        String[] output = {playerName, String.format("%.2f", winRate), String.format("%.2f", averageGuessCount), String.valueOf(gamesPlayed), String.valueOf(mostCommonNumber)};
        return output;
    }

    public void endOfGameUpdate() {
        updatePlayerLog(retrieveStats());
    }

    public void updatePlayerLog(String[] playerStats) {
        ArrayList<String> lines = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("PlayerLog.csv"))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[0].equals(playerStats[0])) {
                    lines.add(String.join(",", playerStats));
                    found = true;
                } else {
                    lines.add(line);
                }
            }
            if (!found) {
                lines.add(String.join(",", playerStats));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("PlayerLog.csv"))) {
            for (String line : lines) {
                pw.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}