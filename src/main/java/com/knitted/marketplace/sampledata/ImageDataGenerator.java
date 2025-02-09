package com.knitted.marketplace.sampledata;

import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.repositories.ImageRepository;
import com.knitted.marketplace.utils.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Service
public class ImageDataGenerator {

    @Autowired
    private ImageRepository imageRepository;

    public void saveImages() throws IOException {
        File folder = new File("src/main/resources/static/images");
        File[] imageFiles = folder.listFiles();

        if (imageFiles != null) {
            for (File file : imageFiles) {
                if (file.isFile()) {
                    byte[] imageData = Files.readAllBytes(file.toPath());

                    ImageFile imageFile = new ImageFile();
                    imageFile.setFilename(file.getName());
                    imageFile.setExtension(FileUtils.getFileExtension(file.getName()));
                    imageFile.setImageData(imageData);

                    imageRepository.save(imageFile);
                }
            }
        }
    }
}
