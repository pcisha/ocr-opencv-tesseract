package com.ocr.controller;

public class EndpointConstants {

    // OpenCV native library
    public static final String OPENCV_LIBRARY_PATH = "/usr/local/share/java/opencv4/libopencv_java4120.dylib";
    // Tesseract JNA library
    public static final String TESSERACT_LIBRARY_NAME = "jna.library.path";
    public static final String TESSERACT_LIBRARY_PATH = "/opt/homebrew/Cellar/tesseract/5.5.0/lib";

    // Endpoints
    public static final String API_OCR = "/api/ocr";
    public static final String UPLOAD_FILE = "/upload";

    // Image handling
    public static final String IMAGE_FILE = "imageFile";
    public static final String UPLOAD = "upload";
    public static final String PROCESSED = "_processed.";

    // Image Preprocessing with Tesseract
    public static final String DATA_PATH = "/opt/homebrew/share/tessdata";
    public static final String LANGUAGE_SUPPORT = "eng";
    public static final int PAGE_SEGMENTATION = 6;
    public static final String USER_DEFINED_DPI_VARIABLE = "user_defined_dpi";
    public static final String USER_DEFINED_DPI_VARIABLE_VALUE = "300";
    public static final String PRESERVE_INTERWORD_SPACES_VARIABLE = "preserve_interword_spaces";
    public static final String PRESERVE_INTERWORD_SPACES_VARIABLE_VALUE = "1";
    public static final String WHITELISTING_VARIABLE = "tessedit_char_whitelist";
    public static final String WHITELISTING_VARIABLE_VALUE = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789(){}[].,:;+-*/=_<>\"'@#$%^&|~`?";

    // Error Handling
    public static final String EMPTY = "Empty";
    public static final String ERROR = "Error";
    public static final String INVALID_IMAGE_FILE_FORMAT = "Invalid image file.";
    public static final String ERROR_PROCESSING_IMAGE = "Error processing the image: ";

}