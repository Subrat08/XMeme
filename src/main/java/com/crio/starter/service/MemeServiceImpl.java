package com.crio.starter.service;

import java.util.List;
import java.util.Map;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.crio.starter.exchange.ResponseDto;
import com.crio.starter.data.Meme;
import com.crio.starter.exception.InvalidPostException;
import com.crio.starter.exception.PostNotFoundException;
import com.crio.starter.exception.DuplicatePostException;
import com.crio.starter.repository.MemeRepository;
import com.crio.starter.repository.MemeSequenceGenerator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the MemeService contract. 
 */

@Service
public class MemeServiceImpl implements MemeService{
    
    @Autowired
    private MemeRepository repositoryService;

    @Autowired
    private MemeSequenceGenerator sequenceGenerator;

    private ModelMapper mapper = new ModelMapper();

    /**
     * This function gets 100 most recent meme post entites by calling RepositoryService.
     * @return List<ResponseDto>
     */
    public List<ResponseDto> getMemes(){

        List<Meme> postEntities = repositoryService.getMemes();
        for (Meme meme : postEntities) {
            System.out.println(meme.getDateOfPosting()+" "+meme.getId());
        }
        List<ResponseDto> posts = new ArrayList<>();

        for (Meme post:postEntities) {
            posts.add(mapper.map(post, ResponseDto.class));
        }

        return posts;
    }

    /**
     * This function gets a single instance of Meme by calling the RepositoryService
     * @param long postId
     * @return A single ResponseDto object
     * @throws PostNotFoundException if RepositoryService did not find a post with the given postId in the database
     */
    public ResponseDto getMeme(long postId) throws PostNotFoundException{
        Meme Meme = repositoryService.getMeme(postId);
        return mapper.map(Meme, ResponseDto.class);
    }

    /**
     * This function creates Meme from a ResponseDto and calls RepositoryService to save the Meme into the database.
     * Before calling the RepositoryService it first validates all the fields in the provided ResponseDto
     * @param ResponseDto post
     * @return long postId - The id of the saved Meme
     * @throws InvalidPostException if the validation of the ResponseDto fails
     * @throws DuplicatePostException
     */
    public long saveMeme(ResponseDto post) throws InvalidPostException, DuplicatePostException {

        validatePost(post);

        Meme entity = Meme.builder()
                                .id(sequenceGenerator.generateSequence(Meme.SEQUENCE_NAME))
                                .name(post.getName())
                                .url(post.getUrl())
                                .caption(post.getCaption())
                                .dateOfPosting(LocalDateTime.now())
                                .build();
        long postId = repositoryService.saveMeme(entity);
        return postId;
    }


    /**
     * Function to update an existing Meme with a given id in the database. 
     * @param Map<String, Object> updates
     * @param long postId
     * @throws PostNotFoundException - If the post with the given id for update is not found.
     * @throws InvalidPostException - If the updates are invalid
     */
    public void updateMeme(Map<String, Object> updates, long postId) throws PostNotFoundException, InvalidPostException{

        Object newUrl = updates.get("url");

        Object newCaption = updates.get("caption");

        if (newUrl != null && isImageUrl(newUrl.toString()) == false) {
            throw new InvalidPostException("Update failed because upadated url is not a valid image url.");
        }

        if (newCaption != null && newCaption.toString().isBlank()) {
            throw new InvalidPostException("Update failed because updated caption is blank.");
        }

        repositoryService.updateMeme(updates, postId); 
    }

    /**
     * Function to validate the fields of a ResponseDto
     * @param post
     * @throws InvalidPostException
     * @throws DuplicatePostException
     */
    private void validatePost(ResponseDto post) throws InvalidPostException, DuplicatePostException{

        String name = post.getName();
        String url = post.getUrl();
        String caption = post.getCaption();

        if (name == null || name.isBlank()){
            throw new InvalidPostException("The name cannot be empty");
        }

        if (url == null || url.isBlank()){
            throw new InvalidPostException("The url cannot be empty");
        }

        if (caption == null || caption.isBlank()) {
            throw new InvalidPostException("The caption cannot be empty.");
        }

        if (isImageUrl(url) == false) {
            throw new InvalidPostException("The url is not a valid image url.");
        }

        Meme meme = repositoryService.findByNameAndCaptionAndUrl(post.getName(), post.getCaption(), post.getUrl());
        if(meme!=null){
            throw new DuplicatePostException("Duplicate Post");
        }
    }


    /**
     * Function to check if the provided url contains an image
     * @param uri
     * @return true if a url is a valid image url or else false
     */
    private boolean isImageUrl(String uri) {

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                                            .uri(URI.create(uri))
                                            .GET()
                                            .build();
            
            HttpResponse<String> response  = client.send(request, HttpResponse.BodyHandlers.ofString());

            String contentType = response.headers().allValues("content-type").get(0);
            
            if (contentType.equals("image/jpeg") || contentType.equals("image/jpg") || contentType.equals("image/png")
                || contentType.equals("image/JPEG") || contentType.equals("image/JPG") || contentType.equals("image/PNG")) return true;
            
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}