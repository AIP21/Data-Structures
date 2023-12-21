package TowerOfHanoi;

import StackADT.LinkedStack;
import utils.CE;

public class TowerOfHanoi extends CE {
    private LinkedStack<Integer> A = new LinkedStack<>();
    private LinkedStack<Integer> B = new LinkedStack<>();
    private LinkedStack<Integer> C = new LinkedStack<>();

    public static void main(String[] args) {
        int n = 5;
        new TowerOfHanoi(n);
    }

    public TowerOfHanoi(int n) {
        // Setup stack A
        for (int i = 0; i < n; i++) {
            A.push(n - i);
        }

        // Print out initial setup
        print("Initial setup:");
        printTowers();
        print("");

        // Solve
        int moves = solveTowerOfHanoi(0);

        print(moves);
    }

    private boolean canMove(Integer a, Integer b) {
        // If A is null, we can't move A to B
        if (a == null) {
            return false;
        }
        // Otherwise ,if B is null, we can move A to B
        else if (b == null) {
            return true;
        }
        // Otherwise, if A is less than B,
        // then we can move A to B
        else {
            return a < b;
        }
    }

    public int solveTowerOfHanoi(int moves) {
        // Move one disk from peg A to peg B or vice versa, whichever move is legal.

        // Move A to B if it is legal
        if (canMove(A.top(), B.top())) {
            // Remove the top of A
            int aTop = A.pop();

            // Add it to B
            B.push(aTop);

            // Increment moves
            moves++;

            // Print move info
            print("Move: " + moves);
            printTowers();
        }
        // Otherwise, move B to A if it is legal
        else if (canMove(B.top(), A.top())) {
            // Remove the top of B
            int bTop = B.pop();

            // Add it to A
            A.push(bTop);

            // Increment moves
            moves++;

            // Print move info
            print("Move: " + moves);
            printTowers();
        }

        // Move one disk from peg A to peg C or vice versa, whichever move is legal.

        // Move A to C if it is legal
        if (canMove(A.top(), C.top())) {
            // Remove the top of A
            int aTop = A.pop();

            // Add it to C
            C.push(aTop);

            // Increment moves
            moves++;

            // Print move info
            print("Move: " + moves);
            printTowers();
        }
        // Otherwise, move C to A if it is legal
        else if (canMove(C.top(), A.top())) {
            // Remove the top of C
            int cTop = C.pop();

            // Add it to A
            A.push(cTop);

            // Increment moves
            moves++;

            // Print move info
            print("Move: " + moves);
            printTowers();
        }

        // Move one disk from peg B to peg C or vice versa, whichever move is legal.

        // Move B to C if it is legal
        if (canMove(B.top(), C.top())) {
            // Remove the top of B
            int bTop = B.pop();

            // Add it to C
            C.push(bTop);

            // Increment moves
            moves++;

            // Print move info
            print("Move: " + moves);
            printTowers();
        }
        // Otherwise, move C to B if it is legal
        else if (canMove(C.top(), B.top())) {
            // Remove the top of C
            int cTop = C.pop();

            // Add it to B
            B.push(cTop);

            // Increment moves
            moves++;

            // Print move info
            print("Move: " + moves);
            printTowers();
        }

        // Check if we're done solving
        int emptyStacks = 0;

        // Get the number of empty stacks
        emptyStacks += A.isEmpty() ? 1 : 0;
        emptyStacks += B.isEmpty() ? 1 : 0;
        emptyStacks += C.isEmpty() ? 1 : 0;

        // If two stacks are empty,
        // and A is empty then we finished solving
        if (A.isEmpty() && emptyStacks == 2) {
            return moves;
        }

        // We're not done, so keep solving
        return solveTowerOfHanoi(moves + 1);
    }

    private void printTowers() {
        print("A: " + A.toString());
        print("B: " + B.toString());
        print("C: " + C.toString());
        print("");
    }
}