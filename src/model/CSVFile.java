package model;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

import static utility.Constants.FILE_SEPARATOR;
import static utility.Constants.HOME;

public class CSVFile extends FileAbstract {
  private final static String EXTENSION = "csv";

  public String getFileExtension() {
    return EXTENSION;
  }

  @Override
  public List<String> readFromFile(String folderName, String filePrefix) {
    List<String> fileData = new ArrayList<>();
    String directory = HOME + FILE_SEPARATOR + folderName + FILE_SEPARATOR;
    Path directoryPath = Paths.get(directory);
    Path filePath = null;
    try {
      Files.createDirectories(directoryPath);
      Optional<Path> file = Files.list(directoryPath).filter(path->path.getFileName().toFile()
              .getName().startsWith(filePrefix)).findFirst();
      if(file.isPresent()) {
        filePath = file.get();
      }
    } catch (IOException ioException) {
      LOGGER.log(Level.SEVERE, "Error occurred in finding file: ", ioException);
      return fileData;
    }

    try {
      AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(filePath,
              StandardOpenOption.READ);
      ByteBuffer buffer = ByteBuffer.allocate(1024);
      Future<Integer> operation = fileChannel.read(buffer, 0);
      operation.get();
      String fileContent = new String(buffer.array()).trim();
      fileData.add(fileContent);
      buffer.clear();
    } catch (IOException | ExecutionException | InterruptedException exception) {
      LOGGER.log(Level.SEVERE, "Error occurred in reading file: ", exception);
    }
    return fileData;
  }

  @Override
  public String convertObjectIntoString(String object, List<String> referenceFile){
    StringBuilder stringFormat = new StringBuilder();
    String [] objectFields = object.split("\n");
    int index = 0;
    for(int i = 0; i<objectFields.length - 1; i++){
      if (objectFields[i].charAt(0) == '*') {
        stringFormat.append("-F:");
        stringFormat.append(referenceFile.get(index));
        index++;
      }
      stringFormat.append(objectFields[i].split(":", 2)[1]);
      stringFormat.append(",");
    }
    stringFormat.append(objectFields[objectFields.length-1].split(":", 2)[1]);
    return stringFormat.toString();
  }

  @Override
  public <T> String convertObjectListIntoString(List<T> objectList){
    StringBuilder stringFormat = new StringBuilder();
    for(int i = 0; i<objectList.size(); i++){
      stringFormat.append(convertObjectIntoString(objectList.get(i).toString().trim(), null));
      stringFormat.append("\n");
    }
    return stringFormat.toString();
  }
}
