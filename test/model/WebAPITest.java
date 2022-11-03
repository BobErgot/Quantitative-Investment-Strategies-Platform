package model;

import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class WebAPITest {
  @Test
  public void getShareValueByGivenDate() throws IOException {
    WebAPI webAPI = new WebAPI();
    List<String> value = webAPI.getData("TSCO.LON", LocalDate.parse("2022-10-28"));
  }
}