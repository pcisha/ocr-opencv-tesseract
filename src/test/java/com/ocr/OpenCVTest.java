package com.ocr;

import org.opencv.core.Core;
import org.springframework.util.Assert;

public class OpenCVTest {

    public OpenCVTest() {
        System.load("/usr/local/share/java/opencv4/libopencv_java4120.dylib"); // Load OpenCV native library
    }

    public static void main(String[] args) {
        Assert.isTrue(Core.NATIVE_LIBRARY_NAME.equals("opencv_java4120"), "Library not loaded.");
        Assert.isTrue(Core.VERSION.equals("4.12.0-dev"), "OpenCV Version not loaded.");
        System.out.println("OpenCV version: " + Core.VERSION); // OpenCV version: 4.12.0-dev
    }

}