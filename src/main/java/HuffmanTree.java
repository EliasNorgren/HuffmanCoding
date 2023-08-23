import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class HuffmanTree {

    private final HashMap<Character, Integer> freqTable = new HashMap<>();
    private final ArrayList<Node> sortedNodes = new ArrayList<Node>();
    private final HashMap<Character, String> characterBitSetHashMap = new HashMap<>();
    public Node rootNode;

    public HuffmanTree(FileInputStream fis) throws IOException {
        createFrequencyTable(fis);
        createSortedNodes();
        printFrequencyTable();

        this.rootNode = buildTree();

        buildBitMappings(rootNode);

    }

    private void buildBitMappings(Node rootNode) {
        StringBuilder bitSequence = new StringBuilder();
        
        recursion(rootNode, bitSequence);
    }

    private void recursion(Node node, StringBuilder bitSequence) {
        if(node.isLeaf){
            System.out.println(node.letter + " - " + bitSequence);
            characterBitSetHashMap.put(node.letter, bitSequence.toString());
            return;
        }
        StringBuilder leftSeq = new StringBuilder(bitSequence);
        StringBuilder rightSeq = new StringBuilder(bitSequence);

        leftSeq.append("0");
        rightSeq.append("1");

        recursion(node.leftChild, leftSeq);
        recursion(node.rightChild, rightSeq);
    }

    private Node buildTree() {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.addAll(sortedNodes);

        while(queue.size() > 1){
            Node leftNode = queue.remove();
            Node rightNode = queue.remove();

            Node parent = new Node('-', leftNode.weight + rightNode.weight, false);
            parent.leftChild = leftNode;
            parent.rightChild = rightNode;

            queue.add(parent);
        }
        return queue.remove();
    }

    private void createSortedNodes() {
        for(char key : freqTable.keySet()){
            sortedNodes.add(new Node(key, freqTable.get(key), true));
        }
        Collections.sort(sortedNodes);
    }

    private void printFrequencyTable() {
        System.out.println("--- Frequency table ---");
        for(Node n : sortedNodes){
            System.out.println(n.letter + " - " + n.weight);
        }
        System.out.println("-----------------------");
    }

    private void createFrequencyTable(FileInputStream fis) throws IOException {
        int r=0;
        while((r= fis.read())!=-1)
        {
            char ch = (char) r;
            if(!freqTable.containsKey(ch)){
                freqTable.put(ch, 1);
            }else{
                freqTable.put(ch, freqTable.get(ch) + 1);
            }
        }
    }

    public String getCharacterBitMapping(char c){
        return characterBitSetHashMap.get(c);
    }

    public class Node implements Comparable<Node>{
        char letter;
        int weight;
        boolean isLeaf;

        Node leftChild = null;
        Node rightChild = null;

        public Node(char letter, int weight, boolean isLeaf){
            this.letter = letter;
            this.weight = weight;
            this.isLeaf = isLeaf;
        }

        @Override
        public int compareTo(Node o) {
            if (this.weight > o.weight){
                return 1;
            }else if (this.weight < o.weight){
                return -1;
            }
            return 0;
        }


    }
}
