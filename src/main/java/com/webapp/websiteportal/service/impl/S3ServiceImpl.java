package com.webapp.websiteportal.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.websiteportal.dto.FileModelResponse;
import com.webapp.websiteportal.service.IS3Service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import org.apache.tomcat.util.codec.binary.Base64;

@Service
public class S3ServiceImpl implements IS3Service{

	@Autowired
    private S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        String fileName = "images/GalleryImages/" + file.getOriginalFilename();

        try {
            s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build(),
                software.amazon.awssdk.core.sync.RequestBody.fromByteBuffer(
                        ByteBuffer.wrap(file.getBytes()))
            );

            return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }
    
    @Override
    public FileModelResponse downloadFile(String fileName) {
        FileModelResponse response = new FileModelResponse();
        String folderPath= "images/GalleryImages";
        
        String fileKey = folderPath + "/" + fileName; // e.g., images/GalleryImages/sample.jpg

        try (InputStream inputStream = s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build())) {
            byte[] fileContent = inputStream.readAllBytes();

            // Encode byte array into Base64
            String base64Encoded = Base64.encodeBase64String(fileContent);

         // Extract file extension from the file name
            String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

            // Get the correct MIME type based on the file extension
            String mimeType = getMimeType(fileExtension);

            // Construct the base64 string with the MIME type prefix
            String base64WithMimeType = "data:" + mimeType + ";base64," + base64Encoded;

            // Set the response
            response.setBase64(base64WithMimeType);
            response.setStatus("Success");
            response.setErrorMessage("");
           
        } catch (IOException e) {
        	response.setBase64("");
            response.setStatus("Failure");
            response.setErrorMessage("Failed to download file: " + e.getMessage());
        }

        return response;
    }
    
    private String getMimeType(String extension) {
        switch (extension.toLowerCase()) {
            // Image files
            case "jpg":
            	return "image/jpg";
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            case "tif":
                return "image/tiff";

            // Document files
            case "pdf":
                return "application/pdf";
            case "txt":
                return "text/plain";
            case "html":
                return "text/html";
            case "xml":
                return "application/xml";
            case "csv":
                return "text/csv";
            case "md":
                return "text/markdown";

            // Office file formats
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";

            // Audio files
            case "mp3":
                return "audio/mpeg";
            case "wav":
                return "audio/wav";
            case "ogg":
                return "audio/ogg";
            case "flac":
                return "audio/flac";
            case "aac":
                return "audio/aac";

            // Video files
            case "mp4":
                return "video/mp4";
            case "avi":
                return "video/x-msvideo";
            case "mov":
                return "video/quicktime";
            case "mkv":
                return "video/x-matroska";
            case "webm":
                return "video/webm";
            case "flv":
                return "video/x-flv";

            // Archive files
            case "zip":
                return "application/zip";
            case "tar":
                return "application/x-tar";
            case "gz":
                return "application/gzip";
            case "rar":
                return "application/x-rar-compressed";
            case "7z":
                return "application/x-7z-compressed";

            // Font files
            case "woff":
                return "font/woff";
            case "woff2":
                return "font/woff2";
            case "otf":
                return "font/otf";
            case "ttf":
                return "font/ttf";

            // JSON and other data formats
            case "json":
                return "application/json";
            case "yaml":
            case "yml":
                return "application/x-yaml";

            // Other file types
            case "js":
                return "application/javascript";
            case "css":
                return "text/css";
            case "svg":
                return "image/svg+xml";
            case "ico":
                return "image/x-icon";

            default:
                return "application/octet-stream"; // Fallback to a generic MIME type
        }
    }

	@Override
	public FileModelResponse downloadFileWithPath(String fileName, String folderPath) throws IOException {
		FileModelResponse response = new FileModelResponse();
//        String folderPath= "images/GalleryImages";
        
		String fileKey = folderPath.replaceAll("/+$", "") + "/" + fileName; 
		fileKey = fileKey.replaceAll("\"", "");
		
		try {
	        response = fetchFileFromS3(fileKey);
	    } catch (NoSuchKeyException e) {
	        // If file not found, attempt to fetch the static file
	        response = fetchFileFromS3("images/logoImage/logo.png");
	        if (response.getStatus().equals("Failure")) {
	            response.setErrorMessage("Requested file and fallback file not found.");
	        }
	    } catch (IOException e) {
	        response.setBase64("");
	        response.setStatus("Failure");
	        response.setErrorMessage("Failed to download file: " + e.getMessage());
	    }
	return response;
	
//        try (InputStream inputStream = s3Client.getObject(GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(fileKey)
//                .build())) {
//            byte[] fileContent = inputStream.readAllBytes();
//
//            // Encode byte array into Base64
//            String base64Encoded = Base64.encodeBase64String(fileContent);
//
//         // Extract file extension from the file name
//            String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
//
//            // Get the correct MIME type based on the file extension
//            String mimeType = getMimeType(fileExtension);
//
//            // Construct the base64 string with the MIME type prefix
//            String base64WithMimeType = "data:" + mimeType + ";base64," + base64Encoded;
//
//            // Set the response
//            response.setBase64(base64WithMimeType);
//            response.setStatus("Success");
//            response.setErrorMessage("");
//           
//        } catch (IOException e) {
//        	response.setBase64("");
//            response.setStatus("Failure");
//            response.setErrorMessage("Failed to download file: " + e.getMessage());
//        }
//
//        return response;
	}
	
	private FileModelResponse fetchFileFromS3(String fileKey) throws IOException {
	    FileModelResponse response = new FileModelResponse();
	    try (InputStream inputStream = s3Client.getObject(GetObjectRequest.builder()
	            .bucket(bucketName)
	            .key(fileKey)
	            .build())) {

	        byte[] fileContent = inputStream.readAllBytes();
	        String base64Encoded = Base64.encodeBase64String(fileContent);

	        // Extract file extension and determine MIME type
	        String fileExtension = fileKey.substring(fileKey.lastIndexOf('.') + 1).toLowerCase();
	        String mimeType = getMimeType(fileExtension);

	        // Construct base64 response
	        String base64WithMimeType = "data:" + mimeType + ";base64," + base64Encoded;
	        response.setBase64(base64WithMimeType);
	        response.setStatus("Success");
	        response.setErrorMessage("");
	    }
	    return response;
	}

}
