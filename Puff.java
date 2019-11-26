import java.nio.file.*;
import java.util.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
public class Puff
{
    public static void main(String[] args)
    {
        ByteNode node;
        File inFile;
        File outFile;
        Integer padding;
        int padLength;
        try 
        {
            if (args.length < 1)
            {
                throw new Exception("Error: Invalid Paramters Entered\nPlease follow the guidelines java Huff [in.huff] [out.puff]");
            }
            inFile = new File(args[0]);
            outFile = new File(args[1]);
	    OutputStream output = new BufferedOutputStream(Files.newOutputStream(outFile.toPath(), WRITE));
            BufferedWriter writer = new	BufferedWriter(new OutputStreamWriter(output));
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(inFile.getPath()));
            padding = (Integer)in.readObject();
            node = (ByteNode)in.readObject();
            byte[] bytes = (byte[])in.readObject();
            List<Integer> bits = Twiddle.bytesToBits(bytes);
            padLength = padding.intValue();
            int size = bits.size();
            if (padLength > 0)
            {
                for (int i = 1; i <= padLength; i++ )
                {
                    bits.remove(size - i);
                }
            }
            List<Byte> byteList = node.getBytes(bits);
            StringBuilder string = new StringBuilder();
            for (Byte b: byteList)
            {
                char c = (char)b.intValue();
                string.append(c);  
            }
            System.out.println(string);
            in.close();
            writer.write(string.toString());
            writer.close();
            output.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
