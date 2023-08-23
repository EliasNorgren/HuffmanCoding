import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File source = new File("D:\\Java\\Huffman\\src\\main\\resources\\testData.txt");
        FileInputStream fis= new FileInputStream(source);
        HuffmanTree tree = new HuffmanTree(fis);
        fis.close();
        File destination = new File("result.txt");
        HuffmanEncoder enc = new HuffmanEncoder(tree);
        enc.encode(destination, source);

        HuffmanDecoder dec = new HuffmanDecoder(tree);
        destination = new File("decrypted.txt");
        source = new File("result.txt");
        dec.decode(destination, source);
    }
}
