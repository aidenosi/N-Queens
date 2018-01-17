
public class blindSearch {

    /**
     * Method that executes a blind search (with backtracking) to find a
     * solution to the N Queens problem.
     *
     * @param initial Board on which to perform the search on.
     */
    public static void performSearch(Board initial) {
        //Init variables
        int queen = 0, row = 1, row_main = row, temp;
        boolean solution_found = true;

        initial.boardState[queen++] = row_main; //Put first queen in row 1
        while (queen < nQueens.n) {
            while (row <= nQueens.n) {
                initial.boardState[queen] = row; //Put queen in this row
                //If this queen doesn't conflict with any others
                if (initial.checkConflicts(queen) == 0) {
                    queen++; //Go to next queen
                    row = 1;
                    break;
                } else { //If this queen conflicts with another
                    initial.boardState[queen] = 0; //Reset queen
                    row++; //Try next row
                }
                //If we've experienced conflicts in all rows
                if (row > nQueens.n) {
                    queen--; //Go to previous queen
                    while (queen != 0) {
                        temp = initial.boardState[queen] + 1; //Try next row
                        while (temp <= nQueens.n) {
                            initial.boardState[queen] = temp;
                            //If this queen doesn't conflict with any others
                            if (initial.checkConflicts(queen) == 0) {
                                queen++; //Go to next queen
                                row = 0;
                                break;
                            } else { //If this queen conflicts with another
                                initial.boardState[queen] = 0; //Reset queen
                                row = ++temp; //Try next row
                            }
                        }
                        //row = 0 iff we found a move that doesnt cause conflict
                        if (row == 0) {
                            row++;
                            break;
                        } else {
                            queen--; //Backtrack to the previous queen
                        }
                    }
                }
            }
            //If we've not found a move that doesn't cause conflict
            if (row > nQueens.n) {
                row_main++;
                if (row_main > nQueens.n) { //Don't go out of bounds
                    break;
                }
                initial.boardState[queen] = row_main; //Put queen here
                row = 1;
                queen++; //Go to next queen
            }
            nQueens.blind_configs_tested++;
        }
        //Check if solution was found or not
        for (int i = 0; i < nQueens.n; i++) {
            if (initial.checkConflicts(i) == 0) {
                solution_found = true;
            } else {
                solution_found = false;
                break;
            }
        }
        if (solution_found) {
            System.out.println("Solution found.");
            System.out.println("Configurations tested: " + nQueens.blind_configs_tested);
            initial.printBoard();
        } else {
            System.out.println("No solution found.");
        }
    }

}
