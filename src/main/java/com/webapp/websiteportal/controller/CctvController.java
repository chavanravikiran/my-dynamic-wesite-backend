package com.webapp.websiteportal.controller;

import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webapp.websiteportal.service.ICctvStreamService;
import jakarta.servlet.http.HttpServletResponse;


@RestController
public class CctvController {

//    @Autowired
//    private ICctvStreamService cctvStreamService;
//
//    @GetMapping(value = "/cctv/stream", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void streamVideo(HttpServletResponse response) {
//        response.setContentType("multipart/x-mixed-replace; boundary=frame");
//        try (OutputStream out = response.getOutputStream()) {
//            while (true) {
//                byte[] frame = cctvStreamService.getLatestFrame();
//                if (frame != null) {
//                    out.write(("--frame\r\nContent-Type: image/jpeg\r\n\r\n").getBytes());
//                    out.write(frame);
//                    out.write("\r\n".getBytes());
//                    out.flush();
//                }
//                Thread.sleep(100); // 10 fps
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}