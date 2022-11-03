package model;

import java.io.IOException;
import java.time.LocalDate;
import org.junit.Test;

/**
 * Class to test WebAPI functionality.
 */
public class WebAPITest {

  @Test
  public void getShareValueByGivenDate() throws IOException {
    WebAPI webAPI = new WebAPI();
    String value = webAPI.getData("TSCO.LON", LocalDate.parse("2022-10-28"));
  }
}