package controller;

import java.nio.file.Path;
import java.util.List;
import model.FileInterface;

class MockFile implements FileInterface {

  @Override
  public Path createFile(String path, String folderName, String filePrefix, String extension) {
    return null;
  }

  @Override
  public boolean exists(String path, String folderName, String fileName, String extension) {
    return false;
  }

  @Override
  public Path createFilePath(String path, String folderName, String fileName, String extension) {
    return null;
  }

  @Override
  public boolean deleteFile(String path, String folderName, String fileName, String extension) {
    return false;
  }

  @Override
  public boolean writeToFile(String path, String folderName, String filePrefix, byte[] dataBytes) {
    return false;
  }

  @Override
  public List<String> readFromFile(String path, String folderName, String filePrefix) {
    return null;
  }

  @Override
  public String getFileExtension() {
    return null;
  }

  @Override
  public String getRecordDelimiter() {
    return null;
  }

  @Override
  public String convertObjectIntoString(String object, List<String> referenceFile) {
    return null;
  }

  @Override
  public <T> String convertObjectListIntoString(List<T> objectList) {
    return null;
  }

  @Override
  public <T> List<T> validateFormat(String content) {
    return null;
  }
}
