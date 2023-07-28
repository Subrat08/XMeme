package com.crio.starter.service;

import java.util.List;
import java.util.Map;

import com.crio.starter.exchange.ResponseDto;
import com.crio.starter.exception.DuplicatePostException;
import com.crio.starter.exception.InvalidPostException;
import com.crio.starter.exception.PostNotFoundException;

public interface MemeService {
    
    List<ResponseDto> getMemes();

    ResponseDto getMeme(long postId) throws PostNotFoundException;

    long saveMeme(ResponseDto post) throws InvalidPostException, DuplicatePostException;

    void updateMeme(Map<String, Object> updates, long postId) throws PostNotFoundException, InvalidPostException;
}