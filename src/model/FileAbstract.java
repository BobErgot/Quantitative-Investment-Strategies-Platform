package model;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.WRITE;
import static utility.Constants.FILE_SEPARATOR;
import static utility.Constants.HOME;

abstract class FileAbstract implements FileInterface {
  private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

  @Override
  public boolean createFile(String folderName, String fileName, String extension) {
    String directory = HOME + FILE_SEPARATOR + folderName + FILE_SEPARATOR;
    Path directoryPath = Paths.get(directory);
    Path filePath = createFilePath(folderName, fileName, extension);
    try {
      Files.createDirectories(directoryPath);
      Files.createFile(filePath);
      return true;
    } catch (FileAlreadyExistsException fileAlreadyExistsException) {
      LOGGER.log(Level.INFO, "File already exists: ", fileAlreadyExistsException);
      return true;
    } catch (IOException ioException) {
      LOGGER.log(Level.SEVERE, "Error occurred in file creation: ", ioException);
      return false;
    }
  }

  @Override
  public boolean exists(String folderName, String fileName, String extension) {
    return Files.exists(createFilePath(folderName, fileName, extension));
  }

  @Override
  public Path createFilePath(String folderName, String fileName, String extension) {
    return Paths.get(HOME + FILE_SEPARATOR + folderName + FILE_SEPARATOR
            + fileName + "." + extension);
  }

  @Override
  public boolean deleteFile(String folderName, String fileName, String extension) {
    boolean operationStatus = false;
    try {
      operationStatus = Files.deleteIfExists(createFilePath(folderName, fileName, extension));
    } catch (IOException ioException) {
      LOGGER.log(Level.SEVERE, "Error occurred in file deletion: ", ioException);
      return false;
    }
    return operationStatus;
  }

  @Override
  public boolean writeToFile(String folderName, String filePrefix, byte[] dataBytes) {
    String fileName = filePrefix + new Date().getTime() + UUID.randomUUID();
    if (createFile(folderName, fileName, getFileExtension())) {
      Path filePath = createFilePath(folderName, fileName, getFileExtension());
      try {
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(filePath, WRITE);

        ByteBuffer buffer = ByteBuffer.allocate(dataBytes.length);
        buffer.put(dataBytes);
        buffer.flip();
        Future<Integer> operation = fileChannel.write(buffer, 0);
        buffer.clear();
      } catch (IOException ioException) {
        LOGGER.log(Level.SEVERE, "Error occurred in file write.", ioException);
        return false;
      }
      return true;
    }
    return false;
  }

  public double getShareValueByGivenDate(String stockSymbol, Date date) {
    return 0;
  }

  public List<String> getListOfPortfolios() {
    return null;
  }

  public Portfolio getListOfPortfoliosById(String id) {
    return null;
  }
}
