package controller.commands;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import controller.StockPortfolioCommand;
import gui.HomeScreen;
import model.ModelInterface;

import static utility.Constants.FILE_SEPARATOR;

/**
 * Sends the path from where the portfolio needs to be uploaded to the model interface object
 * and notifies the user if something goes wrong during upload.
 */
public class UploadPortfolio implements StockPortfolioCommand {
  private String filePath;

  public UploadPortfolio(String filePath){
    this.filePath = filePath;
  }
  @Override
  public boolean process(ModelInterface model, HomeScreen View) {
      boolean validPath = false;
      int idx = filePath.lastIndexOf(FILE_SEPARATOR);
      String folderName = filePath.substring(0, idx);
      String[] file = filePath.substring(idx + 1).split("\\.");
      idx = folderName.lastIndexOf(FILE_SEPARATOR);
      String root = filePath.substring(0, idx);
      String folder = filePath.substring(idx + 1);
      idx = folder.lastIndexOf(FILE_SEPARATOR);
      folder = folder.substring(0, idx);
    try {
      validPath = model.addPortfolioByUpload(root, folder, file[0], file[1]);
    } catch (DataFormatException | FileNotFoundException e) {
      validPath = false;
    }
    return validPath;
  }

  @Override
  public void undo(ModelInterface model) {
    // Undo functionality from future use
  }
}