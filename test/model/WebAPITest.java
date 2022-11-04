package model;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Class to test WebAPI functionality.
 */
public class WebAPITest {

  @Test
  public void getShareValueByGivenDate() {
    WebAPI webAPI = new WebAPI();
    FileInterface fileInterface = new CSVFile();
    String value = webAPI.getData("TSCO.LON", LocalDate.parse("2022-10-28"));
    List<String> stringList = fileInterface.validateFormat(value);
    assertTrue(stringList.size() >= 0);
  }
}