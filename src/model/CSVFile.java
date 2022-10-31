package model;

import java.util.Date;
import java.util.List;

public class CSVFile extends FileAbstract {
  private final static String EXTENSION = "csv";

  public String getFileExtension(){
    return EXTENSION;
  }
}
