package com.example.hrms.business.concretes;

import com.example.hrms.core.services.CloudinaryService;
import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.core.utilities.results.SuccessDataResult;
import com.example.hrms.dataAccess.abstracts.ImageDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageManagerTest {

    @Mock
    private ImageDao imageDao;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private ImageManager imageManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        String imageUrl = "http://cloudinary.com/image.jpg";
        DataResult<Object> uploadResult = new SuccessDataResult<Object>((Object) imageUrl);

        when(cloudinaryService.uploadImage(any())).thenReturn(uploadResult);

        Result result = imageManager.add(file);

        assertTrue(result.isSuccess());
        assertEquals(imageUrl, result.getMessage());
        verify(cloudinaryService, times(1)).uploadImage(file);
    }
}
