package com.codewithjay.scm0_2.Service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.codewithjay.scm0_2.Service.ImageServices;
import com.codewithjay.scm0_2.helper.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServicesImpl implements ImageServices {

    @Autowired
    private Cloudinary cloudinary;

    public ImageServicesImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String uploadImage(MultipartFile contactImage,String filename) {

        String id = UUID.randomUUID().toString();

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data , ObjectUtils.asMap("public_id",id));

            return this.getUrlFormPublicId(id);

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }

    }

    @Override
    public String getUrlFormPublicId(String id) {

        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstants.Width)
                                .height(AppConstants.Height)
                                .crop(AppConstants.Crop))
                .generate(id);

    }
}
