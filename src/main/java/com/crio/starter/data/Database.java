package com.crio.starter.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents an entity that maintains the current sequenceNo for a post to be saved.
 */

@Document(collection = "database_sequences")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Database {
    
    @Id
    private String id;

    private long sequenceNo;
}