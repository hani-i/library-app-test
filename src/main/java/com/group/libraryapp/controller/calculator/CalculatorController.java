package com.group.libraryapp.controller.calculator;


import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //클래스를 api의 진입지점으로 만들어줌. api가 메소드를 사용하게끔
public class CalculatorController {

    @GetMapping("/add") // Get /add
    public int addTwoNumbers(CalculatorAddRequest request){
        return request.getNumber1() + request.getNumber2(); // ctrl + w , ctrl + alt + o -> 알아서 사용 안하는 패키지 없애줌
    }

    @PostMapping("/multiply") // POST /multiply
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request){
        return request.getNumber1() * request.getNumber2();
    }

}
