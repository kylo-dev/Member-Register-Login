package com.coding.recipe.member.controller;

import com.coding.recipe.member.dto.MemberDTO;
import com.coding.recipe.member.entity.MemberEntity;
import com.coding.recipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    // 생성자 주입
    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    // 회원가입 처리 (form 데이터 받음)
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);

        MemberEntity member = memberService.save(memberDTO);
        if (member != null){
            return "login";
        } else {
            return "save";
        }
    }

    // 이메일 중복 체크
    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);

        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult; // success: function(res)의 res에 값 전달
    }

    //    @PostMapping("/member/save")
    //    public String save(@RequestParam("memberEmail") String memberEmail,
    //                       @RequestParam("memberPassword") String memberPassword,
    //                       @RequestParam("memberName") String memberName) {
    //        System.out.println("MemberController.save");
    //        System.out.println("memberEmail = " + memberEmail + ", memberPassword = " + memberPassword + ", memberName = " + memberName);
    //        return null;
    //    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    // login.html의 name 속성인 input 태그의 값을 전달 (!! 받는 자바 class의 속성값만 일치해야함 !!)
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/member/list")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();

        // 어떠한 html로 가져갈 데이터가 있다면 model을 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    // 회원 상세 조회
    @GetMapping("/member/{id}") // @PathVariable 경로상에 값을 가져올 때 사용
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    // 수정 페이지로 이동
    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateFrom(myEmail);

        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);

        return "redirect:/member/list";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
}
