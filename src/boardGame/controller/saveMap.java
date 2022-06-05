package boardGame.controller;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.filechooser.FileFilter;

class FileTypeFilter extends FileFilter {
    private final String extension;
    private final String description;

    public FileTypeFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().endsWith(extension);
    }

    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }
}

public class saveMap {
    public static int load() throws IOException {
        JFileChooser fileDialog = new JFileChooser();
        FileFilter txtFilter = new FileTypeFilter(".txt", "텍스트 파일");

        fileDialog.addChoosableFileFilter(txtFilter);

        int returnVal = fileDialog.showOpenDialog(null);
        if (returnVal == 0) {
            File file = fileDialog.getSelectedFile();

            String filePath = Paths.get("").toAbsolutePath().toString() + "\\resources\\recent.map";
            File save = new File(filePath);

            Files.copy(file.toPath(), save.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return 0;
        } else {
            return -1;
        }
    }
}
