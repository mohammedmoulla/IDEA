package idea.algorithm;
public class Convert {
    public static Binary[] stringToBinary16(String text){
        int n = text.length();
        Binary [] result = new Binary[n];
        for (int i=0 ; i<n; i++){
            char c = text.charAt(i);
            long x = c ;
            String temp = Long.toBinaryString(x);
            result[i]=new Binary(16);
            result[i].set(temp);
        }        
        return result;
    }
     public static Binary[] stringToBinary8(String text){
         int n = text.length();
        Binary [] result = new Binary[n];
        for (int i=0 ; i<n; i++){
            char c = text.charAt(i);
            long x = c ;
            String temp = Long.toBinaryString(x);
            result[i]=new Binary(8);
            result[i].set(temp);
        }        
        return result;
    }
    public static String binaryToString(Binary [] binary){
        String result = "";
        int n = binary.length;
        for (int i=0; i<n; i++){
            char c = (char)Long.parseLong(binary[i].get(),2);
            result+= c;
        }
        return result;
    }
    public static String binaryToHex(Binary [] binary){
        String result = "";
        int n = binary.length;
        for (int i=0; i<n; i++){
            long x = Long.parseLong(binary[i].get(),2);
            String t = Long.toHexString(x);
            result+= t;
        }
        return result;
    }
        
    
}

