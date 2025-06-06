package NRow.Players;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Tree;
import NRow.Node;
import java.util.Scanner;

/**
 * Class of AlphaBetaPruning
 */
public class AlphaBetaPruning extends PlayerController {
    private final int depth; // depth of the tree
    Scanner scanner = new Scanner(System.in);
    final int MIN = Integer.MIN_VALUE; // replacing -infinity
    final int MAX = Integer.MAX_VALUE; // replacing +infinity
    final int Alpha = Integer.MIN_VALUE; // value of beta
    final int Beta = Integer.MAX_VALUE; // value of alpha
    final int maxPlayerId = 1; // player id of the maximizing player

    /**
     * Constructor of alpha-beta pruning
     * @param playerId either 1 or 2
     * @param gameN N in a row required to win
     * @param depth depth of the tree
     * @param heuristic heuristic that will be used
     */
    public AlphaBetaPruning(int playerId, int gameN, int depth, Heuristic heuristic) {
        super(playerId, gameN, heuristic);
        this.depth = depth;
    }

    /**
     * Finds the best column using alpha-beta pruning algorithm
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
                    int maxMove = alphaBetaMove(tree.getRoot(), Alpha, Beta); // evaluate the board
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
     * Evaluates children nodes, prunes children using alpha-beta
     * and finds min or max depending on playerId
     * @param node the current board
     * @return min orMin max value evaluated
     */
    private int alphaBetaMove(Node node, int a, int b) {
        if (node.childNodes.isEmpty()) { // if game ended or depth is reached
            return heuristic.evaluateBoard(node.getPlayerId(), node.getBoard()); // return evaluated value
        }
        else if (node.getPlayerId() == maxPlayerId) { // else if player is maximizing
            int val = MIN; // assign -infinity to value to start
            for (Node child : node.getChildNodes()) { // for each child node
                int tempValue = alphaBetaMove(child, a, b); // assign value of child to temporary value
                val = Math.max(tempValue, val); // update value if temporary value is higher
                a = Math.max(val, a); // update alpha if value is higher
                if (b <= a) { // if beta is less than alpha
                    break; // prune
                }
            }
            return val; // return value
        }
        else {
            int val = MAX; // assign +infinity to value to start
            for (Node child : node.getChildNodes()) { // for each child node
                int tempValue = alphaBetaMove(child, a, b);  // assign value of child to temporary value
                val = Math.min(tempValue, val); // update value if temporary value is less
                b = Math.min(val, b); // update beta if value is less
                if (b <= a) { // if beta is less than alpha
                    break; // prune
                }
            }
            return val; // return value
        }
    }
}
