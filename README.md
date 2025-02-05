# 🖼️ OCR Image Processing Web Application

A Java Spring Boot web application that allows users to upload images (screenshots, scanned documents, etc.) and extract text using Tesseract OCR with advanced image preprocessing powered by OpenCV.

### 🚀 Features

- 📤 Upload Images: Supports multiple image formats (JPG, PNG, BMP).

- 🔍 Accurate OCR: Preprocesses images for high-accuracy text extraction.

- 📏 File Size Validation: Supports file uploads up to 5MB.

- 🧪 Error Handling: Graceful error handling for large files, invalid images, and processing failures.

- ⚙️ Dynamic Configurations: Easily configure library paths, file size limits, and other properties.

### ⚙️ Technologies Used

- Java 17 (or higher)

- Spring Boot (REST API)
- OpenCV (Image Preprocessing)
- Tesseract OCR (Text Extraction)
- Maven (Dependency Management)

### 🚀 Getting Started

##### 1️⃣ Prerequisites

- Java 17+

- Maven

- OpenCV with Java Bindings

- Tesseract OCR

##### 2️⃣ Clone the Repository

`git clone https://github.com/your-username/ocr-image-processor.git
cd ocr-image-processor`

##### 3️⃣ Install Dependencies

`mvn clean install`

##### 4️⃣ Run the Application

`mvn spring-boot:run`

##### 5️⃣ API Endpoint

`POST api/ocr/upload
Content-Type: multipart/form-data`


### 📄 Configuration

Edit `src/main/resources/application.properties`:

#### File Upload Settings
`max.file.size.mb=5`

#### OpenCV and Tesseract Paths
OpenCV: `opencv.library.path=/usr/local/share/java/opencv4/libopencv_java4120.dylib`

Tesseract: `jna.library.path=/opt/homebrew/Cellar/tesseract/5.5.0/lib`

### 🚩 Error Handling

- 413 Payload Too Large: Triggered when a file exceeds 5MB.

- 400 Invalid Image: If the uploaded file is not a valid image.

- 500 Server Error: For unexpected issues during processing.

#
Date: February 5, 2025

Author: Prachi Shah @ https://pcisha.my.canva.site/

P.S. The default copyright laws apply.