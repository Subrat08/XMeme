package com.crio.starter.repository;

import java.util.List;
import java.util.Map;

import com.crio.starter.data.Meme;
import com.crio.starter.exception.PostNotFoundException;

public interface MemeRepository {
    
    List<Meme> getMemes();

    Meme getMeme(long postId) throws PostNotFoundException;

    long saveMeme(Meme post);

    void updateMeme(Map<String, Object> updates, long postId) throws PostNotFoundException;
}