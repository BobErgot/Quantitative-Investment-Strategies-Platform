package controller;

import static utility.Constants.FILE_SEPARATOR;
import static utility.Constants.RELATIVE_PATH;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.DuplicateFormatFlagsException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import model.ModelInterface;
import view.View;
import view.ViewImpl;

public class ControllerImpl implements Controller {

  View viewObject;
  ModelInterface modelObject;
  Scanner scanner;

  public ControllerImpl(InputStream in, PrintStream out, ModelInterface model) {
    this.scanner = new Scanner(in);
    this.viewObject = new ViewImpl(out);
    this.modelObject = model;
  }

  @Override
  public void createPortfolio() {
    boolean invalidInput;
    boolean portfolioCompleted;
    do {
      invalidInput = false;
      portfolioCompleted = false;
      boolean canCreateShare = modelObject.canCreateShare();
      viewObject.showCreatePortfolioMenu(canCreateShare);
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          this.addShareWithApiInput();
          break;
        case 2:
          if (canCreateShare) {
            boolean invalidPortfolioName = false;
            do {
              invalidPortfolioName = false;
              viewObject.askForPortfolioName();
              String portfolioName = scanner.next().trim();
              if (portfolioName.length() > 0 && !modelObject.idIsPresent(portfolioName)) {
                modelObject.createPortfolio(portfolioName, LocalDate.now());
                portfolioCompleted = true;
              } else {
                invalidPortfolioName = true;
              }
              if (invalidPortfolioName) {
                viewObject.printInvalidInputMessage();
              }
            } while (invalidPortfolioName);
          } else {
            invalidInput = true;
          }
          break;
        case 3:
          portfolioCompleted = true;
          break;
        default:
          invalidInput = true;
          break;
      }
      if (invalidInput) {
        viewObject.printInvalidInputMessage();
      }
    } while (invalidInput || !portfolioCompleted);
  }

  @Override
  public void addShareWithApiInput() {
    boolean isValidCompany;
    do {
      viewObject.showAddShareWithApiInputMenu(0);
      String companyName = scanner.next().trim();

      isValidCompany =
          companyName.length() > 0 && companyName.length() <= 10 && Character.isAlphabetic(
              companyName.charAt(0)) && modelObject.checkTicker(companyName);

      if (isValidCompany) {
        viewObject.showAddShareWithApiInputMenu(1);
        int numShares = scanner.nextInt();
        if (numShares <= 0) {
          viewObject.printInvalidInputMessage();
          isValidCompany = false;
        } else {
          try {
            boolean companyAddedWithoutChange = modelObject.addShareToModel(companyName,
                LocalDate.now(), numShares, -1);
            if (!companyAddedWithoutChange) {
              viewObject.printCompanyStockUpdated();
            }
          } catch (IllegalArgumentException illegalArgumentException) {
            isValidCompany = false;
            viewObject.developmentInProgress();
          }
        }

      } else {
        viewObject.notPresentError("Company");
      }
    } while (!isValidCompany);
  }

  @Override
  public boolean showValuationOfPortfolio(String selectedId) {
    if (!modelObject.idIsPresent(selectedId)) {
      return false;
    }
    boolean invalidDate = false;
    LocalDate date;
    String stockDate;
    do {
      while (true) {
        try {
          viewObject.askForDate();
          stockDate = scanner.next();
          invalidDate = !(Pattern.matches("\\d{4}-\\d{2}-\\d{2}", stockDate));
          date = LocalDate.parse(stockDate);
          break;
        } catch (DateTimeParseException dtp) {
          viewObject.printInvalidDateError();
        }

      }
      invalidDate =
          invalidDate && date.isAfter(LocalDate.of(1949, 12, 31)) && date.isBefore(LocalDate.now());
      if (invalidDate) {
        viewObject.printInvalidInputMessage();
      }
    } while (invalidDate);
    viewObject.showValuation(modelObject.getValuationGivenDate(selectedId, date));
    return true;
  }

  @Override
  public void viewPortfolio() {
    viewObject.showViewPortfolioMenu(modelObject.getPortfolio());
    viewObject.showAdditionalPortfolioInformation();
    int choice = scanner.nextInt();
    if (choice == 1) {
      boolean flag = false;
      do {
        viewObject.selectPortfolio();
        String selectedId = scanner.next().trim();
        flag = showValuationOfPortfolio(selectedId);
      } while (!flag);
    }
  }

  @Override
  public void uploadPortfolio() {
    boolean validPath = false;
    do {
      viewObject.showUploadPortfolioOptions();
      // if portfolio successfully uploaded , it will return true.
      viewObject.uploadPath();
      int choice = scanner.nextInt();
      if (choice == 1 || choice == 2) {
        // Relative zenith/harshit/bob.txt
        viewObject.enterPath();
        String str = scanner.next();
        int idx = str.lastIndexOf(FILE_SEPARATOR);
        String folderName = str.substring(0, idx);
        String[] file = str.substring(idx + 1).split("\\.");
        try {
          if (choice == 1) {
            idx = folderName.lastIndexOf(FILE_SEPARATOR);
            String root = str.substring(0, idx);
            String folder = str.substring(idx + 1);
            idx = folder.lastIndexOf(FILE_SEPARATOR);
            folder = folder.substring(0, idx);
            validPath = modelObject.addPortfolioByUpload(root, folder, file[0], file[1]);
          } else {
            validPath = modelObject.addPortfolioByUpload(RELATIVE_PATH, folderName, file[0],
                file[1]);

          }
        } catch (FileNotFoundException e) {
          viewObject.notPresentError("File");

        } catch (DataFormatException d) {
          viewObject.printInvalidDateError();
        } catch (DuplicateFormatFlagsException exceptionMessage) {
          viewObject.printException(exceptionMessage.getMessage());
        } catch (IllegalArgumentException exception) {
          viewObject.printException(exception.getMessage());
        }
      } else if (choice == 3) {
        return;
      } else {
        viewObject.printInvalidInputMessage();
      }
    } while (!validPath);
  }

  @Override
  public void go() {
    boolean invalidInput;
    boolean haveUserContinue;

    do {
      invalidInput = false;
      haveUserContinue = true;

      viewObject.showMainMenu();
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          this.createPortfolio();
          break;
        case 2:
          this.uploadPortfolio();
          break;
        case 3:
          this.viewPortfolio();
          break;
        case 4:
          haveUserContinue = false;
          break;
        default:
          invalidInput = true;
          break;
      }
      if (invalidInput) {
        viewObject.printInvalidInputMessage();
      }
    } while (invalidInput || haveUserContinue);
  }
}