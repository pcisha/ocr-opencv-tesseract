package com.ocr.controller;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ocr.service.OCRService;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.logging.Logger;

/*
 * @author: prachi.shah
 */
@RestController
@RequestMapping(EndpointConstants.API_OCR)
public class OCRController {

    private static final Logger logger = Logger.getLogger(OCRController.class.getName());

    private final OCRService ocrService;

    @Value("${max.file.size.mb:5}")
    private int maxFileSizeMB; // Default to 5MB if not set

    public OCRController(final OCRService ocrService) {
        System.load(EndpointConstants.OPENCV_LIBRARY_PATH);
        System.setProperty(EndpointConstants.TESSERACT_LIBRARY_NAME, EndpointConstants.TESSERACT_LIBRARY_PATH);
        this.ocrService = ocrService;
    }

    @PostMapping(value = EndpointConstants.UPLOAD_FILE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> fileUpload(@RequestParam(EndpointConstants.IMAGE_FILE) MultipartFile imageFile) {

        logger.info("Starting Image to Text conversion...");

        String convertedText = ocrService.convertImageToText(imageFile);

        if (convertedText == null || convertedText == EndpointConstants.EMPTY) {
            logger.warning(EndpointConstants.INVALID_IMAGE_FILE_FORMAT);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(EndpointConstants.INVALID_IMAGE_FILE_FORMAT);
        } else if (convertedText == EndpointConstants.ERROR) {
            logger.warning(EndpointConstants.ERROR_PROCESSING_IMAGE + EndpointConstants.ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(EndpointConstants.ERROR_PROCESSING_IMAGE + EndpointConstants.ERROR);
        }

        logger.info("Image to Text converted successfully.");
        return ResponseEntity.ok(convertedText);
    }

}