package model;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.WRITE;
import static utility.Constants.FILE_SEPARATOR;
import static utility.Constants.HOME;

abstract class FileAbstract implements FileInterface {
  protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());

  @Override
  public Path createFile(String folderName, String filePrefix, String extension) {
    String directory = HOME + FILE_SEPARATOR + folderName + FILE_SEPARATOR;
    Path directoryPath = Paths.get(directory);
    try {
      Files.createDirectories(directoryPath);
      Optional<Path> file = Files.list(directoryPath).filter(path->path.getFileName().toFile()
                      .getName().startsWith(filePrefix)).findFirst();
      if(file.isPresent()) {
        return file.get();
      }
    } catch (IOException ioException) {
      LOGGER.log(Level.SEVERE, "Error occurred in file lookup: ", ioException);
      return null;
    }
    String fileName = filePrefix + LocalDate.now() + UUID.randomUUID();
    Path filePath = createFilePath(folderName, fileName, extension);
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
    Path filePath = createFile(folderName, filePrefix, getFileExtension());
    if (null != filePath && filePath.toFile().isFile()) {
      try {
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(filePath, WRITE);
        byte [] newLine = getRecordDelimiter().getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(dataBytes.length + newLine.length);
        buffer.put(dataBytes);
        if (dataBytes.length > 0) {
          buffer.put(newLine);
        }
        buffer.flip();
        if (fileChannel.size() > 0) {
          Future<Integer> operation = fileChannel.write(buffer, fileChannel.size());
        } else{
          Future<Integer> operation = fileChannel.write(buffer, 0);
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

  public double getShareValueByGivenDate(String stockSymbol, LocalDate date) {
    return 0;
  }

  public Portfolio getListOfPortfoliosById(String id) {
    return null;
  }
}
