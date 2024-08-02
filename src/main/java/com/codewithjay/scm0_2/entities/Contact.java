package com.codewithjay.scm0_2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class  Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String contactImage;
    private String description;
    private String picture;

    private Boolean favorite=false;

    private String websiteLink;

    private String linkedInLink;

    private String cloudinaryImagePublicId;

    @ManyToOne
    @JsonIgnore(value = true)
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<socialLink> socialLink = new ArrayList<>();


}
