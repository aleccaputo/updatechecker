import org.apache.commons.codec.digest.*;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class UrlUpdateChecker{

   static Map<String, String> checkSumDB = new HashMap<String, String>();
   
   public static void main (String[] args) throws IOException, InterruptedException{
   
      String n = JOptionPane.showInputDialog("Please Enter a Url: ");
      if(!n.startsWith("http://") || !n.startsWith ("https://")){
         n = "http://" + n;
      }
      int updateCounter = 0;
      JFrame frame = new JFrame("Website Update!");
      
      while (true){
         String url = n;
         
         String lastChecksum = checkSumDB.get(url);
         
         String currentChecksum = getChecksumForURL(url);
         
         if (currentChecksum.equals(lastChecksum)){
            System.out.println("No Updates!");
            Thread.sleep(5000);
         }else{
            checkSumDB.put(url, currentChecksum);
            updateCounter ++; //excludes first check (obviously updated)
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

