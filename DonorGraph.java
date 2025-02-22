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
        // Get number of recipients from matchScores sub array. The first index doesn't matter for size but 0 will always word
        int numRecipients = matchScores[0].length;
        // Create an array of booleans to mark if an edge exists between two recipients in order to keep the graph simple
        boolean[][] edgeExist = new boolean[numRecipients][numRecipients];
        // Populate adjList with numRecipients LinkedLists
        adjList = new ArrayList<>();
        for (int i = 0; i < numRecipients; i++) {
            adjList.add(new LinkedList<>());
        }
        // Loop over each donor
        for (int i = 0; i < donorToBenefit.length; i++) {
            // Loop over each of that donors compatability with recipients
            for (int j = 0; j < matchScores[i].length; j++) {
                // If the donor and beneficiary are compatible and there isn't already an edge add a Match edge object
                if (matchScores[i][j] >= 60 && !edgeExist[donorToBenefit[i]][j]) {
                    // Create an edge with current donor and recipient
                    Match edge = new Match(i, donorToBenefit[i], j);
                    // Add edge to adjList
                    adjList.get(donorToBenefit[i]).add(edge);
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
        // Call the recersive helper to find cycles starting from the intended recipient node
        return recFindCycle(new LinkedList<>(), new ArrayList<>(), recipient, recipient);
    }

    // Recursive DFS to find a cycle including the recipient node, returns a LinkedList of edges forming the cycle
    private List<Match> recFindCycle(List<Match> path, List<Integer> visitedNodes, int node, int target) {
        // Get the edges of the current node
        List<Match> edgeList = adjList.get(node);
        // Loop over each edge
        for (int i = 0; i < edgeList.size(); i++) {
            // Get the current edge and add it to a new path LinkedList so they don't conflict
            Match edge = edgeList.get(i);
            // Check the node the edge points to hasn't been visited before
            if (visitedNodes.contains(edge.recipient)) {
                continue;
            }
            List<Match> copyPath = new LinkedList<>(path);
            copyPath.add(edge);
            // Add new node to visitedNodes
            visitedNodes.add(edge.recipient);
            // If the new edge points to the target recipient return the cycling path
            if (edge.recipient == target) {
                return copyPath;
            }
            // Otherwise call the recursive function on the new path, if it isn't empty return the found cycle
            path = recFindCycle(copyPath, visitedNodes, edge.recipient, target);
            if (!path.isEmpty()) {
                return path;
            }
        }
        // If the node doesn't have any edges return null since it isn't a cycle
        return new LinkedList<>();
    }

    // returns true or false to indicate whether there
    // is some cycle which includes the given recipient.
    public boolean hasCycle(int recipient){
        return findCycle(recipient).size() != 0;
    }
}
