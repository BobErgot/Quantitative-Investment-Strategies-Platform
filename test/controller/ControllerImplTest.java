package controller;

import static controller.MockModelUtil.compareStringContents;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import model.ModelInterface;
import org.junit.Test;

/**
 * Controller Tests to check functioning of controller in MVC.
 */
public class ControllerImplTest {

  @Test
  public void invalidDate() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream(("3 2 testabc 2/0/201").getBytes());
    // can be any mock model
    ModelInterface model = new MockModelUpload(new StringBuilder());
    model.createPortfolio("testabc", LocalDate.now());
    ControllerImpl controller = new ControllerImpl(in, out, model);
    try {
      controller.start();
    } catch (Exception e) {
      fail("Test threw an error");
    }
    assertTrue(
        compareStringContents("* Home Menu:\n"
            + "1. Create Portfolio\n"
            + "2. Upload Portfolio from given path\n"
            + "3. View Portfolio\n"
            + "Type 'quit' or 'exit' to close the program\n"
            + "Please select an option from 1-x from above: 3\n"
            + "\n"
            + "Portfolios available:\n"
            + "-----------------------------------------------------------------"
            + "-------------------\n"
            + "||Serial Number     ||Portfolio Name                    "
            + "      ||Creation Date     ||\n"
            + "||------------------||---------------------------------------"
            + "-||------------------||\n"
            + "||0.                ||Test                                "
            + "    ||2022-11-17        ||\n"
            + "--------------------------------------------------------------"
            + "----------------------\n"
            + "\n"
            + "* View Portfolio Menu:\n"
            + "1. View composition of a particular portfolio\n"
            + "2. Valuation of Portfolio on a specific date\n"
            + "3. Cost basis of a portfolio till a specific date\n"
            + "4. Purchase a share and add to portfolio\n"
            + "5. Sell a share from portfolio\n"
            + "6. Performance of portfolio over time\n"
            + "Type 'back' to return to Home Menu\n"
            + "Please select an option from 1-x from above: 2\n"
            + "\n"
            + "Select a 'Portfolio Name' from above list:\tTest\n"
            + "\n"
            + "Please enter date in yyyy-mm-dd format.2020/20/20\n"
            + "\n"
            + "Invalid date format!\n"
            + "Please enter date in yyyy-mm-dd format.", bytes.toString()));
  }

  // main function with valid inputs
  @Test
  public void goInvalid() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream(("876 4").getBytes());
    // can be any mock model
    ModelInterface model = new MockModelUpload(new StringBuilder());
    ControllerImpl controller = new ControllerImpl(in, out, model);
    try {
      controller.start();
    } catch (Exception e) {
      fail("Test threw an error");
    }
  }
}
