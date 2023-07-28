package com.crio.starter.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This entity represents an meme post entity to be persisted in the collection "userPosts"
 */

@Document(collection = "userPosts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Meme {

    @Transient
    public static final String SEQUENCE_NAME = "posts_sequence";
    
    @Id
    private long id;

    private String name;

    private String url;

    private String caption;

    private LocalDate dateOfPosting;
}