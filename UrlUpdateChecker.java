import org.apache.commons.codec.digest.*;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class UrlUpdateChecker{

   static Map<String, String> checkSumDB = new HashMap<String, String>();
   
   public static void main (String[] args) throws IOException, InterruptedException{
      JFrame frame = new JFrame("Website Update!");
   
      String n = JOptionPane.showInputDialog("Please Enter a Url: ");
      if(!n.startsWith("http")){ //check if http is there and adds it if not
         
         n = "http://" + n;
      }
      System.out.println(n);
      
      if (n.equals("http://google.com")|| n.equals("http://www.google.com")||n.equals("https://google.com")||n.equals("http://www.google.com")) {
          JOptionPane.showMessageDialog(frame,""+n+" is always updating!");
          System.exit(0);
      }
      
      int updateCounter = 0;
      
      while (true){
         String url = n;
         
         String lastChecksum = checkSumDB.get(url);
         
         String currentChecksum = getChecksumForURL(url);
         
         if (currentChecksum.equals(lastChecksum)){
            System.out.println("No Updates!");
            Thread.sleep(5000);
         }else{
            checkSumDB.put(url, currentChecksum);
            updateCounter ++; //excludes first check (Code considers it update...fix this)
            if (updateCounter > 1){
               JOptionPane.showMessageDialog(frame,"There's been an update for: "+url);
               System.exit(0);
            }
            
         }
         
      }
}

   private static String getChecksumForURL(String spec) throws IOException{
      URL u = new URL (spec);
      HttpURLConnection huc = (HttpURLConnection) u.openConnection();
      huc.setRequestMethod("GET");
      huc.setDoOutput(true);
      huc.connect();
      return DigestUtils.sha256Hex(huc.getInputStream());
   }
}
