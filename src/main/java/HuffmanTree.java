import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class HuffmanTree {

    private final HashMap<Character, String> characterBitSetHashMap = new HashMap<>();
    public Node rootNode;

    public HuffmanTree(File data) throws IOException {
        FileInputStream fis = new FileInputStream(data);
        HashMap<Character, Integer> freqTable = createFrequencyTable(fis);
        ArrayList<Node> sortedNodes = createSortedNodes(freqTable);
        printFrequencyTable(sortedNodes);
        this.rootNode = buildTree(sortedNodes);

        buildBitMappings(rootNode);

        printBitmappings();

    }

    private void printBitmappings() {
        System.out.println("--- Printing bit mappings ---");
        for (Character key : characterBitSetHashMap.keySet()){
            String bitmap = characterBitSetHashMap.get(key);
            char sign;
            if (bitmap.length() == 8){
                sign = '=';
            }else if (bitmap.length() <= 8){
                sign = '<';
            }else{
                sign = '>';
            }
            System.out.println((int)key + " - " + key + " - " + characterBitSetHashMap.get(key) + " - " + sign);
        }
        System.out.println("------------------------------");
    }

    private void buildBitMappings(Node rootNode) {
        StringBuilder bitSequence = new StringBuilder();
        
        recursion(rootNode, bitSequence);
    }

    private void recursion(Node node, StringBuilder bitSequence) {
        if(node.isLeaf){
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

    private Node buildTree(ArrayList<Node> sortedNodes) {
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

    private ArrayList<Node> createSortedNodes(HashMap<Character, Integer> freqTable) {
        ArrayList<Node> sortedNodes = new ArrayList<Node>();
        for(char key : freqTable.keySet()){
            sortedNodes.add(new Node(key, freqTable.get(key), true));
        }
        Collections.sort(sortedNodes);
        return sortedNodes;
    }

    private void printFrequencyTable(ArrayList<Node> sortedNodes) {
        System.out.println("--- Frequency table ---");
        for(Node n : sortedNodes){
            System.out.println((int) n.letter + " - " + n.letter + " - " + n.weight);
        }
        System.out.println("-----------------------");
    }

    private HashMap<Character, Integer> createFrequencyTable(FileInputStream fis) throws IOException {
        HashMap<Character, Integer> freqTable = new HashMap<>();
        int r=0;
        while((r= fis.read()) != -1)
        {

            char ch = (char) r;
            if(!freqTable.containsKey(ch)){
                freqTable.put(ch, 1);
            }else{
                freqTable.put(ch, freqTable.get(ch) + 1);
            }
        }
        return freqTable;
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

        public void writeToFile(File destination){

        }


    }
}
