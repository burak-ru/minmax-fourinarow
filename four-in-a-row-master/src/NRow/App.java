package NRow;

import NRow.Heuristics.SimpleHeuristic;
import NRow.Players.AlphaBetaPruning;
import NRow.Players.HumanPlayer;
import NRow.Players.MinMaxPlayer;
import NRow.Players.PlayerController;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        int gameN = 4;
        int boardWidth = 7;
        int boardHeight = 6;

        PlayerController[] players = getPlayers(gameN);

        Game game = new Game(gameN, boardWidth, boardHeight, players);
        game.startGame();
    }

    /**
     * Determine the players for the game
     * @param n
     * @return an array of size 2 with two Playercontrollers
     */
    private static PlayerController[] getPlayers(int n) {
        Scanner scanner = new Scanner(System.in);
        SimpleHeuristic heuristic1 = new SimpleHeuristic(n);
        SimpleHeuristic heuristic2 = new SimpleHeuristic(n);
        System.out.println("Depth:");
        int depth = scanner.nextInt();

        PlayerController human = new HumanPlayer(1, n, heuristic1);
        PlayerController human2 = new HumanPlayer(2, n, heuristic2);

        //TODO: Implement other PlayerControllers (MinMax, AlphaBeta)
        PlayerController minMaxPlayer = new MinMaxPlayer(1, n, depth, heuristic1);
        PlayerController minMaxPlayer2 = new MinMaxPlayer(2, n, depth, heuristic2);

        PlayerController alphaBetaPlayer = new AlphaBetaPruning(1, n, depth, heuristic1);
        PlayerController alphaBetaPlayer2 = new AlphaBetaPruning(2, n, depth, heuristic2);

        PlayerController[] players = { human, human2 };
        PlayerController[] minMaxPlayers = { minMaxPlayer, minMaxPlayer2 };
        PlayerController[] alphaBetaPlayers = { alphaBetaPlayer, alphaBetaPlayer2 };
        PlayerController[] customPlayers = { null, null };

        System.out.println("""
                Choose player 1:
                 HumanPlayer(1)
                 MinMaxPlayer(2)
                 AlphaBetaPlayer(default)""");
        int choicePlayer1 = scanner.nextInt();
        switch (choicePlayer1) {
            case 1 -> {
                customPlayers[0] = human;
                System.out.println("Human player!");
            }
            case 2 -> {
                customPlayers[0] = minMaxPlayer;
                System.out.println("MinMax player!");
            }
            default -> {
                customPlayers[0] = alphaBetaPlayer;
                System.out.println("AlphaBeta player!");
            }
        };
        System.out.println("""
                Choose player 2:
                 HumanPlayer(1)
                 MinMaxPlayer(2)
                 AlphaBetaPlayer(default)""");
        int choicePlayer2 = scanner.nextInt();
        switch (choicePlayer2) {
            case 1 -> {
                customPlayers[1] = human2;
                System.out.println("Human player!");
            }
            case 2 -> {
                customPlayers[1] = minMaxPlayer2;
                System.out.println("MinMax player!");
            }
            default -> {
                customPlayers[1] = alphaBetaPlayer2;
                System.out.println("AlphaBeta player!");
            }
        };
        return customPlayers;
    }
}
