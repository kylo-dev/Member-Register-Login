package com.coding.recipe.member.controller.ajax;

import com.coding.recipe.member.dto.ajax.AjaxDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AjaxController {

    @GetMapping("/ex01")
    public String ex01() {
        System.out.println("AjaxController.ex01 요청");
        return "/ajax/main";
    }

    @PostMapping("/ex02")
    public @ResponseBody String ex02() {
        System.out.println("AjaxController.ex02 요청");
        return "/ajax/main";
    }

    @GetMapping("/ex03")
    public @ResponseBody String ex03(@RequestParam("param1") String param1,
                                     @RequestParam("param2") String param2) {
        System.out.println("param1 = " + param1 + ", param2 = " + param2);
        return "ex03 메서드 호출";
    }

    @PostMapping("/ex04")
    public @ResponseBody String ex04(@RequestParam("param1") String param1,
                                     @RequestParam("param2") String param2) {
        System.out.println("param1 = " + param1 + ", param2 = " + param2);
        return "ex04 메서드 호출";
    }

    @GetMapping("/ex05")
    public @ResponseBody AjaxDto ex05(AjaxDto ajaxDto) {
        System.out.println("param1 = " + ajaxDto.getParam1() + ", param2 = " + ajaxDto.getParam2());
        return ajaxDto;
    }

    @PostMapping("/ex06")
    public @ResponseBody AjaxDto ex06(AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);
        return ajaxDto;
    }

    // @RequestBody를 바인딩할 때 기본 생성자를 사용한다. -> DTO에 기본 생성자가 있어야 함
    @PostMapping("/ex07")
    public @ResponseBody AjaxDto ex07(@RequestBody AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);
        return ajaxDto;
    }

    private List<AjaxDto> DtoList() {
        List<AjaxDto> ajaxDtoList = new ArrayList<>();
        ajaxDtoList.add(new AjaxDto("data1", "data11"));
        ajaxDtoList.add(new AjaxDto("data2", "data22"));
        return ajaxDtoList;
    }

    @PostMapping("/ex08")
    public @ResponseBody List<AjaxDto> ex08(@RequestBody AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);
        List<AjaxDto> dtoList = DtoList();
        dtoList.add(ajaxDto);
        return dtoList;
    }

    @PostMapping("/ex09")
    public ResponseEntity ex09(@RequestBody AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);
        return new ResponseEntity<>(ajaxDto, HttpStatus.OK);
    }

    @PostMapping("/ex10")
    public ResponseEntity ex10(@RequestBody AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);
        List<AjaxDto> dtoList = DtoList();
        dtoList.add(ajaxDto);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
