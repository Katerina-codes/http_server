package http_server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class FileHandler {

    public byte[] returnResourceContents(String resource) {
        byte[] contents = null;
        String filePath = String.format("public/%s", resource);
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                contents = Files.readAllBytes(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    public List<String> returnDirectoryContents(String path) throws DirectoryNotFoundException {
        Path directoryPath = Paths.get(path);
        if (Files.exists(directoryPath)) {
            List<File> files = getFiles(directoryPath);
            return extractFileNames(files);
        } else throw new DirectoryNotFoundException();
    }

    private List<File> getFiles(Path directoryPath) {
        File directory = new File(directoryPath.toString());
        File[] listOfFiles = directory.listFiles();
        List<File> files;
        files = directoryContents(listOfFiles);
        return files;
    }

    private List<File> directoryContents(File[] listOfFiles) {
        List<File> files;
        if (listOfFiles != null) {
            files = asList(listOfFiles);
        } else {
            files = emptyList();
        }
        return files;
    }

    private ArrayList<String> extractFileNames(List<File> files) {
        ArrayList<String> extractedFileNames = new ArrayList<>();
        for (File file : files) {
            String fileName = file.toString().substring(7, file.toString().length());
            extractedFileNames.add(fileName);
        }
        return extractedFileNames;
    }

    public void createFile(String path, String file) {
        Path pathToNewFile = Paths.get(path + "/" + file);
        try {
            Files.createFile(pathToNewFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String directoryPath, String fileToDelete) {
        File file = new File(directoryPath + "/" + fileToDelete);
        file.delete();
    }

}