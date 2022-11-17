package model;

import java.nio.file.Path;
import java.util.List;

/**
 * This interface represents all the I/O operations to be supported by a file database.
 */
interface FileInterface {
  /**
   * Creates a new file at the specified path passed to it as parameter.
   *
   * @param path       specify absolute path or relative path by passing ~
   * @param folderName directory of file structure where the file needs to be stored
   * @param filePrefix Prefix name of the file to be searched
   * @param extension  extension of the file to be read
   * @return boolean value based on file creation success or failure
   */
  Path createFile(String path, String folderName, String filePrefix, String extension);

  /**
   * Checks whether the file exists or not.
   *
   * @param path       specify absolute path or relative path by passing ~
   * @param folderName directory of file structure where the file needs to be stored
   * @param fileName   name of the file to be searched
   * @param extension  extension of the file to be read
   * @return true if file is found, false otherwise
   */
  boolean exists(String path, String folderName, String fileName, String extension);

  /**
   * Return the path formed from the given folder name, file name and extension relative to Home
   * path.
   *
   * @param path       specify absolute path or relative path by passing ~
   * @param folderName directory of file structure where the file needs to be stored
   * @param fileName   name of the file to be searched
   * @param extension  extension of the file to be read
   * @return file path object
   */
  Path createFilePath(String path, String folderName, String fileName, String extension);

  /**
   * Delete file at the specified path passed to it as parameter.
   *
   * @param path       specify absolute path or relative path by passing ~
   * @param folderName directory of file structure where the file needs to be deleted from
   * @param fileName   name of the file to be searched
   * @param extension  extension of the file to be read
   * @return boolean value based on file deletion success or failure
   */
  boolean deleteFile(String path, String folderName, String fileName, String extension);

  /**
   * Writes data to file in format that is depended on the concrete implementation of this
   * interface.
   *
   * @param path       specify absolute path or relative path by passing ~
   * @param folderName directory where the file needs to be stored
   * @param filePrefix the symbol of the stock whose data needs to be stored
   * @param dataBytes  data to be written to file in bytes format
   * @return true if write is successful, else returns false
   */
  boolean writeToFile(String path, String folderName, String filePrefix, byte[] dataBytes);

  /**
   * Read data from file in format that is depended on the concrete implementation of this
   * interface.
   *
   * @param path       specify absolute path or relative path by passing ~
   * @param folderName directory where the file needs to be read from
   * @param filePrefix the symbol of the stock whose data needs to be retrieved
   * @return individual records that are retrieved from file
   */
  List<String> readFromFile(String path, String folderName, String filePrefix);

  /**
   * Returns the file extension.
   *
   * @return file extension as string
   */
  String getFileExtension();

  /**
   * Returns the record delimiter.
   *
   * @return record delimiter as string
   */
  String getRecordDelimiter();

  /**
   * Convert Object into record representation for this file implementation.
   *
   * @param object        object in string format that needs to be mapped to this file format
   * @param referenceFile if object is dependent on other object type, then it can be stored in
   *                      another file, and a reference of that file is given
   * @return string representation of that object in this file implementation
   */
  String convertObjectIntoString(String object, List<String> referenceFile);

  /**
   * Convert Object List into records representation for this file implementation.
   *
   * @param objectList list of objects that is to be represented in this file implementation
   * @param <T>        Custom object whose list is passed as parameter
   * @return string representation of that object list in this file implementation
   */
  <T> String convertObjectListIntoString(List<T> objectList);

  /**
   * Check data content that needs to be checked against file interface implementation.
   *
   * @param content data content in string format
   * @param <T>     Custom object whose list is passed as parameter
   * @return list of individual reports
   */
  <T> List<T> validateFormat(String content);

  /**
   * Clear the file content in the specified path.
   *
   * @param path       specify absolute path or relative path by passing ~
   * @param folderName directory of file structure where the file needs to be stored
   * @param filePrefix Prefix name of the file to be searched
   * @param extension  extension of the file to be read
   * @return boolean value if file is cleared successfully
   */
  boolean clearFile(String path, String folderName, String filePrefix, String extension);
}
