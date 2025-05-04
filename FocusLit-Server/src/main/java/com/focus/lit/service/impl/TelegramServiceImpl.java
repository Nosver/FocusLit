package com.focus.lit.service.impl;

import com.focus.lit.model.User;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramServiceImpl implements TelegramService {

    @Value("${T_BOT}")
    private String botToken;

    @Value("${T_CHAT_ID}")
    private String chatId;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer createForumTopic(String topicName) {
        String url = "https://api.telegram.org/bot" + botToken + "/createForumTopic";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("chat_id", chatId);
        requestBody.put("name", topicName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(response.getBody().get("ok"))) {
            Map<String, Object> result = (Map<String, Object>) response.getBody().get("result");
            return (Integer) result.get("message_thread_id");
        } else {
            throw new RuntimeException("Failed to create topic on Telegram: " + response.getBody());
        }
    }

    @Override
    public String generateInviteLink(int userId) {
        User user = userRepository.findById(userId).orElseThrow();

        String url = "https://api.telegram.org/bot" + botToken + "/createChatInviteLink";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("chat_id", chatId);
        requestBody.put("member_limit", 1); // one-person link
        requestBody.put("name", "Invite for " + user.getUsername()); // optional label

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(response.getBody().get("ok"))) {
            Map<String, Object> result = (Map<String, Object>) response.getBody().get("result");
            return (String) result.get("invite_link");
        } else {
            throw new RuntimeException("Failed to generate invite link: " + response.getBody());
        }
    }

}
