package com.example.hrms.core.services;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    DataResult<Object> uploadImage(MultipartFile multipartFile) throws IOException;

    Result delete(String id) throws IOException;
}
