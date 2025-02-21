import java.util.*;

public class DonorGraph {
    private List<List<Match>> adjList;

    // The donatingTo array indicates which repient each donor is
    // affiliated with. Specifically, the donor at index i has volunteered
    // to donate a kidney on behalf of recipient donatingTo[i].
    // The matchScores 2d array gives the match scores associated with each
    // donor-recipient pair. Specificically, matchScores[x][y] gives the
    // HLA score for donor x and reciplient y.
    public DonorGraph(int[] donorToBenefit, int[][] matchScores){
        adjList = new ArrayList<>();
        // Get number of recipients from matchScores sub array. The first index doesn't matter for size but 0 will always word
        int numRecipients = matchScores[0].length;
        // Create an array of booleans to mark if an edge exists between two recipients in order to keep the graph simple
        boolean[][] edgeExist = new boolean[numRecipients][numRecipients];
        // Loop over each donor
        for (int i = 0; i < donorToBenefit.length; i++) {
            // Add new LinkedList to adjList to store current donor's matches
            adjList.add(new LinkedList<>());
            // Loop over each of that donors compatability with recipients
            for (int j = 0; j < matchScores[i].length; j++) {
                // If the donor and beneficiary are compatible and there isn't already an edge add a Match edge object
                if (matchScores[i][j] >= 60 && !edgeExist[donorToBenefit[i]][j]) {
                    // Create an edge with current donor and recipient
                    Match edge = new Match(i, donorToBenefit[i], j);
                    // Add edge to adjList
                    adjList.get(i).add(edge);
                    // Mark the edge as existing
                    edgeExist[donorToBenefit[i]][j] = true;
                }
            }
        }
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public boolean isAdjacent(int start, int end){
        for(Match m : adjList.get(start)){
            if(m.recipient == end)
                return true;
        }
        return false;
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public int getDonor(int beneficiary, int recipient){
        for(Match m : adjList.get(beneficiary)){
            if(m.recipient == recipient)
                return m.donor;
        }
        return -1;
    }


    // returns a chain of matches to make a donor cycle
    // which includes the given recipient.
    // Returns an empty list if no cycle exists. 
    public List<Match> findCycle(int recipient){
        // TODO
        return new ArrayList<>();
    }

    // returns true or false to indicate whether there
    // is some cycle which includes the given recipient.
    public boolean hasCycle(int recipient){
        // TODO
        return false;
    }
}
