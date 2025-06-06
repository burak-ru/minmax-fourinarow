package NRow;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public Board board; // nodes board state
    public int playerId; // nodes player id which is 1 or 2
    public List<Node> childNodes; // list of children nodes

    /**
     * Constructor that creates node
     * @param board the board to check
     * @param playerId either 1 or 2
     */
    public Node(Board board, int playerId) {
        this.board = board;
        this.playerId = playerId;
        this.childNodes = new ArrayList<>();
    }

    public Board getBoard() {
        return board;
    }

    public int getPlayerId() {
        return playerId;
    }

    public List<Node> getChildNodes() {
        return childNodes;
    }

    public void addChildNode(Node child) {
        this.childNodes.add(child);
    }
}
