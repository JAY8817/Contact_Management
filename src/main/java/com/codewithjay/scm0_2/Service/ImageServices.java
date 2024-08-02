package com.codewithjay.scm0_2.Service;

import com.codewithjay.scm0_2.forms.ContactForm;
import org.springframework.web.multipart.MultipartFile;

public interface ImageServices {

  String uploadImage(MultipartFile file,String filename);

  String getUrlFormPublicId(String id);

}
