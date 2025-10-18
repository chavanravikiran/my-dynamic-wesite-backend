package com.webapp.websiteportal.service.impl;

import org.springframework.stereotype.Service;

import com.webapp.websiteportal.service.ICctvStreamService;

@Service
public class CctvStreamServiceImpl implements ICctvStreamService{

//	 private static final String RTSP_URL = "rtsp://username:password@192.168.1.100:554/stream1";
//
//	    private volatile byte[] latestFrame;
//
//	    @PostConstruct
//	    public void startStream() {
//	        Executors.newSingleThreadExecutor().execute(() -> {
//	            try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(RTSP_URL)) {
//	                grabber.setOption("rtsp_transport", "tcp"); // RTSP over TCP
//	                grabber.start();
//
//	                OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
//	                Java2DFrameConverter java2D = new Java2DFrameConverter();
//
//	                while (true) {
//	                    Frame frame = grabber.grabImage();
//	                    if (frame != null) {
//	                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	                        javax.imageio.ImageIO.write(java2D.convert(frame), "jpg", baos);
//	                        latestFrame = baos.toByteArray();
//	                    }
//	                }
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	            }
//	        });
//	    }
//
//	    public byte[] getLatestFrame() {
//	        return latestFrame;
//	    }
}
