package com.example.springboot.temporary;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: yiqq
 * @date: 2018/9/29
 * @description:
 */
public class exportStudent {

    @RequestMapping("/exports")
    public String toExport(HttpServletResponse response) {

        return null;
    }
}
