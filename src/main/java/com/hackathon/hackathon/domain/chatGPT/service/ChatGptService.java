package com.hackathon.hackathon.domain.chatGPT.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.hackathon.domain.chatGPT.dto.request.ChatGptRequestDTO;
import com.hackathon.hackathon.domain.chatGPT.dto.request.QuestionRequestDTO;
import com.hackathon.hackathon.domain.chatGPT.dto.response.ChatGptColorResponseDTO;
import com.hackathon.hackathon.domain.chatGPT.dto.response.ChatGptResponseDTO;
import com.hackathon.hackathon.domain.chatGPT.dto.response.ChatGptResponseDTO.Choice;
import com.hackathon.hackathon.domain.chatGPT.config.ChatGptConfig;
import com.hackathon.hackathon.domain.chatGPT.entity.ChatGptMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatGptService {
    private final RestTemplate restTemplate;


    //api key를 application.yml에 넣어두었습니다.
    @Value("${chatgpt.api-key}")
    private String apiKey;

    public ChatGptResponseDTO buildHttpEntity(ChatGptRequestDTO chatGptRequest){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        httpHeaders.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + apiKey);
        return getResponse(new HttpEntity<>(chatGptRequest, httpHeaders));
    }

    public ChatGptResponseDTO getResponse(HttpEntity<ChatGptRequestDTO> chatGptRequestHttpEntity){

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        //답변이 길어질 경우 TimeOut Error가 발생하니 1분정도 설정해줍니다.
        requestFactory.setReadTimeout(60 * 1000);   //  1min = 60 sec * 1,000ms
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<ChatGptResponseDTO> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.CHAT_URL,
                chatGptRequestHttpEntity,
                ChatGptResponseDTO.class);

        return responseEntity.getBody();
    }
    public ChatGptColorResponseDTO askQuestion(String questionRequest){
        List<ChatGptMessage> messages = new ArrayList<>();
        messages.add(ChatGptMessage.builder()
                .role(ChatGptConfig.ROLE)
                .content(questionRequest)
                .build());
        ChatGptResponseDTO test = buildHttpEntity( new ChatGptRequestDTO(
                ChatGptConfig.CHAT_MODEL,
                ChatGptConfig.MAX_TOKEN,
                ChatGptConfig.TEMPERATURE,
                ChatGptConfig.STREAM,
                messages
                //ChatGptConfig.TOP_P
        ));
//        System.out.println(test.getChoices().get(1).getMessage().getContent());
//
//        // Json에서 color만 추출해 반환
//        ChatGptResponseDTO.Choice firstChoice = test.getChoices().get(0);
//        String tt = test.getChoices().get(0).getFinishReason();
//        System.out.println(tt);
//        System.out.println(firstChoice);
//        test.getChoices().get(0).getMessage();
//        String color = String.valueOf(firstChoice.getMessage());
//        System.out.println(color.toString());
        ChatGptColorResponseDTO chatGptColorResponseDTO = ChatGptColorResponseDTO.builder()
                        .color(test.getChoices().get(0).getMessage().getContent())
                        .build();
        return chatGptColorResponseDTO;
    }
}