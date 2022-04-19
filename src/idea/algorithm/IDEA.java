package idea.algorithm;
public class IDEA {
    public static Word round (SubKey K ,Word X, int r ){
        Binary b1 = Binary.mul(X.X1,K.S1);
        Binary b2 = Binary.add(X.X2,K.S2);
        Binary b3 = Binary.add(X.X3,K.S3);
        Binary b4 = Binary.mul(X.X4,K.S4);
        /////////////////////////////////
        Binary b5 = Binary.xor(b1, b3);
        Binary b6 = Binary.xor(b2, b4);
        Binary b7 = Binary.mul(b5, K.S5);
        Binary b8 = Binary.add(b6, b7);
        Binary b9 = Binary.mul(b8, K.S6);
        Binary b10 = Binary.add(b7, b9);
        /////////////////////////////////
        Binary b11 = Binary.xor(b1, b9);
        Binary b12 = Binary.xor(b3, b9);
        Binary b13 = Binary.xor(b2, b10);
        Binary b14 = Binary.xor(b4, b10);
        /////////////////////////////////
        if (r ==8 ) 
            return  new Word (b11,b13,b12,b14);           
        else        
            return  new Word (b11,b12,b13,b14);               
    }
    public static Word halfround (SubKey K ,Word X){
        Binary b1 = Binary.mul(X.X1,K.S1);
        Binary b2 = Binary.add(X.X2,K.S2);
        Binary b3 = Binary.add(X.X3,K.S3);
        Binary b4 = Binary.mul(X.X4,K.S4);
          return  new Word (b1,b2,b3,b4);     
    }
    /**
     * use this to encrypt 16 bit characters like Arabic
     * @param text
     * @param key
     * @return 
     */
    public static String encrypt16 (String text , String key ){
         Word [] Words = Word.generate16(text);
         int n = Words.length;
         Key K = new Key(key,16);
         //initialize output 
         Binary [] Out = new Binary [n*4];
         for (int i=0; i<n*4 ; i++)
             Out[i]= new Binary (16);
         ////////////////
         int j = 0;
         for (int i=0; i <n; i++){
             Word X = Words[i];
             int s =1;
             // rounds from 1 -----> 8 
             for (int r = 1; r<=8; r++){    
                 SubKey SK = new SubKey(K.ENC[s],K.ENC[s+1],K.ENC[s+2],K.ENC[s+3],K.ENC[s+4],K.ENC[s+5]);
                 s+=6;
                 X= round(SK,X,r);
             }
             // round 9
             Binary T5 = new Binary (16); //nothing 
             Binary T6 = new Binary (16); //nothing 
             SubKey SK = new SubKey(K.ENC[s],K.ENC[s+1],K.ENC[s+2],K.ENC[s+3],T5,T6);
             X=halfround(SK, X); 
             Out[j]=X.X1; j++; 
             Out[j]=X.X2; j++; 
             Out[j]=X.X3; j++; 
             Out[j]=X.X4; j++; 
         }//end of for 
       String result = Convert.binaryToString(Out);
        return result;
    }
    /**
     * use this to decrypt 16 bit characters like Arabic
     * @param text
     * @param key
     * @return 
     */
    public static String decrypt16 (String text , String key ){
        
         Word [] Words = Word.generate16(text);
         int n = Words.length;
         Key K = new Key(key,16);
         //initialize output 
         Binary [] Out = new Binary [n*4];
         for (int i=0; i<n*4 ; i++)
             Out[i]= new Binary (16);
         ////////////////
         int j = 0;
         for (int i=0; i <n; i++){
             Word X = Words[i];
             int s =1;
             // rounds from 1 -----> 8 
             for (int r = 1; r<=8; r++){    
                 SubKey SK = new SubKey(K.DEC[s],K.DEC[s+1],K.DEC[s+2],K.DEC[s+3],K.DEC[s+4],K.DEC[s+5]);
                 s+=6;
                 X= round(SK,X,r);
             }
             // round 9
             Binary T5 = new Binary (16); //nothing 
             Binary T6 = new Binary (16); //nothing 
             SubKey SK = new SubKey(K.DEC[s],K.DEC[s+1],K.DEC[s+2],K.DEC[s+3],T5,T6);
             X=halfround(SK, X); 
             // 4 words = 8 binaries
             Out[j]=X.X1; j++; 
             Out[j]=X.X2; j++; 
             Out[j]=X.X3; j++; 
             Out[j]=X.X4; j++; 
         }//end of for 
         String result = Convert.binaryToString(Out);
        return result;
    }   
    
    /**
     * 
     *  use this to encrypt 8 bit characters like English
     * @param text
     * @param key
     * @return 
     */
    public static String encrypt8 (String text , String key ){
         Word [] Words = Word.generate8(text);
         int n = Words.length;
         Key K = new Key(key,8);
         //initialize output 
         Binary [] Out = new Binary [n*8];
         for (int i=0; i<n*8 ; i++)
             Out[i]= new Binary (8);
         ////////////////
         int j = 0;
         Word [] W = new Word [n];
         for (int i=0; i <n; i++){
             Word X = Words[i];
             int s =1;
             // rounds from 1 -----> 8 
             for (int r = 1; r<=8; r++){    
                 SubKey SK = new SubKey(K.ENC[s],K.ENC[s+1],K.ENC[s+2],K.ENC[s+3],K.ENC[s+4],K.ENC[s+5]);
                 s+=6;
                 X= round(SK,X,r);
             }
             // round 9
             Binary T5 = new Binary (16); //nothing 
             Binary T6 = new Binary (16); //nothing 
             SubKey SK = new SubKey(K.ENC[s],K.ENC[s+1],K.ENC[s+2],K.ENC[s+3],T5,T6);
             X=halfround(SK, X); 
             Out[j]=Binary.disjoin(X.X1)[0]; j++; 
             Out[j]=Binary.disjoin(X.X1)[1]; j++; 
             Out[j]=Binary.disjoin(X.X2)[0]; j++; 
             Out[j]=Binary.disjoin(X.X2)[1]; j++; 
             Out[j]=Binary.disjoin(X.X3)[0]; j++; 
             Out[j]=Binary.disjoin(X.X3)[1]; j++; 
             Out[j]=Binary.disjoin(X.X4)[0]; j++; 
             Out[j]=Binary.disjoin(X.X4)[1]; j++; 
             W[i]=X;
         }//end of for 
         //rebuild Words array 
       String result = Convert.binaryToString(Out);
        return result;
    }
    /**
     * use this to decrypt 8 bit characters like English
     * @param cipher
     * @param key
     * @return 
     */
    public static String decrypt8 (String cipher, String key ){
        
        Word [] Words = Word.generate8(cipher);
         int n = Words.length;
         Key K = new Key(key,8);
         //initialize output 
         Binary [] Out = new Binary [n*8];
         for (int i=0; i<n*8 ; i++)
             Out[i]= new Binary (8);
         ////////////////
         int j = 0;
         for (int i=0; i <n; i++){
             Word X = Words[i];
             int s =1;
             // rounds from 1 -----> 8 
             for (int r = 1; r<=8; r++){    
                 SubKey SK = new SubKey(K.DEC[s],K.DEC[s+1],K.DEC[s+2],K.DEC[s+3],K.DEC[s+4],K.DEC[s+5]);
                 s+=6;
                 X= round(SK,X,r);
             }
             // round 9
             Binary T5 = new Binary (16); //nothing 
             Binary T6 = new Binary (16); //nothing 
             SubKey SK = new SubKey(K.DEC[s],K.DEC[s+1],K.DEC[s+2],K.DEC[s+3],T5,T6);
             X=halfround(SK, X); 
             // 4 words = 8 binaries
             Out[j]=Binary.disjoin(X.X1)[0]; j++; 
             Out[j]=Binary.disjoin(X.X1)[1]; j++; 
             Out[j]=Binary.disjoin(X.X2)[0]; j++; 
             Out[j]=Binary.disjoin(X.X2)[1]; j++; 
             Out[j]=Binary.disjoin(X.X3)[0]; j++; 
             Out[j]=Binary.disjoin(X.X3)[1]; j++; 
             Out[j]=Binary.disjoin(X.X4)[0]; j++; 
             Out[j]=Binary.disjoin(X.X4)[1]; j++; 
         }//end of for 
         String result = Convert.binaryToString(Out);
        return result;
    }   
     
}