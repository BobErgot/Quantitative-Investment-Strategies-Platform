package controller;

import static controller.MockModels.MockModelUtil.compareStringContents;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.MockModels.MockModelAddShare;
import controller.MockModels.MockModelCreatePortfolio;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import model.ModelInterface;
import org.junit.Test;

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
    assertEquals("IBM,2022-11-03,23,-1.0", log.toString());
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
  // create portfolio tests

  // choice for create portfolio menu: valid/invalid

  @Test
  public void createValidPortfolio() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream in = new ByteArrayInputStream("1 1 IBM 25 2 testValid 4".getBytes());

    StringBuilder log = new StringBuilder();
    ModelInterface model = new MockModelCreatePortfolio(log);
    ControllerImpl controller = new ControllerImpl(in, out, model);
    controller.createPortfolio();
    System.out.println(bytes.toString());
    assertTrue(
        compareStringContents("Enter Company Name:Enter Number of shares:", bytes.toString()));
    assertEquals("IBM,2022-11-03,23,-1.0", log.toString());

  }
  // case 2 : add valid/existing/invalid portfolio name
  // case 3: go back

  //Show valuation test


  @Test
  public void showValuationOfPortfolio() {
  }

  @Test
  public void viewPortfolio() {
  }

  @Test
  public void uploadPortfolio() {
  }

  @Test
  public void go() {
  }
}