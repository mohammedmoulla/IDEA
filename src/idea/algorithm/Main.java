package idea.algorithm;
public class Main {
    public static void main(String[] args) {
        String text1 = "mohammed moulla";
        String key1 = "qqqqqqqqqeeeeeeeeeeeeeeeee";
        String cipher1 = IDEA.encrypt8(text1, key1);
        
        String original1 = IDEA.decrypt8(cipher1, key1);
        System.out.println("text = "+text1);
        System.out.println("cipher = "+cipher1);
        System.out.println("original = "+original1);

        System.out.println("*******************");
        
        String text2 = "محمد معلا";
        String key2 = "المفتاح العربي";
         String cipher2 = IDEA.encrypt16(text2, key2);
        String original2 = IDEA.decrypt16(cipher2, key2);
        System.out.println("text = "+text2);
        System.out.println("cipher = "+cipher2);
        System.out.println("original = "+original2);
       
    } 
}
