package com.example.songguoaccountserver.controller;

import com.example.songguoaccountserver.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
public class BackupController {
    @Autowired
    BackupService backupService;
    /**
     * 测试是否连通
     */
    @RequestMapping(value = {"/hello"}, method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }
    /**
     * 数据库文件上传
     */
    @RequestMapping(value = {"/backup"}, method = RequestMethod.POST)
    public void backup(@RequestParam MultipartFile file,HttpServletResponse httpServletResponse)
            throws Exception {

       backupService.backup(file,httpServletResponse);
       return ;
    }
    /**
     * 数据库文件下载
     */
    @RequestMapping(value = {"/restore"}, method = RequestMethod.GET)
    public void restore(HttpServletResponse httpServletResponse){
        backupService.restore(httpServletResponse);
        return ;
    }

}
