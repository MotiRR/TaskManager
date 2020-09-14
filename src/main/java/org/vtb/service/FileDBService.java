package org.vtb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.vtb.entity.FileDB;
import org.vtb.repository.FileDBRepository;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileDBService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file, Long taskId) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB FileDB = new FileDB(taskId, fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(FileDB);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
