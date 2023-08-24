import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public class HuffmanEncoder {

    private HuffmanTree tree;

    public HuffmanEncoder(HuffmanTree tree){
        this.tree = tree;
    }

    public void encode(File destination, File source) throws IOException {
        FileInputStream fr = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(destination);
        OutputStreamWriter fosw = new OutputStreamWriter(fos, StandardCharsets.ISO_8859_1);
        int b;
        BitSet bits = new BitSet(8);
        int bitIndex = 7;
        int writes = 0;
        while((b = fr.read()) != -1){
            String bitSequence = this.tree.getCharacterBitMapping((char) b);
            if(bitSequence == null){
                System.out.println("Byte with decimal value " + b + " / " + (char) b + " could not be found in CharacterBitMapping");
                continue;
            }
            for (char bit : bitSequence.toCharArray()){

                if(bit == '1'){
                    bits.set(bitIndex);
                }

                bitIndex --;

                if(bitIndex == -1){
                    writes ++;
                    long [] longArray = bits.toLongArray();
                    if (longArray.length == 0){
                        fosw.write(0);
                        bits.clear();
                        bitIndex = 7;
                    }else{
                        fosw.write((int) longArray[0]);
                        bits.clear();
                        bitIndex = 7;
                    }
                    fosw.flush();

                }
            }
        }
        System.out.println("Writes = " + writes);
        if(!bits.isEmpty()){
            long [] longArray = bits.toLongArray();
            fosw.write((int) longArray[0]);
        }

        fosw.write(bitIndex == 7 ? 0 : bitIndex + 1);
        fr.close();
        fosw.close();
    }
}
