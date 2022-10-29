package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class FileDatabase implements APIInterface {
  // Import the IOException class to handle errors
  public boolean writeToFile(String filePrefix, String folderName, String args) {
    boolean flag = true;
    try {
      String filename = filePrefix+UUID.randomUUID().toString()+".txt";
      FileWriter myWriter = new FileWriter(folderName + "/"+filename);
      myWriter.write(args);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      flag = false;
    }
    return flag;
  }

  @Override
  public double getShareValueByGivenDate(String stockSymbol, Date date) {
    return 0;
  }

}
