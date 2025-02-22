package com.mandarin.petching.controller;

import com.mandarin.petching.domain.ChatMessage;
import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;
    private final MemberRepository memberRepository;

    @GetMapping("/chat/{petSitterId}")
    public String createRoom(Authentication authentication, @PathVariable Long petSitterId) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);
        Long petOwnerId = member.getId();

        Long roomId = chatService.createRoom(petOwnerId, petSitterId);
        return "redirect:/chat/room/" + roomId;
    }

    @GetMapping("/chat/room/{roomId}")
    public String enterRoom(Authentication authentication, @PathVariable Long roomId, Model model) {

        List<ChatMessage> chatList = chatService.findAllChatByRoomId(roomId);

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);
        String username = member.getUserName();

        model.addAttribute("roomId", roomId);
        model.addAttribute("chatList", chatList);
        model.addAttribute("username", username);

        return "chat/room";
    }
}
