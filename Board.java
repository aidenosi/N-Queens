
import java.util.PriorityQueue;

/*
 * Aiden Osipenko
 * 5853874
 * October 17th, 2017
 * COSC 3P71 Assignment 1
 */
public class Board implements Comparable<Board> {

    //Variable declarations
    int[] boardState; //Current state of the board
    private int hv; //Heuristic value
    private final int n; //Number of queens
    private PriorityQueue<Board> children; //List of all next possible board states

    /*
     * Constructor for when there is already a board state to be used.
     * @param board Array representation of the board state.
     */
    public Board(int[] board) {
        boardState = new int[board.length]; //Init boardState
        System.arraycopy(board, 0, boardState, 0, board.length);
        n = boardState.length;
        hv = calcHeuristic(); //Want heuristic value ASAP
        children = new PriorityQueue(); //Declare children
    }//constructor

    /*
     * Alternate constructor for when there is not already a board state to be 
     * used.
     * @param n Size of array (number of queens).
     */
    public Board(int n) {
        boardState = new int[n];
        for (int i : boardState) {
            boardState[i] = 0;
        }
        this.n = n;
    }

    public int[] getBState() {
        return this.boardState;
    }//getBState

    public int getHV() {
        return this.hv;
    }//getHV

    public Board getBestChild() {
        return this.children.peek();
    }//getBestChild

    /*
     * Generate a PriorityQueue of all next possible board states (children).
     * @return children A PriorityQueue containing this Board's children.
     */
    public PriorityQueue<Board> generateChildren() {
        //Make a copy of the board state that we can manipulate
        int[] boardCopy = new int[n];
        System.arraycopy(boardState, 0, boardCopy, 0, n);

        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= n; j++) {
                //Don't make a new board that doesn't move a queen
                if (boardCopy[i] != j) {
                    boardCopy[i] = j; //Move queen to new spot
                    //Create new child with new queen position
                    children.add(new Board(boardCopy));
                    //Reset copy
                    System.arraycopy(boardState, 0, boardCopy, 0, n);
                }
            }
        }
        return children;
    }//generateChildren

    /*
     * Calulate the heuristic value of this board.
     * @return H Integer result of the heuristic calculations.
     */
    private int calcHeuristic() {
        int H = 0; //Total number of conflicts
        for (int i = 0; i < n; i++) {
            //Add the number of conflicts that queen i has to total
            H += checkConflicts(i);
        }
        return H;
    }//calcHeuristic

    /*
    * Check the number of conflicts of a given queen.
    * @param c The index of the queen to check.
    * @return conflicts The number of conflicts that the given queen has.
     */
    public int checkConflicts(int c) {
        int conflicts = 0; //Number of conflicts that queen c has
        for (int i = 0; i < n; i++) {
            if (i != c) { //Don't check the current queen's column
                //If two queens have the same row
                if (boardState[i] == boardState[c] && boardState[c] != 0) {
                    conflicts++; //There is a conflict
                }
                //If two queens are along the same diagonal
                if (Math.abs(boardState[i] - boardState[c]) == Math.abs(i - c)
                        && boardState[c] != 0 && boardState[i] != 0) {
                    conflicts++; //There is a conflict
                }
            }
        }
        return conflicts; //Return the number of conflicts that this queen has
    } //checkConflicts

    /*
     * Print this Board's boardState to the output console.
     */
    public void printBoard() {
        for (int i : boardState) {
            System.out.print(i + "\t");
        }
        System.out.println();
        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= n; i++) {
                if (boardState[i - 1] == j) {
                    System.out.print("Q\t");
                } else {
                    System.out.print("_\t");
                }
            }
            System.out.println("\n");
        }
    } // end printBoard

    //Overriding compareTo method so that Boards are sorted based on their hv
    @Override
    public int compareTo(Board b) {
        if (this.getHV() == b.getHV()) {
            return 0;
        }
        return (this.getHV() < b.getHV() ? -1 : 1);
    }

}
