package com.luizhenriue.crudapi.controller;

import com.luizhenriue.crudapi.dto.auth.UserResponse;
import com.luizhenriue.crudapi.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> findAll(@RequestParam(required = false, defaultValue = "false") boolean onlyBarbers) {
        return onlyBarbers ? userService.findBarbers() : userService.findAll();
    }
}
