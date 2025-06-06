package NRow;

public class Tree {
    public Node root; // beginning Node of the Tree

    /**
     * Constructor that creates root
     * @param board the board to check
     * @param playerId either 1 or 2
     * @param depth depth of the tree
     * @param gameN N in a row required to win
     */
    public Tree(Board board, int playerId, int depth, int gameN) {
        this.root = buildTree(board, playerId, depth, gameN);
    }

    /**
     * Builds the tree
     * @param board the board to check
     * @param playerId either 1 or 2
     * @param depth depth of the tree
     * @param gameN N in a row required to win
     * @return root node of the built tree
     */
    public Node buildTree(Board board, int playerId, int depth, int gameN) {
        Node node = new Node(board, playerId); // creates current states node
        int winning = Game.winning(board.getBoardState(), gameN); // checks if the game is won or drawn
        if (winning == 0 && depth > 0) {
            for (int i = 0; i < board.width; i++) {
                Board boardCopy = new Board(board); // clones board
                if (boardCopy.play(i, playerId)) { // finds next players ID
                    int nextPlayerId = 3 - playerId; // subtract current playerId from 3 and it gives nextPlayerId
                    Node child = buildTree(boardCopy, nextPlayerId, (depth - 1), gameN);
                    node.addChildNode(child);
                    /* adds the child node to childNodes list, builds it recursively so that all the children in every
                    depth will be added in a tree structure */
                }
            }
        }
        return node;
    }

    public Node getRoot() {
        return root;
    }
}
