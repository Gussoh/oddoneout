/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;

/**
 *
 * @author Gussoh
 */
public class QuestionImage extends GameImage {

    private URL webPage;
    
    public QuestionImage(URL location, URL webPage) throws IOException {
        super(location);
        this.webPage = webPage;
    }

    public QuestionImage(ByteBuffer imageData, String contentType, URL webPage) {
        super(imageData, contentType);
        this.webPage = webPage;
    }

    public URL getWebPage() {
        return webPage;
    }

    

    
}
