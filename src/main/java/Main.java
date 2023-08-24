import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File source = new File("D:\\Java\\Huffman\\fin.png");

        long uncompressedSize = source.length();
        System.out.println("File size before compression: " + uncompressedSize + " bytes");
        HuffmanTree tree = new HuffmanTree(source);

        System.out.println("Encoding file");
        File destination = new File("result.txt");
        HuffmanEncoder enc = new HuffmanEncoder(tree);
        enc.encode(destination, source);

        long compressedSize = destination.length();
        System.out.println("File size after compression: " + compressedSize + " bytes");

        System.out.println("Compression ratio: " + (double) compressedSize / uncompressedSize);

        System.out.println("Decoding file");
        HuffmanDecoder dec = new HuffmanDecoder(tree);
        destination = new File("decrypted");
        source = new File("result.txt");
        dec.decode(destination, source);


    }
}
