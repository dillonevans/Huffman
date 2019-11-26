import java.util.*;
import java.io.*;
public class Huff
{
    static List<Byte> byteArray = new ArrayList<Byte>();
    static HashMap<Byte, Integer> map = new HashMap<Byte, Integer>();
    static PriorityQueue<ByteNode> pq = new PriorityQueue<ByteNode>();
    static ByteNode l, r, node = null;
    static List<Integer> bits = new ArrayList<>();
    static int padding = 0;
    public static void main(String[] args)
    {
        try
        {
            if (args.length < 1)
            {
                throw new Exception("Error: Invalid Paramters Entered\nPlease follow the guidelines java Huff [in.txt] [in.huff]");
            }
            File in = new File(args[0]);
            FileInputStream stream = new FileInputStream(in);
            Byte i;
            while (stream.available() > 0)
            {
               i = (byte)stream.read();
               byteArray.add(i);
               if (!map.containsKey(i))
               {
                   map.put(i, 1);
               }
               else
               {
                   map.replace(i, map.get(i), map.get(i) + 1);
               }  
            }
            stream.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        
        
        for (Byte a: map.keySet())
        {
            if (a.intValue() > 0)
            {
                pq.add(new ByteNode(a, map.get(a)));

            }
        }

        while (!pq.isEmpty())
        {
            l = pq.poll();
            if (!pq.isEmpty())
            {
                r = pq.poll();
                node = new ByteNode(null, l.getOccurence() + r.getOccurence());
                node.setLeft(l);
                node.setRight(r);
                pq.add(node);
            }
            else
            {
                node = l;
            }
        }
     
        for (Byte b : byteArray)
        {
            for (int i: node.getBits(b))
            {
                bits.add(i);
            }
        }

        while (bits.size() % 8 != 0)
        {
            bits.add(0);
            padding++;
        }
        try
        {
            File outFile = new File(args[1]);
            byte[] bytes = Twiddle.bitsToBytes(bits);
            Integer paddingAmount = new Integer(padding);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outFile.getPath()));
            out.writeObject(paddingAmount);
            out.writeObject(node);
            out.writeObject(bytes);
            out.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}