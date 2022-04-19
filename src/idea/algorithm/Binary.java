package idea.algorithm;
//Binary = 16 bit 
public class Binary {
int [] mat; 
String s ;
int len ;
public void init () {
    s = "";
    for (int i=0; i<mat.length; i++)
        s=s+String.valueOf(mat[i]);
    len=mat.length;
}
public Binary () { 
    mat = new int[8];
    init();
}
public Binary (int n){
    mat = new int[n];
    init();
}
public Binary (String s ){
     mat = new int[s.length()];
    for (int i=0; i<mat.length; i++)
        mat[i]=Integer.parseUnsignedInt(String.valueOf(s.charAt(i)));
    init();
}
public String get () {
    init();
    return s ;
}
public void set (String s){
    int i = s.length()-1;
    int j= mat.length-1;
    while (i>=0){
        String ss   = String.valueOf(s.charAt(i));
        mat[j]=Integer.parseInt(ss);
        i--;
        j--;
    }        
    init();
}
public void print () { 
    for (int i=0; i<mat.length; i++)
        System.out.print(mat[i]);
    System.out.println("");
      
}
public static Binary binary (long x){
    String s = Long.toString(x,2);
    Binary B = new Binary (16);
    B.set(s);
    return B;
}
public long decimal () { 
    
    return Long.parseUnsignedLong(s,2);
}
public String hex () {
    long x = this.decimal();
    int n = len /4;
    String H = Long.toHexString(x);
    for (int i=H.length(); i<n; i++)
        H= "0"+H;
    return H;
}
public static Binary merge (Binary A,Binary B){
    Binary C = new Binary(16);
    for (int i=0;i<8; i++)
        C.mat[i]=A.mat[i];
    for (int i=8;i<16; i++)
        C.mat[i]=B.mat[i-8];
    C.init();
    return C ;
    
}
public static Binary [] disjoin (Binary A){
    Binary [] Out = new Binary [2];
    String s = A.get();
    if (s.length() !=16)
        return null;
    String a =s.substring(0,8);
    String b= s.substring(8, 16);
    Out[0]=new Binary (a);
    Out[1]=new Binary(b);
    return Out;
}
public static Binary xor (Binary A , Binary B){
    
    Binary AA = new Binary (16);
    AA.set(A.get());
    Binary BB = new Binary (16);
    BB.set(B.get());
    Binary C = new Binary(16);
    for (int i=0;i<16; i++)
        if (AA.mat[i]==BB.mat[i])       C.mat[i]=0;
            else                        C.mat[i]=1;
    return C ;
}
public static Binary add (Binary A , Binary B){
    long a =  A.decimal();
    long b = B.decimal();
     long  big = (long)a+b;
    while (big >65535) //modulo 2^16
        big-=65536;  
    Binary C = new Binary(16);
    C.set(Binary.binary(big).get());
    return  C;  
}
public static Binary mul (Binary A , Binary B){
    long a = A.decimal();
    long b = B.decimal();
    // input zero ---> 2^16
    if (a==0)   a=65536; //2^16
    if (b==0)  b=65536; //2^16
    long  big = (long)a*b;
    
    while (big > 65536) //modulo2^(16+1)
        big-=65537;  
    
    // output 2^16 ---> 0
    if (big== 65536)  big=0; 
    Binary C = new Binary(16);
    C.set(Binary.binary(big).get());
    return  C; 
}
public Binary addInverse (){  // - X + X = 0 
    long x;
    if (this.decimal() !=0 )
     x= 65536 - this.decimal();
    else
         x=0;
    Binary temp = new Binary(16);
    Binary b = Binary.binary(x);
    temp.set(b.get());
    return temp;
}
public Binary mulInverse () { // X * (1/X) = 1
    //rabeeh algorithm
    /////////////////////////////////////////
    long a = this.decimal();
    long n = 65537;
    long b = n-2;
    long ret = 1 ;
    while (b>0){
        if (b % 2 == 1){
            ret*= a;
            ret%= n;
            }
        b/=2;
        a*=a;
        a%=n;
       }
    /////////////////////////////////////////
     long reverse = ret;  
    Binary temp = new Binary(16);
    temp.set(Binary.binary(reverse).get());
    return temp;
}
}
