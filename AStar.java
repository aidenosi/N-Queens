
import java.util.PriorityQueue;

class AStar {

    /**
     * Method that executes an A* search to find a solution to the N Queens
     * problem.
     *
     * @param b Board to perform the search on.
     */
    public static void performSearch(Board initial) {
        //Init variables
        int bestHV = Integer.MAX_VALUE, bestIndex = -1;
        Board bestChild;

        //Boards to be evaluated
        PriorityQueue<Board> closed = new PriorityQueue();
        //Boards that have been evaluated
        PriorityQueue<Board> open = new PriorityQueue();

        open.add(initial); //Add initial board to list

        while (!open.isEmpty()) {
            //First Board will always have heuristic value <= all other Boards
            bestChild = open.poll();

            nQueens.aStar_configs_tested++;

            if (bestChild.getHV() == 0) {
                System.out.println("Solution found. ");
                System.out.println("Configurations tested: " + nQueens.aStar_configs_tested);
                bestChild.printBoard();
                return;
            }
            //We've now evaluated this Board
            closed.add(bestChild);
            PriorityQueue<Board> childrenOfCurrent = bestChild.generateChildren();
            childrenOfCurrent.stream().filter((b) -> !(closed.contains(b)))
                    .filter((b) -> (!closed.contains(b))).forEach((b) -> {
                open.add(b);
            });
            bestHV = Integer.MAX_VALUE; //Resetting variables
            bestIndex = -1;
        }
        //If we get here, no solution was found
        System.out.println("No solution found.");
    }//doAStar
}
