package model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.WRITE;
import static utility.Constants.FILE_SEPARATOR;
import static utility.Constants.HOME;
import static utility.Constants.RELATIVE_PATH;

/**
 * Abstract base class for implementations of {@link FileInterface}. This clas contains all the
 * method definitions that are common to the concrete implementations of the {@link FileInterface}
 * interface. A new implementation of the interface has the option of extending this class, or
 * directly implementing all the methods.
 */
abstract class FileAbstract implements FileInterface {

  protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());

  @Override
  public Path createFile(String path, String folderName, String filePrefix, String extension) {
    String root = HOME;
    String folderPath = "";
    String directory = "";
    if (!path.equals(RELATIVE_PATH)) {
      root = path;
    }
    if (!folderName.isEmpty()) {
      folderPath = folderName + FILE_SEPARATOR;
    }
    directory = root + FILE_SEPARATOR + folderPath;
    Path directoryPath = Paths.get(directory);
    try {
      Files.createDirectories(directoryPath);
      Optional<Path> file = Files.list(directoryPath).filter(filePath ->
              filePath.getFileName().toFile().getName().startsWith(filePrefix)).findFirst();
      if (file.isPresent()) {
        return file.get();
      }
    } catch (IOException ioException) {
      LOGGER.log(Level.SEVERE, "Error occurred in file lookup: ", ioException);
      return null;
    }
    String fileName = filePrefix + LocalDate.now() + UUID.randomUUID();
    Path filePath = createFilePath(path, folderName, fileName, extension);
    try {
      return Files.createFile(filePath);
    } catch (FileAlreadyExistsException fileAlreadyExistsException) {
      LOGGER.log(Level.INFO, "File already exists: ", fileAlreadyExistsException);
      return filePath;
    } catch (IOException ioException) {
      LOGGER.log(Level.SEVERE, "Error occurred in file creation: ", ioException);
      return null;
    }
  }

  @Override
  public boolean exists(String path, String folderName, String fileName, String extension) {
    return Files.exists(createFilePath(path, folderName, fileName, extension));
  }

  @Override
  public Path createFilePath(String path, String folderName, String fileName, String extension) {
    String root = HOME;
    String folderPath = "";
    if (!path.equals(RELATIVE_PATH)) {
      root = path;
    }
    if (!folderName.isEmpty()) {
      folderPath = folderName + FILE_SEPARATOR;
    }
    return Paths.get(root + FILE_SEPARATOR + folderPath + fileName + "." + extension);
  }

  @Override
  public boolean deleteFile(String path, String folderName, String fileName, String extension) {
    boolean operationStatus = false;
    try {
      operationStatus = Files.deleteIfExists(createFilePath(path, folderName, fileName, extension));
    } catch (IOException ioException) {
      LOGGER.log(Level.SEVERE, "Error occurred in file deletion: ", ioException);
      return false;
    }
    return operationStatus;
  }

  @Override
  public boolean writeToFile(String path, String folderName, String filePrefix, byte[] dataBytes) {
    Path filePath = createFile(path, folderName, filePrefix, getFileExtension());
    if (null != filePath && filePath.toFile().isFile()) {
      try {
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(filePath, WRITE);
        byte[] newLine = getRecordDelimiter().getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(dataBytes.length + newLine.length);
        buffer.put(dataBytes);
        if (dataBytes.length > 0) {
          buffer.put(newLine);
        }
        buffer.flip();

        Future<Integer> operation;
        if (fileChannel.size() > 0) {
          operation = fileChannel.write(buffer, fileChannel.size());
        } else {
          operation = fileChannel.write(buffer, 0);
        }
        buffer.clear();
      } catch (IOException ioException) {
        LOGGER.log(Level.SEVERE, "Error occurred in file write.", ioException);
        return false;
      }
      return true;
    }
    return false;
  }

  @Override
  public boolean clearFile(String path, String folderName, String filePrefix, String extension) {
    String directory = "";
    if (path.equals(RELATIVE_PATH)) {
      directory = HOME + FILE_SEPARATOR + folderName + FILE_SEPARATOR;
    } else {
      directory = path + FILE_SEPARATOR + folderName + FILE_SEPARATOR;
    }
    Path directoryPath = Paths.get(directory);
    Path filePath = null;
    try {
      Files.createDirectories(directoryPath);
      Optional<Path> file = Files.list(directoryPath).filter(filepath -> filepath.getFileName()
              .toFile().getName().startsWith(filePrefix)).findFirst();
      if (file.isPresent()) {
        filePath = file.get();
      }
    } catch (IOException ioException) {
      LOGGER.log(Level.SEVERE, "Error occurred in finding file: ", ioException);
      return false;
    }

    if (filePath != null) {
      try (BufferedWriter bufferedWriter =
                   Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING)) {
        return true;
      } catch (IOException exception) {
        LOGGER.log(Level.SEVERE, "Error occurred in reading file: ", exception);
        return false;
      }
    }
    return true;
  }
}
