package model;

import java.nio.file.Path;
import java.util.Date;

public interface FileInterface {
  /**
   * Creates a new file at the specified path passed to it as parameter.
   *
   * @param folderName directory of file structure where the file needs to be stored
   * @param fileName   name of the file to be searched
   * @param extension  extension of the file to be read
   * @return boolean value based on file creation success or failure
   */
  boolean createFile(String folderName, String fileName, String extension);

  /**
   * Checks whether the file exists or not.
   *
   * @param folderName directory of file structure where the file needs to be stored
   * @param fileName   name of the file to be searched
   * @param extension  extension of the file to be read
   * @return true if file is found, false otherwise
   */
  boolean exists(String folderName, String fileName, String extension);

  /**
   * @param folderName directory of file structure where the file needs to be stored
   * @param fileName   name of the file to be searched
   * @param extension  extension of the file to be read
   * @return file path object
   */
  Path createFilePath(String folderName, String fileName, String extension);

  /**
   * Delete file at the specified path passed to it as parameter.
   *
   * @param folderName directory of file structure where the file needs to be deleted from
   * @param fileName   name of the file to be searched
   * @param extension  extension of the file to be read
   * @return boolean value based on file deletion success or failure
   */
  boolean deleteFile(String folderName, String fileName, String extension);

  /**
   * Writes data to file in format that is depended on the concrete implementation of this
   * interface.
   *
   * @param folderName directory where the file needs to be stored
   * @param filePrefix the symbol of the stock whose data needs to be stored
   * @param dataBytes  data to be written to file in bytes format
   * @return true if write is successful, else returns false
   */
  boolean writeToFile(String folderName, String filePrefix, byte[] dataBytes);

  /**
   * Returns the file extension
   * @return file extension as string
   */
  String getFileExtension();
}
