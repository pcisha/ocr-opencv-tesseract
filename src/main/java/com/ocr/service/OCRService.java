package com.ocr.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ocr.controller.EndpointConstants;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class OCRService {

    private static final Logger logger = Logger.getLogger(OCRService.class.getName());

    public String convertImageToText(MultipartFile imageFile) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile(EndpointConstants.UPLOAD, imageFile.getOriginalFilename());
            imageFile.transferTo(tempFile);

            Mat image = Imgcodecs.imread(tempFile.getAbsolutePath());
            if (image.empty()) {
                logger.warning(EndpointConstants.INVALID_IMAGE_FILE_FORMAT);
                return EndpointConstants.EMPTY;
            }

            // Preprocess image with OpenCV
            // Mat processedImage = preprocessImage(image);
            // Save preprocessed image for OCR (optional for debugging)
            // String processedImagePath = tempFile.getAbsolutePath().replace(".",
            // "_processed.");
            // Imgcodecs.imwrite(processedImagePath, processedImage);

            Mat gray = new Mat();
            Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY); // Convert to Grayscale
            String processedImagePath = tempFile.getAbsolutePath().replace(".", EndpointConstants.PROCESSED);
            Imgcodecs.imwrite(processedImagePath, image);

            // OCR Processing
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath(EndpointConstants.DATA_PATH); // Load Tesseract data files
            tesseract.setLanguage(EndpointConstants.LANGUAGE_SUPPORT); // English
            tesseract.setPageSegMode(EndpointConstants.PAGE_SEGMENTATION); // Assume a uniform block of text
            // Increase DPI. Improve recognition for scanned documents
            tesseract.setVariable(EndpointConstants.USER_DEFINED_DPI_VARIABLE,
                    EndpointConstants.USER_DEFINED_DPI_VARIABLE_VALUE);
            // Preserve spaces between words
            tesseract.setVariable(EndpointConstants.PRESERVE_INTERWORD_SPACES_VARIABLE,
                    EndpointConstants.PRESERVE_INTERWORD_SPACES_VARIABLE_VALUE);
            // Whitelist characters for better recognition
            tesseract.setVariable(EndpointConstants.WHITELISTING_VARIABLE,
                    EndpointConstants.WHITELISTING_VARIABLE_VALUE);

            String convertedText = tesseract.doOCR(new File(processedImagePath));

            // Clean-up temporary files
            Files.deleteIfExists(tempFile.toPath());
            Files.deleteIfExists(new File(processedImagePath).toPath());
            return convertedText;
        } catch (IOException | TesseractException e) {
            logger.warning(EndpointConstants.ERROR_PROCESSING_IMAGE + e.getMessage());
            return EndpointConstants.ERROR;
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
                logger.info("Deleted temporary file: " + tempFile.getName());
            }
        }
    }

    // Preprocess the image using OpenCV
    private Mat preprocessImage(Mat image) {
        Mat gray = new Mat();
        Mat filtered = new Mat();
        Mat thresholded = new Mat();

        // Convert to Grayscale
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        // Apply Bilateral Filter to preserve edges while reducing noise
        Imgproc.bilateralFilter(gray, filtered, 9, 75, 75);

        // Apply Otsu's Thresholding for optimal binarization
        Imgproc.threshold(filtered, thresholded, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);

        // Morphological Transformations (Dilation followed by Erosion)
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
        Imgproc.morphologyEx(thresholded, thresholded, Imgproc.MORPH_CLOSE, kernel);

        // Resize the image to improve OCR accuracy
        Mat resizedImage = new Mat();
        Imgproc.resize(thresholded, resizedImage, new Size(image.width() * 1.5, image.height() * 1.5),
                0, 0, Imgproc.INTER_CUBIC);

        logger.info("Image Preprocessing using OpenCV completed.");
        return resizedImage;
    }

}
