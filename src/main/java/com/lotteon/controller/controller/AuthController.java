package com.lotteon.controller.controller;


import com.lotteon.dto.requestDto.PostCustSignupDTO;
import com.lotteon.dto.responseDto.GetTermsResponseDto;
import com.lotteon.entity.term.Terms;
import com.lotteon.service.member.CustomerService;
import com.lotteon.service.member.MemberService;
import com.lotteon.service.member.SellerService;
import com.lotteon.service.term.TermsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final MemberService memberService;
    private final SellerService sellerService;
    private final TermsService termsService;

    @GetMapping("/login/view")
    public String login() {
        return "pages/auth/login";
    }

    @GetMapping("/join")
    public String join() {
        return "pages/auth/join";
    }
    
    // 1. 이용약관 Terms (termsType = 일반 customer,판매자 seller)
    @GetMapping("/signup")
    public String signUp(@RequestParam String termsType, Model model) {
        List<GetTermsResponseDto> getterms = termsService.selectTerms(termsType);
        log.info(getterms);
        model.addAttribute("terms", getterms); // 뷰에서 보이는 거
        model.addAttribute("termsType", termsType); // customer일 때만 선택 약관 조회
        return "pages/auth/signup";
    }

    // 2-1. 회원가입 (일반회원 정보입력) | optional : 선택약관 동의 여부
    // 체크박스 선택 여부에 따라 true=1, false=0
    @GetMapping("/customer/{optional}")
    public String customer(@PathVariable Optional<Boolean> optional, Model model) {
        log.info("체크박스 선택 여부에 따라 true=1, false=0 >>>>>>> " + optional);
        model.addAttribute("custOptional", optional);
        return "pages/auth/customer";
    }

    // 2-1. 회원가입 (일반회원 정보입력)
    @PostMapping("/customer")
    public String customer(PostCustSignupDTO postCustSignupDTO) {

        log.info("일반회원 정보입력 : " + postCustSignupDTO.toString());
        customerService.insertCustomer(postCustSignupDTO);

        return "redirect:/auth/login/view";
    }

    // 2-2. 회원가입 (판매회원 정보입력)
    @GetMapping("/seller")
    public String seller(){

        return "pages/auth/seller";
    }

    // 2-2. 회원가입 (판매회원 정보입력)
    @PostMapping("/seller")
    public String seller(PostCustSignupDTO postCustSignupDTO){

        return "redirect:/auth/login/view";
    }

}


