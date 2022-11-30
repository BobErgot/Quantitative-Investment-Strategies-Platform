package gui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;

public class ViewValidator {
  protected static boolean checkValidFile(String fileName, Set<String> supportedFileExtension){
    if(null == fileName || fileName.isEmpty()){
      return false;
    }
    String [] fileComponents = fileName.split("\\.");
    return fileComponents.length == 2 &&
            supportedFileExtension.contains(fileComponents[1].trim().toLowerCase());
  }

  protected static boolean checkValidDate(String date) {
    try {
      LocalDate localDate = LocalDate.parse(date);
      return localDate.isAfter(LocalDate.of(1949, 12, 31));
    } catch (DateTimeParseException dateError) {
      return false;
    }
  }

  static boolean checkValidStocks(String numStocks) {
    try {
      int numberOfStocks = Integer.parseInt(numStocks);
      return numberOfStocks > 0;
    } catch (NumberFormatException invalidStock) {
      return false;
    }
  }
}
