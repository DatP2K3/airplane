package com.airplane.schedule.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    Map upLoadFile(MultipartFile file);
}
