package com.hackathon.hackathon.domain.chatGPT.controller;

import com.hackathon.hackathon.domain.chatGPT.dto.request.QuestionRequestDTO;
import com.hackathon.hackathon.domain.chatGPT.dto.response.ChatGptResponseDTO;
import com.hackathon.hackathon.domain.chatGPT.service.ChatGptService;
import com.hackathon.hackathon.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RequiredArgsConstructor
@RequestMapping("/chat-gpt")
@RestController
public class ChatGptController {
    //private final ApiResponse apiResponse;
    private final ChatGptService chatGptService;

    @Operation(summary = "Question to Chat-GPT")
    @PostMapping("/question")
    public SuccessResponse<ChatGptResponseDTO> sendQuestion(
            Locale locale,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody QuestionRequestDTO questionRequest) {
        String code = "200";
        ChatGptResponseDTO chatGptResponse = null;
        try {
            chatGptResponse = chatGptService.askQuestion(questionRequest);
            System.out.println(chatGptResponse);
        } catch (Exception e) {
//            apiResponse.printErrorMessage(e);
            code = e.getMessage();
        }
        //return 부분은 자유롭게 수정하시면됩니다. ex)return chatGptResponse;
        return SuccessResponse.onSuccess(200, chatGptResponse);
    }
}