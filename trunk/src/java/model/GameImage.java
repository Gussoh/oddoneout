/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;

/**
 *
 * @author Gussoh
 */
public class GameImage {

    private ByteBuffer imageData;
    private String contentType;
    private int size;

    /**
     * 
     * @param imageData raw image data encoded according to the contentType
     * @param contentType the mime type of the image. image/jpeg or image/gif for example.
     */
    protected GameImage(ByteBuffer imageData, String contentType) {
        this.imageData = imageData;
        this.contentType = contentType;
        size = imageData.capacity();
    }

    /**
     * Files can have a maximum size of Integer.MAX_VALUE bytes
     * @param location location of the file.
     * @throws IOException when error fetching the image
     */
    protected GameImage(URL location) throws IOException {

        URLConnection connection = location.openConnection();
        InputStream in = new BufferedInputStream(connection.getInputStream());
        
        int data;
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        while ((data = in.read()) != -1) {
            arrayOutputStream.write(data);
        }

        imageData = ByteBuffer.wrap(arrayOutputStream.toByteArray());

        contentType = connection.getContentType();
        in.close();
        size = imageData.capacity();
    }

    public ByteBuffer getImageData() {
        return imageData;
    }

    public String getContentType() {
        return contentType;
    }

    public int getSize() {
        return size;
    }
    
    
}
