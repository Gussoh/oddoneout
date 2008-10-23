/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.SearchParameters;
import com.aetrion.flickr.tags.Tag;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author TAND0015
 */
public class FlickrQuestionProvider extends QuestionProvider {

    private int numberOfImages = 4;

    @Override
    protected Question createQuestion() throws QuestionProviderException {

        try {
            String apiKey = "9d12e1ff7fc3d48b446217fec21e5fa2";
            Flickr f = new Flickr(apiKey);
            f.setSharedSecret("8fe71e0a81c3874e");

            PhotosInterface photosInterface = f.getPhotosInterface();

            Photo firstPhoto = null;

            while (true) {
                PhotoList photoList = photosInterface.getRecent(10, 1);

                for (int i = 0; i < photoList.size(); i++) {

                    Photo photo = (Photo) photoList.get(i);
                    if (firstPhoto == null) {
                        firstPhoto = photo;
                        continue;
                    }

                    Photo photoInfo = photosInterface.getInfo(photo.getId(), null);

                    System.out.println("PHOTO: " + photo.getTitle() + " tags: " + photoInfo.getTags());

                    for (Iterator it = photoInfo.getTags().iterator(); it.hasNext();) {
                        Tag t = (Tag) it.next();
                        System.out.println("tag: " + t.getValue() + " raw: " + t.getRaw());
                        SearchParameters sp = new SearchParameters();
                        sp.setTags(new String[]{t.getValue()});
                        sp.setSort(SearchParameters.INTERESTINGNESS_DESC);
                        PhotoList pl = photosInterface.search(sp, Math.max(numberOfImages, 10), 1);
                        if (pl.size() >= Math.max(numberOfImages, 10)) {
                            System.out.println("Found a tag with >= " + Math.max(numberOfImages, 10) + " photos: " + t.getValue());
                            List<QuestionImage> images = new ArrayList<QuestionImage>(4);

                            int correct = (int) (Math.random() * numberOfImages);

                            for (int j = 0; images.size() < numberOfImages; j++) {
                                if (correct == j) {
                                    images.add(new QuestionImage(new URL(firstPhoto.getSmallUrl()), new URL(firstPhoto.getUrl())));
                                }

                                if (images.size() < numberOfImages) {
                                    Photo notOdd = (Photo) pl.get(j);
                                    images.add(new QuestionImage(new URL(notOdd.getSmallUrl()), new URL(notOdd.getUrl())));
                                }
                            }

                            return new Question(images, correct, t.getRaw());
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new QuestionProviderException(e.getMessage());
        }
    }
}
