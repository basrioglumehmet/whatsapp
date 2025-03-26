package com.whatsapp.backend.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "profiles")

public class Profile
{
    @Id
    private String id;
    private byte[] image;
    private byte[] thumbnail;
    private String description;

}
