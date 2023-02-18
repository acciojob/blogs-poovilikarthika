package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Image image = new Image();
        image.setDimensions(dimensions);
        image.setDescription(description);

        Blog blog = blogRepository2.findById(blogId).get();
        image.setBlog(blog);
        blog.getImageList().add(image);

        blogRepository2.save(blog);

        return image;
    }

    public void deleteImage(Integer id){
        Image image = imageRepository2.findById(id).get();
        Blog blog = image.getBlog();
        blog.getImageList().remove(image);
        blogRepository2.save(blog);
        imageRepository2.delete(image);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        Image image = imageRepository2.findById(id).get();
        String imageDimensions = image.getDimensions();

        String imageDimension[] = imageDimensions.split("X");
        int imageLength = Integer.parseInt(imageDimension[0]);
        int imageBredth = Integer.parseInt(imageDimension[1]);

        String screenDimension[] = screenDimensions.split("X");
        int screenLength = Integer.parseInt(screenDimension[0]);
        int screenBredth = Integer.parseInt(screenDimension[1]);

        int imagesInLength = screenLength/imageLength;
        int imagesInBredth = screenBredth/imageBredth;

        int totalImages = imagesInLength*imagesInBredth;

        return totalImages;
    }
}