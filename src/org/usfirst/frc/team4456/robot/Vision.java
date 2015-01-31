package org.usfirst.frc.team4456.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.ColorMode;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.RGBValue;
import com.ni.vision.NIVision.Range;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.vision.AxisCamera;

public class Vision
{
	/*
	 * NOTE: if you get an Invalid Image error, check to make sure that
	 * the image type for extracting planes is IMAGE_U8
	 */
	
	int session;
    Image frame;
    Image frameDest, frameDest8;
    AxisCamera camera;
    
    Image tmpImgA, tmpImgB;
    
    NIVision.RGBValue grayVal;
    NIVision.RGBValue rgbVal;
    
    public Vision()
    {
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	frameDest = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	
    	tmpImgA = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
    	tmpImgB = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
    	frameDest8 = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
    	
        camera = new AxisCamera("10.50.0.30");
        
        rgbVal = new RGBValue(1, 1, 1, 1);
        grayVal = new RGBValue(0, 1, 0, 1);
    }
    
    public void cycle()
    {
    	NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
    	
    	camera.getImage(frame);
        NIVision.imaqDrawShapeOnImage(frameDest, frame, rect,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.5f);
        
        
        CameraServer.getInstance().setImage(frameDest);

        Timer.delay(0.005);
    }
    
    public void writeThresholdImg()
    {
    	camera.getImage(frame);
    	frameDest = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
    	
    	NIVision.imaqExtractColorPlanes(frame, ColorMode.RGB, tmpImgA, frameDest8, tmpImgB);
    	
    	NIVision.imaqWriteBMPFile(frameDest8, Constants.filePathRoborio + "beforeThresImg.bmp", 0, rgbVal);
    	
    	NIVision.imaqThreshold(frameDest8, frameDest8, 0.5f, 1.0f, 1, 0.5f);
    	
    	NIVision.imaqWriteBMPFile(frameDest8, Constants.filePathRoborio + "thresImg.bmp", 10, rgbVal);
    	System.out.println("img written to: " + Constants.filePathRoborio + "thresImg.bmp");
    	
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	
    	Timer.delay(0.005);
    }
}
