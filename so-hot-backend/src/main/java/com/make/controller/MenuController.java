package com.make.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author make
 * @since 2022-08-24
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @GetMapping("/teach")
    @PreAuthorize("hasAuthority('menu:teach')")
    public String teach() {
        return "教书育人";
    }

    @GetMapping("/study")
    @PreAuthorize("hasAuthority('menu:study')")
    public String study() {
        return "好好学习，报效祖国";
    }

    @GetMapping("/patrol")
    @PreAuthorize("hasAuthority('menu:patrol')")
    public String patrol() {
        return "认真巡逻，保护一方";
    }

    @GetMapping("/paid")
    @PreAuthorize("hasAuthority('menu:paid')")
    public String paid() {
        return "发工资喽";
    }

    @GetMapping("/pay")
    @PreAuthorize("hasAuthority('menu:pay')")
    public String pay() {
        return "交学费吧";
    }

}
