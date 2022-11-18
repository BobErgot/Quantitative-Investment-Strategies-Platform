package model;

import java.nio.file.Path;

/**
 * Mock class to mimic the behaviour of CSVFile.
 */
class MockFile extends CSVFile {

  @Override
  public Path createFile(String path, String folderName, String filePrefix, String extension) {
    return null;
  }

  @Override
  public boolean exists(String path, String folderName, String fileName, String extension) {
    return true;
  }

  @Override
  public Path createFilePath(String path, String folderName, String fileName, String extension) {
    return null;
  }

  @Override
  public boolean deleteFile(String path, String folderName, String fileName, String extension) {
    return true;
  }

  @Override
  public boolean writeToFile(String path, String folderName, String filePrefix, byte[] dataBytes) {
    return true;
  }
}
