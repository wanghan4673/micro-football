package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.SystemInfoService;
import com.football.administrator.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/news")
@Slf4j
public class AdminNewsController {

    @GetMapping("")
    public Result getNews(){
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteNews(@PathVariable("id") Integer id){
        return Result.success();
    }
}
