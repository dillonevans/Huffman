import java.util.*;
import java.io.Serializable;
public class ByteNode implements Comparable, Serializable
{
    Byte ascii;
    int occurence;
    ByteNode left;
    ByteNode right;
    
    public ByteNode(Byte ascii, int occurence)
    {
        this.ascii = ascii;
        this.occurence = occurence;
        right = null;
        left = null;
    }

    public List<Byte> getBytes(List<Integer> l)
    {
        List<Byte> byteList = new ArrayList<>();
        ByteNode node = null;
        while (l.size() > 0)
        {
            node = this;
            while (node.getAscii() == null)
            {
                if (l.remove(0) == 0)
                {
                    node = node.getLeft();
                }
                else
                {
                    node = node.getRight();
                }
            }
            byteList.add(node.getAscii());
        }
        return byteList;
    }

    public List<Integer> getBits(Byte b)
    {
        return getBits(b,  new ArrayList<Integer>());
    }

    public List<Integer> getBits(Byte b, List<Integer> l)
    {
        if (this.getAscii() == b)
        {
            return l;
        }
        else
        {
            List<Integer> temp = new ArrayList<>(l);
    
            if (this.getLeft() != null)
            {
                l.add(0);
                l = this.getLeft().getBits(b, l);
                if (l != null)
                {
                    return l;
                }
            }
            l = temp;
            if (this.getRight() != null)
            {
                l.add(1);
                l = this.getRight().getBits(b, l);
                if (l != null)
                {
                    return l;
                }
            }    
        }
        return null;
    }

    public int getOccurence()
    {
        return occurence;
    }
    public Byte getAscii()
    {
        return ascii;
    }

    public ByteNode getLeft()
    {
        return left;
    }

    public ByteNode getRight()
    {
        return right;
    }

    public void setLeft(ByteNode l)
    {
        this.left = l;
    }

    public void setRight(ByteNode r)
    {
        this.right = r;
    }

   @Override
   public int compareTo(Object b) {
       ByteNode a = (ByteNode)b;

       if (a.getOccurence() > this.getOccurence())
       {
           return -1;
       }
       else if (a.getOccurence() < this.getOccurence())
       {
           return 1;
       }
       else
       {
           return 0;
       }
   }
}