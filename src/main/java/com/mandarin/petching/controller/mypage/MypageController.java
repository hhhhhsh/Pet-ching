package com.mandarin.petching.controller.mypage;

import com.mandarin.petching.domain.ChatRoom;
import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MyPageService myPageService;
    private final MemberRepository memberRepository;

    @GetMapping
    public String mypage(Authentication authentication, @RequestParam String type, Model model) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        model.addAttribute("member", member);
        model.addAttribute("type", type);
        return "mypage/mypage";
    }

    @GetMapping("/chats")
    public String viewChatList(Authentication authentication, Model model) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        List<ChatRoom> chatRoomList = myPageService.getChatList(member.getId());

        model.addAttribute("chatRoomList", chatRoomList);

        return "mypage/chatList";
    }
}
