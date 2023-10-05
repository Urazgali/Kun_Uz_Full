package com.example.service;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;


    @Value("${attach.folder.name}")
    private String folderName;

    @Value("${attach.url}")
    private String attachUrl;

//    public String saveToSystem(MultipartFile file) {
//        try {
//            File folder = new File("attaches");
//            if (!folder.exists()) {
//                folder.mkdir();
//            }
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get("attaches/" + file.getOriginalFilename());
//            Files.write(path, bytes);
//            return file.getOriginalFilename();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }


    public AttachDTO save(MultipartFile file) {
        if (file == null || file.isEmpty()) throw new AppBadRequestException("You have uploaded an unknown file");
        String pathFolder = getYmDString();
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            boolean t = folder.mkdirs();
        }
        String key = UUID.randomUUID().toString();
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(getUrl(pathFolder, key, extension));
            Files.write(path, bytes);
            AttachEntity entity = new AttachEntity();
            entity.setId(key);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOriginalName(file.getOriginalFilename());
            entity.setExtension(extension);
            attachRepository.save(entity);

            return getDTO(entity.getId());

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public byte[] loadImage(String fileName) {
//        try {
//            BufferedImage originalImage = ImageIO.read(new File("attaches/" + fileName));
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(originalImage, "png", baos);
//
//            baos.flush();
//            byte[] imageInByte = baos.toByteArray();
//            baos.close();
//            return imageInByte;
//        } catch (Exception e) {
//            return new byte[0];
//        }
//    }

    public byte[] loadImageById(String fileName) {
        AttachEntity entity = get(fileName);
        try {
            BufferedImage originalImage = ImageIO.read(new File(getUrl(entity.getPath(), entity.getId(), entity.getExtension())));
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            ImageIO.write(originalImage, entity.getExtension(), boas);

            byte[] imageInByte = boas.toByteArray();

            boas.flush();
            boas.close();

            return imageInByte;

        } catch (Exception e) {
            return new byte[0];
        }
    }

    public byte[] loadByIdGeneral(String fileName) {
        AttachEntity entity = get(fileName);
        try {
            File file = new File(getUrl(entity.getPath(), entity.getId(), entity.getExtension()));
            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);
            int i = fileInputStream.read(bytes);
            fileInputStream.close();

            return bytes;

        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public ResponseEntity<Resource> download(String fileName) {
        AttachEntity entity = get(fileName);
        try {
            Path file = Paths.get(getUrl(entity.getPath(), entity.getId(), entity.getExtension()));
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
            } else throw new AppBadRequestException("Could not read the file!");

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public PageImpl<AttachDTO> pagination(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttachEntity> pageList = attachRepository.findAll(pageable);
        List<AttachDTO> list = new LinkedList<>();
        pageList.getContent().forEach(entity -> {
            AttachDTO dto = new AttachDTO();
            dto.setId(entity.getId());
            dto.setOriginalName(entity.getOriginalName());
            list.add(dto);
        });
        return new PageImpl<>(list, pageable, pageList.getTotalElements());
    }

    public void delete(String fileName) {
        AttachEntity entity = get(fileName);
        attachRepository.delete(entity);
        try {
            File file = new File(getUrl(entity.getPath(), entity.getId(), entity.getExtension()));
            boolean t = file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public AttachDTO getDTO(String fileName) {
        if (fileName == null) return null;
        return new AttachDTO(fileName, attachUrl + "/" + fileName);
    }

    private String getUrl(String pathFolder, String key, String extension) {
        return folderName + "/" + pathFolder + "/" + key + "." + extension;
    }

    public AttachEntity get(String fileName) {
        if (fileName == null) throw new ItemNotFoundException("Image not found");
        return attachRepository.findById(fileName).orElseThrow(() -> new ItemNotFoundException("Image not found"));
    }

    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
}
