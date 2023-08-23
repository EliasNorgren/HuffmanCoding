import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;

public class HuffmanDecoder {

    private HuffmanTree tree;

    public HuffmanDecoder(HuffmanTree tree) {
        this.tree = tree;
    }

    public void decode(File destination, File source) throws IOException {
        FileReader fr = new FileReader(source);
        FileWriter fw = new FileWriter(destination);
        StringBuilder totalBits = new StringBuilder();
        int read;
        int padding = -1;
        while ((read = fr.read()) != -1){
            padding = read;
            String binaryString = Integer.toBinaryString(read);
            String paddedBinaryString = String.format("%8s", binaryString).replace(' ', '0');
            totalBits.append(paddedBinaryString);
        }
        totalBits.delete(totalBits.length() - 8 - padding, totalBits.length());
        System.out.println("     Decoding with: " + totalBits);

        HuffmanTree.Node currentNode = tree.rootNode;
        for (char bit : totalBits.toString().toCharArray()){
            if (bit == '0'){
                currentNode = currentNode.leftChild;
            }else {
                currentNode = currentNode.rightChild;
            }

            if(currentNode.isLeaf){
                fw.write(currentNode.letter);
                System.out.print(currentNode.letter);
                currentNode = tree.rootNode;
            }
        }

        fw.close();
        fr.close();
    }
}
