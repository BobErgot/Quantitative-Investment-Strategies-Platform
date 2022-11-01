package model;

import java.nio.file.Path;
import java.util.List;

public interface FileInterface {
  /**
   * Creates a new file at the specified path passed to it as parameter.
   *
   * @param folderName directory of file structure where the file needs to be stored
   * @param filePrefix   Prefix name of the file to be searched
   * @param extension  extension of the file to be read
   * @return boolean value based on file creation success or failure
   */
  Path createFile(String folderName, String filePrefix, String extension);

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
   * Return the path formed from the given folder name, file name and extension relative to Home
   * path.
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
   * Read data from file in format that is depended on the concrete implementation of this
   * interface.
   *
   * @param folderName directory where the file needs to be read from
   * @param filePrefix the symbol of the stock whose data needs to be retrieved
   * @return individual records that are retrieved from file
   */
  List<String> readFromFile(String folderName, String filePrefix);

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
   * @param object object in string format that needs to be mapped to this file format
   * @param referenceFile if object is dependent on other object type, then it can be stored in
   *                      another file, and a reference of that file is given
   * @return string representation of that object in this file implementation
   */
  String convertObjectIntoString(String object, List<String> referenceFile);

  /**
   * Convert Object List into records representation for this file implementation.
   * @param objectList list of objects that is to be represented in this file implementation
   * @return string representation of that object list in this file implementation
   * @param <T> Custom object whose list is passed as parameter
   */
  <T> String convertObjectListIntoString(List<T> objectList);

  /**
   * Returns the index as positive number if hashKey is found in file data else returns negative
   * number which could be supposed position for this hashKey
   * @param folderName directory where the file needs to be stored
   * @param filePrefix the symbol of the stock whose data needs to be stored
   * @param hashKey the key that needs to be searched in the file
   * @return index position in the file
   */
  long getIndex(String folderName, String filePrefix, String hashKey);
}
