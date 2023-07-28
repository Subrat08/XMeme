
package com.crio.starter.controller;

import java.util.List;
import java.util.Map;

import com.crio.starter.exchange.ResponseDto;
import com.crio.starter.exception.DuplicatePostException;
import com.crio.starter.exception.InvalidPostException;
import com.crio.starter.exception.PostNotFoundException;
import com.crio.starter.exchange.SaveMemeResponse;
import com.crio.starter.service.MemeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class MemeController {
    
    @Autowired
    private MemeService memeService;

    public static final String URI = "/memes";

    @GetMapping(URI)
    public List<ResponseDto> getMemes() {
        return memeService.getMemes();
    }

    @GetMapping(URI+"/{id}")
    public ResponseDto getMeme(@PathVariable(name = "id") long id) throws PostNotFoundException {
        return memeService.getMeme(id);
    }

    @PostMapping(URI)
    public SaveMemeResponse saveMeme(@RequestBody ResponseDto post) throws InvalidPostException, DuplicatePostException{
        long postId = memeService.saveMeme(post);
        SaveMemeResponse response = new SaveMemeResponse(String.valueOf(postId));
        return response;
    }

    @PatchMapping(URI+"/{id}")
    public ResponseEntity<?> updateMeme(@RequestBody Map<String, Object> updates, @PathVariable(name = "id") long id) throws PostNotFoundException, InvalidPostException{
        memeService.updateMeme(updates, id);
        return ResponseEntity.ok("Post updated");
    }
}