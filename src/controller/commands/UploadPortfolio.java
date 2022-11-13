package controller.commands;

import java.io.FileNotFoundException;
import java.util.DuplicateFormatFlagsException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import controller.StockPortfolioCommand;
import model.ModelInterface;
import view.View;

import static utility.Constants.FILE_SEPARATOR;
import static utility.Constants.RELATIVE_PATH;

public class UploadPortfolio implements StockPortfolioCommand {

  /**
   * Sends the path from where the portfolio needs to be uploaded to the model interface object
   * and notifies the user if something goes wrong during upload.
   */
  @Override
  public void process(View view, Scanner scanner, ModelInterface model) {
    boolean validPath = false;
    do {
      view.showUploadPortfolioOptions();
      // if portfolio successfully uploaded , it will return true.
      view.uploadPath();
      int choice = scanner.nextInt();
      if (choice == 1 || choice == 2) {
        view.enterPath();
        String str = scanner.next();
        try {
          int idx = str.lastIndexOf(FILE_SEPARATOR);
          String folderName = str.substring(0, idx);
          String[] file = str.substring(idx + 1).split("\\.");

          if (choice == 1) {
            idx = folderName.lastIndexOf(FILE_SEPARATOR);
            String root = str.substring(0, idx);
            String folder = str.substring(idx + 1);
            idx = folder.lastIndexOf(FILE_SEPARATOR);
            folder = folder.substring(0, idx);
            validPath = model.addPortfolioByUpload(root, folder, file[0], file[1]);
          } else {
            validPath = model.addPortfolioByUpload(RELATIVE_PATH, folderName, file[0],
                    file[1]);
          }
        } catch (FileNotFoundException e) {
          view.notPresentError("File");

        } catch (DataFormatException d) {
          view.printInvalidDateError();
        } catch (DuplicateFormatFlagsException exceptionMessage) {
          view.printException(exceptionMessage.getMessage());
        } catch (IllegalArgumentException exception) {
          view.printException(exception.getMessage());
        } catch (StringIndexOutOfBoundsException dateEx) {
          view.printInvalidInputMessage();
        }
      } else if (choice == 3) {
        return;
      } else {
        view.printInvalidInputMessage();
      }
    }
    while (!validPath);
  }

  @Override
  public void undo(ModelInterface model) {
  }
}