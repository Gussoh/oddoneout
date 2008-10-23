/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.SearchParameters;
import com.aetrion.flickr.tags.Tag;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.xml.sax.SAXException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author TAND0015
 */
public class test {


    // testing 
    public static void main(String args[]) throws IOException, SAXException, FlickrException {

        String apiKey = "9d12e1ff7fc3d48b446217fec21e5fa2";
        Flickr f = new Flickr(apiKey);

        // Set the shared secret which is used for any calls which require signing.
        f.setSharedSecret("8fe71e0a81c3874e");

        PhotosInterface photosInterface = f.getPhotosInterface();

        Photo firstPhoto = null;
        
        while (true) {
            PhotoList photoList = photosInterface.getRecent(10, 1);
            
            for (int i = 0; i < photoList.size(); i++) {
                
                Photo photo = (Photo) photoList.get(i);
                if(firstPhoto == null) {
                    firstPhoto = photo;
                    continue;
                }
                
                Photo photoInfo = photosInterface.getInfo(photo.getId(), null);

                System.out.println("PHOTO: " + photo.getTitle() + " tags: " + photoInfo.getTags());
                
                for (Iterator it = photoInfo.getTags().iterator(); it.hasNext();) {
                    Tag t = (Tag) it.next();
                    System.out.println("tag: " + t.getValue() + " raw: " + t.getRaw() + " count: " + t.getCount());
                    SearchParameters sp = new SearchParameters();
                    sp.setTags(new String[]{t.getValue()});
                    PhotoList pl = photosInterface.search(sp, 10, 1);
                    if(pl.size() > 8) {
                        System.out.println("Found a tag with >= 8 photos: " + t.getValue());
                        String string = t.getValue();
                        List<QuestionImage> images = new ArrayList<QuestionImage>(4);
                        
                        int correct = (int) (Math.random() * 4);
                        
                        for (int j = 0; j <= 4; j++) {
                            if(correct == j) {
                                images.add(new QuestionImage(new URL(firstPhoto.getMediumUrl()), new URL(firstPhoto.getMediumUrl())));
                            }
                            Photo notOdd = (Photo)pl.get(j);
                            images.add(new QuestionImage(new URL(notOdd.getMediumUrl()), new URL(photo.getMediumUrl())));
                        }
                        
                        System.out.println("New question: " + new Question(images, correct, string));
                        
                        break;
                    }
                }
            }
        }
    }
}
