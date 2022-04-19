package idea.algorithm;

public class Key {
    private Binary [] K ; //8 keys //16 bit each 
    String key ; //128 items 
    Binary mat; //128 items 
    Binary [] ENC ; //52 subkey for encrypting 
    Binary [] DEC ; //52 subkey for decrypting 
   
    Key  (String temp , int b )  {
         K = new Binary[9];
        //there is no index 0
        for (int i=1; i<=8; i++)
            K[i]=new Binary(16);
        ///////////////////////////
        Binary [] A16 =null;
        if (b==8){
            int n =16;
            int m = 8; 
            Binary [] A8 = Convert.stringToBinary8(temp); 
            A16 = new Binary[m];
            int j=0;
            for (int i=0; i<n-1; i+=2){
                A16[j]=Binary.merge(A8[i],A8[i+1]);
                j++;
            }
        }else if (b==16){
            A16 = Convert.stringToBinary16(temp); 
            //generate all keys  K[i] from K[1] ------> K[8] 
        }
          for (int i=1; i<=8; i++)
                K[i].set(A16[i-1].get());
        //generate string //128 bits
         key ="";
        for (int i=1; i<=8; i++)
            key+=K[i].get();
        // generate matrix //128 bits
        mat = new Binary(key);
        //generate ENC SUB KEYS //52 elements
        generateENC ();
        //generate DEC SUB KEYS from ENC //52 elements
        generateDEC ();   
  
        }
       
    
    void reset () { // after SHIFTING 
        //String generate from mat 
        key = mat.get();
        int j =0;
        for (int i=1; i<=8; i++){
            String t = "";
            for (int k=1; k<=16;k++){
                t+=key.charAt(j);
                j++;
            }
            K[i]= new Binary(16);
            K[i].set(t);
        }//end of for         
    }
       void leftShift () { 
       //left shift 25 bits to left 
       Binary temp = new Binary (128);
       //first step
       for (int i=25; i<128; i++)
           temp.mat[i-25]=mat.mat[i];
       //second step
       for (int i=0; i<25; i++)
           temp.mat[i+103]=mat.mat[i];
       //save the new key in mat
      mat= new Binary(temp.get());
       reset();
   }
    void generateENC () { 
        ENC = new Binary[53];
        //there is no index 0 
        for (int i=1; i<=52; i++)
            ENC[i]=new Binary(16);
        // K1 -------> K8
        for (int i=1; i<=8; i++)
            ENC[i].set(K[i].get());
        //LS #1
        leftShift();    
        // K9 -------> K16
        for (int i=9; i<=16; i++)
            ENC[i].set(K[i-8].get());
        //LS #2
        leftShift();
        // K17 -------> K24
        for (int i=17; i<=24; i++)
            ENC[i].set(K[i-16].get());
        //LS #3
        leftShift();
        // K25 -------> K32
        for (int i=25; i<=32; i++)
            ENC[i].set(K[i-24].get());
        //LS #4
        leftShift();
        // K33 -------> K40
        for (int i=33; i<=40; i++)
            ENC[i].set(K[i-32].get());
        //LS #5
        leftShift();
        // K41 -------> K48
        for (int i=41; i<=48; i++)
            ENC[i].set(K[i-40].get());
        //LS #6
        leftShift();
        // K49 -------> K52
        for (int i=49; i<=52; i++)
            ENC[i].set(K[i-48].get());
    }
    void generateDEC () {
         DEC = new Binary[53];
        //there is no index 0 
        for (int i=1; i<=52; i++)
            DEC[i]=new Binary(16);
        //round 1 
        DEC[1]=ENC[49].mulInverse();
        DEC[2]=ENC[50].addInverse();
        DEC[3]=ENC[51].addInverse();
        DEC[4]=ENC[52].mulInverse();
        DEC[5]=ENC[47];
        DEC[6]=ENC[48];
        ///////////////////////////////////
        //round 2 
        DEC[7]= ENC[43].mulInverse();
        DEC[8]= ENC[45].addInverse();
        DEC[9]= ENC[44].addInverse();
        DEC[10]=ENC[46].mulInverse();
        DEC[11]=ENC[41];
        DEC[12]=ENC[42];
        ///////////////////////////////////
        //round 3 
        DEC[13]=ENC[37].mulInverse();
        DEC[14]=ENC[39].addInverse();
        DEC[15]=ENC[38].addInverse();
        DEC[16]=ENC[40].mulInverse();
        DEC[17]=ENC[35];
        DEC[18]=ENC[36];
        ///////////////////////////////////
        //round 4 
        DEC[19]=ENC[31].mulInverse();
        DEC[20]=ENC[33].addInverse();
        DEC[21]=ENC[32].addInverse();
        DEC[22]=ENC[34].mulInverse();
        DEC[23]=ENC[29];
        DEC[24]=ENC[30];
        ///////////////////////////////////
        //round 5
        DEC[25]=ENC[25].mulInverse();
        DEC[26]=ENC[27].addInverse();
        DEC[27]=ENC[26].addInverse();
        DEC[28]=ENC[28].mulInverse();
        DEC[29]=ENC[23];
        DEC[30]=ENC[24];
        ///////////////////////////////////
        //round 6 
        DEC[31]=ENC[19].mulInverse();
        DEC[32]=ENC[21].addInverse();
        DEC[33]=ENC[20].addInverse();
        DEC[34]=ENC[22].mulInverse();
        DEC[35]=ENC[17];
        DEC[36]=ENC[18];
        ///////////////////////////////////
        //round 7
        DEC[37]=ENC[13].mulInverse();
        DEC[38]=ENC[15].addInverse();
        DEC[39]=ENC[14].addInverse();
        DEC[40]=ENC[16].mulInverse();
        DEC[41]=ENC[11];
        DEC[42]=ENC[12];
        ///////////////////////////////////
        //round 8
        DEC[43]=ENC[7].mulInverse();
        DEC[44]=ENC[9].addInverse();
        DEC[45]=ENC[8].addInverse();
        DEC[46]=ENC[10].mulInverse();
        DEC[47]=ENC[5];
        DEC[48]=ENC[6];
        ///////////////////////////////////
        //round 9
        DEC[49]=ENC[1].mulInverse();
        DEC[50]=ENC[2].addInverse();
        DEC[51]=ENC[3].addInverse();
        DEC[52]=ENC[4].mulInverse();
        ///////////////////////////////////
    }
   void printENC (){ 
       for (int i=1; i <=52; i++){
           System.out.print("ENC["+i+"] = "+ENC[i].hex()+"   ");
           if (i % 8 ==0)System.out.println("");
       }
           System.out.println("");  
   }
   void printDEC (){ 
       for (int i=1; i <=52; i++){
           System.out.print("DEC["+i+"] = "+DEC[i].hex()+"   ");
           if (i % 6 ==0)System.out.println("");
       }
           System.out.println("");   
   } 
}