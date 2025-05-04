package com.focus.lit.service;

import com.focus.lit.model.User;

public interface TelegramService {
    Integer createForumTopic(String topicName);
    String generateInviteLink(int userId);
}
