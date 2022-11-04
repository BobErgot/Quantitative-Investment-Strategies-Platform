package controller;

import static controller.MockModelUtil.compareStringContents;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utility.Constants.FILE_SEPARATOR;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import model.ModelInterface;
import org.junit.Test;

/**
 * Controller Tests to check functioning of controller in MVC.
 */
public class ControllerImplTest {

  // Enter company & numshares: Valid
  @Test
  public void addValidShareWithApiInput() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("IBM 23".getBytes());

    StringBuilder log = new StringBuilder();

    ModelInterface model = new MockModelAddShare(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    controller.addShareWithApiInput();
    assertTrue(
        compareStringContents("Enter Company Name:Enter Number of shares:", bytes.toString()));
    assertEquals("IBM," + LocalDate.now() + ",23,-1.0", log.toString());
  }

  // Enter company: Invalid Blank
  @Test
  public void addInValidCompanyShareWithApiInput() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("NotIBM".getBytes());

    StringBuilder log = new StringBuilder();

    ModelInterface model = new MockModelAddShare(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    try {
      controller.addShareWithApiInput();
    } catch (Exception e) {
      //
    }
    assertTrue(compareStringContents(
        "Enter Company Name:Company is not present, please enter again!Enter Company Name:",
        bytes.toString()));
    assertEquals(0, log.toString().length());
  }

  // Enter company: Blank
  @Test
  public void addBlankCompanyShareWithApiInput() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("".getBytes());

    StringBuilder log = new StringBuilder();

    ModelInterface model = new MockModelAddShare(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    try {
      controller.addShareWithApiInput();
    } catch (Exception e) {
      //
    }
    assertTrue(compareStringContents("Enter Company Name:", bytes.toString()));
    assertEquals(0, log.toString().length());
  }

  // enter num shares: negative
  @Test
  public void addNegativeShareWithApiInput() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("IBM -23".getBytes());

    StringBuilder log = new StringBuilder();

    ModelInterface model = new MockModelAddShare(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    try {
      controller.addShareWithApiInput();
    } catch (Exception e) {
      //
    }
    assertTrue(compareStringContents(
        "Enter Company Name:Enter Number of shares:Invalid Input!Enter Company Name:",
        bytes.toString()));
    assertEquals(0, log.toString().length());
  }

  // enter num shares: zero
  @Test
  public void addZeroShareWithApiInput() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("IBM 0".getBytes());

    StringBuilder log = new StringBuilder();

    ModelInterface model = new MockModelAddShare(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    try {
      controller.addShareWithApiInput();
    } catch (Exception e) {
      //
    }
    assertTrue(compareStringContents(
        "Enter Company Name:Enter Number of shares:Invalid Input!Enter Company Name:",
        bytes.toString()));
    assertEquals(0, log.toString().length());
  }

  // choice for create portfolio menu: valid/invalid
  @Test
  public void selectCorrectChoiceInPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("1".getBytes());

    StringBuilder log = new StringBuilder();
    ModelInterface model = new MockModelCreatePortfolio(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    try {
      controller.createPortfolio();
    } catch (NoSuchElementException ne) {
      //
    }
    assertTrue(compareStringContents(
        "Please select an option:\n" + "1. Add Shares\n" + "3. Go back.\n" + "Enter Company Name:",
        bytes.toString()));
    assertEquals(0, log.toString().length());

  }

  @Test
  public void selectInorrectChoiceInPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("5".getBytes());

    StringBuilder log = new StringBuilder();
    ModelInterface model = new MockModelCreatePortfolio(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    try {
      controller.createPortfolio();
    } catch (NoSuchElementException ne) {
      //
    }
    assertTrue(compareStringContents(
        "Please select an option:\n" + "1. Add Shares\n" + "3. Go back.\n" + "Invalid Input!\n"
            + "Please select an option:\n" + "1. Add Shares\n" + "3. Go back.", bytes.toString()));
    assertEquals(0, log.toString().length());

  }

  // case 2 : add valid portfolio name
  @Test
  public void createValidPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("1 IBM 25 2 valid_portfolio_name 4".getBytes());

    StringBuilder log = new StringBuilder();
    ModelInterface model = new MockModelCreatePortfolio(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    controller.createPortfolio();
    assertTrue(compareStringContents(
        "Please select an option:\n" + "1. Add Shares\n" + "3. Go back.\n" + "Enter Company Name:\n"
            + "Enter Number of shares:\n" + "Please select an option:\n" + "1. Add Shares\n"
            + "2. Create Portfolio (Finalize current Portfolio)\n" + "3. Go back.\n"
            + "Please write down the unique name of portfolio", bytes.toString()));
    assertEquals("createPortfolio,valid_portfolio_name," + LocalDate.now(), log.toString());

  }

  // case 2 : add existing portfolio name
  @Test
  public void createExistingPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("1 IBM 25 2 repeated_portfolio_name 4".getBytes());

    ModelInterface model = new MockModelCreatePortfolio(new StringBuilder());
    ControllerImpl controller = new ControllerImpl(in, out, model);
    controller.createPortfolio();

    StringBuilder log = new StringBuilder();
    model = new MockModelCreatePortfolio(log);

    out = new PrintStream(bytes);
    in = new ByteArrayInputStream("1 IBM 25 2 repeated_portfolio_name 4".getBytes());
    controller = new ControllerImpl(in, out, model);
    controller.createPortfolio();
    assertTrue(compareStringContents(
        "Please select an option:\n" + "1. Add Shares\n" + "3. Go back.\n" + "Enter Company Name:\n"
            + "Enter Number of shares:\n" + "Please select an option:\n" + "1. Add Shares\n"
            + "2. Create Portfolio (Finalize current Portfolio)\n" + "3. Go back.\n"
            + "Please write down the unique name of portfolio\n" + "Please select an option:\n"
            + "1. Add Shares\n" + "3. Go back.\n" + "Enter Company Name:\n"
            + "Enter Number of shares:\n" + "Please select an option:\n" + "1. Add Shares\n"
            + "2. Create Portfolio (Finalize current Portfolio)\n" + "3. Go back.\n"
            + "Please write down the unique name of portfolio", bytes.toString()));
    assertEquals("createPortfolio,repeated_portfolio_name," + LocalDate.now(), log.toString());

  }
  // case 3: go back

  //Show valuation test valid

  @Test
  public void showValidValuationOfPortfolio() {

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("2021-11-03".getBytes());

    StringBuilder log = new StringBuilder();
    ModelInterface model = new MockModelShowValuation(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    controller.showValuationOfPortfolio("testing");
    assertTrue(compareStringContents(
        "Please enter date in yyyy-mm-dd format.\n" + "Valuation of Portfolio is:\t0.0",
        bytes.toString()));
    assertEquals("testing,2021-11-03", log.toString());
  }
  //Show valuation test invalid date

  @Test
  public void showInvalidValuationOfPortfolio() {

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("2021-1".getBytes());

    StringBuilder log = new StringBuilder();
    ModelInterface model = new MockModelShowValuation(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    try {
      controller.showValuationOfPortfolio("testing");
    } catch (NoSuchElementException e) {
      //
    }
    assertTrue(compareStringContents(
        "Please enter date in yyyy-mm-dd format.\n" + "Invalid date format!\n"
            + "Please enter date in yyyy-mm-dd format.", bytes.toString()));
    assertEquals(0, log.toString().length());
  }

  @Test
  public void viewPortfolioCancelNext() {

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("0".getBytes());

    StringBuilder log = new StringBuilder();
    ModelInterface model = new MockModelShowValuation(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    controller.viewPortfolio();
    assertTrue(compareStringContents(
        "Portfolios available:\n" + "-------------------------------------------------------\n"
            + "+id:testing\n" + "creationDate:2022-11-03\n"
            + "*shares:+companyName:IBM,purchaseDate:2022-11-03,price:138.485,numShares:20|"
            + "+companyName:ZZZ,purchaseDate:2022-11-03,price:0.01,numShares:1000\n"
            + "-------------------------------------------------------\n" + "\n"
            + "Do you want to see the valuation of this portfolio? (1 for yes)", bytes.toString()));
  }

  // upload valid portfolio
  @Test
  public void uploadValidPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream(
        ("2 portfoliotest" + FILE_SEPARATOR + "testingportfoliovalid.csv").getBytes());

    StringBuilder log = new StringBuilder();
    ModelInterface model = new MockModelUpload(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    controller.uploadPortfolio();
    assertTrue(compareStringContents("\n" + "Write path to portfolio:\n"
            + "How do you want to upload this data? (Write your path sperated by \\\n"
            + "1. Absolute Path\n" + "2. Relative Path\n" + "3. Go back\n" + "Enter path:",
        bytes.toString()));
    assertEquals("~,portfoliotest,testingportfoliovalid,csv", log.toString());

  }

  // upload non-existent portfolio
  @Test
  public void uploadInvalidPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream(
        ("2 portfoliotest/testingportfoliovalid.csv").getBytes());

    StringBuilder log = new StringBuilder();
    ModelInterface model = new MockModelUpload(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    try {
      controller.uploadPortfolio();
    } catch (Exception e) {
      // Test Passed
    }
    assertTrue(compareStringContents("Write path to portfolio:\n"
        + "How do you want to upload this data? (Write your path sperated by \\\n"
        + "1. Absolute Path\n" + "2. Relative Path\n" + "3. Go back\n" + "Enter path:\n"
        + "Invalid Input!\n" + "Write path to portfolio:\n"
        + "How do you want to upload this data? (Write your path sperated by \\\n"
        + "1. Absolute Path\n" + "2. Relative Path\n" + "3. Go back", bytes.toString()));
    assertEquals(0, log.toString().length());
  }

  // main function with valid inputs
  @Test
  public void goValid() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream(("1 3 4").getBytes());
    // can be any mock model
    ModelInterface model = new MockModelUpload(new StringBuilder());
    ControllerImpl controller = new ControllerImpl(in, out, model);
    controller.start();
    assertTrue(compareStringContents(
        "Please select an option from 1-x from the main menu\n" + "Main.Main Menu:\n"
            + "1. Create Portfolio\n" + "2. Upload Portfolio from given path.\n"
            + "3. View Portfolio\n" + "4. Exit\n" + "Please select an option:\n" + "1. Add Shares\n"
            + "3. Go back.\n" + "Please select an option from 1-x from the main menu\n"
            + "Main.Main Menu:\n" + "1. Create Portfolio\n" + "2. Upload Portfolio from given path.\n"
            + "3. View Portfolio\n" + "4. Exit", bytes.toString()));
  }

  // main function with invalid inputs
  @Test
  public void goInvalid() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream(("876 4").getBytes());
    // can be any mock model
    ModelInterface model = new MockModelUpload(new StringBuilder());
    ControllerImpl controller = new ControllerImpl(in, out, model);
    controller.start();
    assertTrue(compareStringContents(
        "Please select an option from 1-x from the main menu\n" + "Main.Main Menu:\n"
            + "1. Create Portfolio\n" + "2. Upload Portfolio from given path.\n"
            + "3. View Portfolio\n" + "4. Exit\n" + "Invalid Input!\n"
            + "Please select an option from 1-x from the main menu\n" + "Main.Main Menu:\n"
            + "1. Create Portfolio\n" + "2. Upload Portfolio from given path.\n"
            + "3. View Portfolio\n" + "4. Exit", bytes.toString()));
  }
}