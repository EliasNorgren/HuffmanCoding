import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public class HuffmanDecoder {

    private HuffmanTree tree;

    public HuffmanDecoder(HuffmanTree tree) {
        this.tree = tree;
    }

    public void decode(File destination, File source) throws IOException {
        FileInputStream fis = new FileInputStream(source);
        InputStreamReader instr = new InputStreamReader(fis, StandardCharsets.ISO_8859_1);
        FileOutputStream fos = new FileOutputStream(destination);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.ISO_8859_1);
        StringBuilder totalBits = new StringBuilder();
        int read;
        int padding = -1;
        while ((read = instr.read()) != -1){
            padding = read;
            String binaryString = Integer.toBinaryString(read);
            String paddedBinaryString = String.format("%8s", binaryString).replace(' ', '0');
            totalBits.append(paddedBinaryString);
        }
        totalBits.delete(totalBits.length() - 8 - padding, totalBits.length());

        HuffmanTree.Node currentNode = tree.rootNode;
        for (char bit : totalBits.toString().toCharArray()){
            if (bit == '0'){
                currentNode = currentNode.leftChild;
            }else {
                currentNode = currentNode.rightChild;
            }

            if(currentNode.isLeaf){
                osw.write(currentNode.letter);
                currentNode = tree.rootNode;
            }
        }

        osw.close();
        instr.close();
    }
}
