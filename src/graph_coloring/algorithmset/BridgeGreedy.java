package graph_coloring.algorithmset;

public class BridgeGreedy implements Comparable<BridgeGreedy> {

    public final NodeGreedy node;
    public final double weight;

    public BridgeGreedy(NodeGreedy node, double weight) {
        this.node = node;
        this.weight = weight;
    }

    @Override
    public int compareTo(BridgeGreedy anotherNeighbourWeight) {
        if (this.weight > anotherNeighbourWeight.weight) {
            return -1;
        } else if (this.weight < anotherNeighbourWeight.weight) {
            return 1;
        } else {
            return 0;
        }
    }
}
