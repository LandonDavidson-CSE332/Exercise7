# Exercise7
This exercise required us to interpret a distribution problem (finding cycles of compatible kidney donors) as a graph problem. First I took the input of each donors compatability to each recipient and if it was high enough (>= 60) added it to the graph using an adjacency list representation. Then we implemented a recursive function to find a cycle in the graph that includes the given recipient, which represents a group of donors who can each donate to the next recipient in the cycle and all have their desired beneficiary recieve a donation.
