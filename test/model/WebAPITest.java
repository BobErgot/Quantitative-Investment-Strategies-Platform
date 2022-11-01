package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class WebAPITest {
  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void getShareValueByGivenDate() throws IOException {
    WebAPI webAPI = new WebAPI();
    Double value = webAPI.getShareValueByGivenDate("TSCO.LON",
            LocalDate.parse("2022-10-28"));
    assertTrue(value <= 216.6000 && value >= 212.900);
  }
}