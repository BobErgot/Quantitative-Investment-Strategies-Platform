package generalcontroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static utility.Constants.FILE_SEPARATOR;

import gui.GUIView;
import java.time.LocalDate;
import model.ModelImplementation;
import model.ModelInterface;
import org.junit.Test;

/**
 * Test for the general controllers class.
 */
public class GeneralControllerTest {

  @Test
  public void testValidCreatePortfolio() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    gc.createPortfolio("test123", PortfolioType.FIXED);
    assertEquals("createPortfolio,test123,2022-11-30", log.toString());
  }

  @Test
  public void testInvalidCreatePortfolio() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    try {
      gc.createPortfolio("", PortfolioType.FIXED);
      fail("Blank portfolio cannot be created");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testValidUploadPortfolio() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    gc.uploadPortfolio("example" + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "abc.csv");
    assertEquals("example,test,abc,csv", log.toString());

  }

  @Test
  public void testInvalidUploadPortfolio() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    try {
      gc.uploadPortfolio("");
      fail("Invalid path, but testcase still passed");
    } catch (StringIndexOutOfBoundsException e) {
      //
    }
  }

  @Test
  public void testInvalidPurchaseShareToCreatePortfolio() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    boolean sharePurchased = gc.purchaseShare("IBM", -1, LocalDate.parse("2022-02-02"));
    assertFalse(sharePurchased);
  }

  @Test
  public void testValidPurchaseShareToCreatePortfolio() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    gc.purchaseShare("IBM", 23, LocalDate.parse("2022-02-02"));
    assertEquals("IBM,2022-02-02,23,-1.0", log.toString());
  }

  @Test
  public void testValidSellShare() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    gc.sellShare("test123", "IBM", 20, LocalDate.parse("2022-02-02"));
    assertEquals("test123,IBM,20,2022-02-02", log.toString());
  }

  @Test
  public void testInvalidTickerNameSellShare() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    double output = gc.sellShare("test123", "xyzdoesntexist!", 20, LocalDate.parse("2022-02-02"));
    assertEquals(-1.0, output, 0.0);
  }

  @Test
  public void testInvalidNumStocksSellShare() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    double output = gc.sellShare("test123", "IBM", -20, LocalDate.parse("2022-02-02"));
    assertEquals(-1.0, output, 0.0);
  }

  @Test
  public void testValidGenerateCostBasis() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    double output = gc.generateCostBasis("test", LocalDate.parse("2022-02-02"));
    assertEquals("test,2022-02-02", log.toString());
  }

  @Test
  public void testInvalidGenerateCostBasis() {
    ModelInterface mockModel = new ModelImplementation();
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    try {
      gc.generateCostBasis("xyzdoesntexist", LocalDate.parse("2022-02-02"));
      fail("Portfolio doesnt exist and it still generates a costbasis.");
    } catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testValidGetValuation() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    double output = gc.getValuation("test", LocalDate.parse("2022-02-02"));
    assertEquals("test,2022-02-02", log.toString());
  }

  @Test
  public void testInvalidGetValuation() {
    StringBuilder log = new StringBuilder();
    ModelInterface mockModel = new MockModel(log);
    GeneralController gc = new GeneralController(mockModel);
    GUIView view = new MockView();
    gc.setView(view);
    try {
      gc.getValuation("xyzdoesntexist", LocalDate.parse("2022-02-02"));
      fail("Portfolio doesnt exist and it still generates a costbasis.");
    } catch (AssertionError e) {
      //
    }
  }
}