package gui;

import static gui.ViewValidator.checkValidFile;
import static gui.ViewValidator.showErrorMessage;
import static gui.ViewValidator.showInformationMessage;
import static gui.utility.ViewConstants.FILE_UPLOAD_ANOTHER;
import static gui.utility.ViewConstants.FILE_UPLOAD_FAIL_EXTENSION;
import static gui.utility.ViewConstants.FILE_UPLOAD_FAIL_FORMAT;
import static gui.utility.ViewConstants.FILE_UPLOAD_FAIL_NOT_FOUND;
import static gui.utility.ViewConstants.FILE_UPLOAD_SUCCESS;
import static gui.utility.ViewConstants.NO_FILES_SELECTED;
import static gui.utility.ViewConstants.SUPPORTED_FILES;
import static gui.utility.ViewConstants.SUPPORTED_FILE_EXTENSION;
import static gui.utility.ViewConstants.UPLOAD_ANOTHER_FILE;

import generalcontroller.Features;
import java.nio.file.Path;
import java.util.DuplicateFormatFlagsException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * GUI Swing to support uploading portfolios from file system.
 */
public class UploadPortfolioPanel extends JPanel {

  private JPanel applicationJPanel;
  private JButton browseFileJButton;
  private JButton uploadButton;
  private JLabel pathSelectedJLabel;
  private Path filePath;

  /**
   * Constructor to support upload portfolio class.
   *
   * @param features Features accessible to action listeners.
   */
  public UploadPortfolioPanel(Features features) {
    this.add(applicationJPanel);
    this.enableButtonEvents(features);
    this.enableValidations(features);
  }

  private void enableButtonEvents(Features features) {
    uploadButton.addActionListener(event -> uploadPortfolio(features));
    browseFileJButton.addActionListener(event -> browseFiles());
  }

  private void enableValidations(Features features) {
    //
  }

  private void browseFiles() {
    JFileChooser jFileChooser = new JFileChooser();
    int selectedFile = jFileChooser.showOpenDialog(null);
    if (selectedFile != JFileChooser.APPROVE_OPTION) {
      return;
    }
    filePath = jFileChooser.getSelectedFile().toPath();
    String fileName = filePath.getFileName().toString();
    if (checkValidFile(fileName, SUPPORTED_FILE_EXTENSION)) {
      pathSelectedJLabel.setText(filePath.getFileName().toString());
      pathSelectedJLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
      uploadButton.setEnabled(true);
      browseFileJButton.setText(FILE_UPLOAD_ANOTHER);
    } else {
      errorPathSelectedLabel(
          FILE_UPLOAD_FAIL_EXTENSION + SUPPORTED_FILES + SUPPORTED_FILE_EXTENSION);
    }
  }

  private void uploadPortfolio(Features features) {
    Path filePath = this.filePath;
    String absoluteFilePath = null;
    if (filePath != null && !filePath.getFileName().toString().isEmpty()) {
      absoluteFilePath = filePath.toAbsolutePath().toString();
    }
    try {
      int status = features.uploadPortfolio(absoluteFilePath);
      if (status == 0) {
        clearPathSelectedLabel();
      } else if (status == 1) {
        errorPathSelectedLabel(FILE_UPLOAD_FAIL_FORMAT);
      } else if (status == 2) {
        errorPathSelectedLabel(FILE_UPLOAD_FAIL_NOT_FOUND);
      }
    } catch (DuplicateFormatFlagsException duplicateFormatFlagsException) {
      errorPathSelectedLabel(duplicateFormatFlagsException.getMessage());
    }
  }

  private void clearPathSelectedLabel() {
    pathSelectedJLabel.setText(NO_FILES_SELECTED);
    uploadButton.setEnabled(false);
    showInformationMessage(this, FILE_UPLOAD_SUCCESS);
  }

  private void errorPathSelectedLabel(String error) {
    pathSelectedJLabel.setText(UPLOAD_ANOTHER_FILE);
    uploadButton.setEnabled(false);
    showErrorMessage(this, error);
  }
}
