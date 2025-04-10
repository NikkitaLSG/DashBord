package org.example.vkr3test.Error;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/error")
    public String testError() {
        throw new RuntimeException("Тестовая ошибка для проверки сохранения в БД");
    }
}