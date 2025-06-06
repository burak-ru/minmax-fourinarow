package NRow.Players;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Node;
import NRow.Tree;
import java.util.Scanner;

/**
 * Class of MinMaxPlayer
 */

public class MinMaxPlayer extends PlayerController {
    private final int depth; // depth of the tree
    Scanner scanner = new Scanner(System.in);
    final int MIN = Integer.MIN_VALUE; // replacing -infinity
    final int MAX = Integer.MAX_VALUE; // replacing +infinity
    final int maxPlayerId = 1; // player id of the maximizing player

    /**
     * Constructor of minmax player
     * @param playerId either 1 or 2
     * @param gameN N in a row required to win
     * @param depth depth of the tree
     * @param heuristic heuristic that will be used
     */
    public MinMaxPlayer(int playerId, int gameN, int depth, Heuristic heuristic) {
        super(playerId, gameN, heuristic);
        this.depth = depth;
    }

    /**
     * Finds the best column using minmax algorithm
     * @param board the current board
     * @return column integer the player chose
     */
    @Override
    public int makeMove(Board board) {
        System.out.println(board);
        if (heuristic != null) {
            int move = 0; // starts from the first column
            int maxValue = MIN; // assign -infinity to max value to start
            for (int i = 0; i < board.width; i++) { // for each possible move
                if (board.isValid(i)) { // if move is playable
                    Board boardCopy = board.getNewBoard(i, playerId); // move played on board
                    Tree tree = new Tree(boardCopy, playerId, depth, gameN); // new boards tree
                    int maxMove = minMaxMove(tree.getRoot()); // evaluate the board
                    if (maxMove > maxValue) { // if evaluation is higher than current value
                        maxValue = maxMove; // assign evaluated value to current value
                        move = i; // pick that move as best
                    }
                }
            }
            System.out.println("Heuristic: " + heuristic + " calculated the best move is: "
                    + (move + 1));
        }
        System.out.println("Player " + this + "\nWhich column would you like to play in?");

        int column = scanner.nextInt();

        System.out.println("Selected Column: " + column);
        return column - 1;
    }

    /**
     * Evaluates children nodes and finds min or max depending on playerId
     * @param node the current board
     * @return min or max value evaluated
     */
    private int minMaxMove(Node node) {
        if (node.childNodes.isEmpty()) { // if game ended or depth is reached
            return heuristic.evaluateBoard(node.getPlayerId(), node.getBoard()); // return evaluated value
        }
        else if (node.getPlayerId() == maxPlayerId) { // else if player is maximizing
            int val = MIN; // assign -infinity to value to start
            for (Node child : node.getChildNodes()) { // for each child node
                int tempValue = minMaxMove(child); // assign value of child to temporary value
                val = Math.max(tempValue, val); // update value if temporary value is higher
            }
            return val; // return value
        }
        else {
            int val = MAX; // assign +infinity to value to start
            for (Node child : node.getChildNodes()) { // for each child node
                int tempValue = minMaxMove(child); // assign value of child to temporary value
                val = Math.min(tempValue, val); // update value if temporary value is less
            }
            return val; // return value
        }
    }
}
