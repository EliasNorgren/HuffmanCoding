import java.io.*;
import java.util.BitSet;

public class HuffmanEncoder {

    private HuffmanTree tree;

    public HuffmanEncoder(HuffmanTree tree){
        this.tree = tree;
    }

    public void encode(File destination, File source) throws IOException {
        FileReader fr = new FileReader(source);
        FileWriter fw = new FileWriter(destination);
        int b;
        StringBuilder totalBitString = new StringBuilder();
        BitSet bits = new BitSet(8);
        int bitIndex = 7;
        while((b = fr.read()) != -1){
            String bitSequence = this.tree.getCharacterBitMapping((char) b);
            totalBitString.append(bitSequence);
            for (char bit : bitSequence.toCharArray()){


                if(bit == '1'){
                    bits.set(bitIndex);
                }

                bitIndex --;

                if(bitIndex == -1){
                    long [] longArray = bits.toLongArray();
                    System.out.println("Writing: " + longArray[0]);
                    fw.write((int) longArray[0]);
                    bits.clear();
                    bitIndex = 7;
                }
            }
        }

        if(!bits.isEmpty()){
            long [] longArray = bits.toLongArray();
            System.out.println("Writing: " + longArray[0]);

            fw.write((int) longArray[0]);
        }

        System.out.println("Padding = " + bitIndex + 1);
        fw.write(bitIndex + 1);

        System.out.println("Encoded bit string: " + totalBitString);
        fr.close();
        fw.close();
    }
}
