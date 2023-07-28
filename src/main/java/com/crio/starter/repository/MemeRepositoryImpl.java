package com.crio.starter.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.crio.starter.data.Meme;
import com.crio.starter.exception.PostNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Service class that implements RepositoryService contract
 */

@Service
public class MemeRepositoryImpl implements MemeRepository{
    
    @Autowired
    private MongoTemplate mongoTemplate;

    
    /**
     * Function to get the bottom 100 entities from the database
     * @return List<Meme> posts
     */
    @Override
    public List<Meme> getMemes() {

        // // Query to get the bottom 100 documents from the mongodb collection.
        // Query query = new Query().with(Sort.by(Direction.DESC, "id")).limit(100);
        
        // List<Meme> posts = mongoTemplate.find(query, Meme.class, "userPosts");
        
        // Collections.reverse(posts);

        // return posts;
        Query query = new Query()
                .with(Sort.by(Sort.Direction.DESC, "dateOfPosting")) // Replace "timestampField" with the field you want to use for sorting
                .limit(100);

        List<Meme> posts = mongoTemplate.find(query, Meme.class);
        Collections.reverse(posts);
        return posts;
    }


    /**
     * This function gets a single Meme instance from database having the provided id and returns it
     * @param long postId
     * @return Meme
     * @throws PostNotFoundException if there is no post with the given id in the database  
     */
    @Override
    public Meme getMeme(long postId) throws PostNotFoundException {
        Query query = new Query(Criteria.where("id").is(postId));
        Meme post = mongoTemplate.findOne(query, Meme.class, "userPosts");
        if (post == null) throw new PostNotFoundException();
        return post;
    }


    /**
     * This function saves a Meme instance in the database.
     * @return long postId - id of the saved instance
     */
    @Override
    public long saveMeme(Meme post) {
        Meme savedPost = mongoTemplate.save(post, "userPosts");
        return savedPost.getId();
    }


    /**
     * This function updates an existing post in the database.
     * @param Map<String, Object> updates
     * @param long postId
     * @throws PostNotFoundException if the entity with the given id is not found in the database
     */
    @Override
    public void updateMeme(Map<String, Object> updates, long postId) throws PostNotFoundException{
        Query query = new Query(Criteria.where("id").is(postId));
        Meme post = mongoTemplate.findOne(query, Meme.class, "userPosts");
        if (post == null) throw new PostNotFoundException();
        
        Object newUrl = updates.get("url");
        Object newCaption = updates.get("caption");

        if (newUrl != null) post.setUrl(newUrl.toString());
        
        if (newCaption != null) post.setCaption(newCaption.toString());

        mongoTemplate.save(post);
    }


    @Override
    public Meme findByNameAndCaptionAndUrl(String name, String caption, String url) {
        Query query = new Query(
            Criteria.where("name").is(name)
                    .and("caption").is(caption)
                    .and("url").is(url)
        );

        return mongoTemplate.findOne(query, Meme.class);
    }
}