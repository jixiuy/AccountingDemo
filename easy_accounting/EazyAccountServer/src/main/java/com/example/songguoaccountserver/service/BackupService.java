package com.example.songguoaccountserver.service;

import com.example.songguoaccountserver.utils.SongGuoConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Service
public class BackupService {
    public static final String OPTION_VALUES_KEY = "filePath";
    @Autowired
    ApplicationArguments args;

    /**
     * 备份
     */
    public void backup(MultipartFile originalFile,HttpServletResponse httpServletResponse) {
        // 获取选项参数
        List<String> pathPath = args.getOptionValues(OPTION_VALUES_KEY);
        // 获取文件路径
        String filePath = "";
        if(pathPath != null) {
            filePath = pathPath.get(0);
        }
        // 原始文件名
        String originalFilename = originalFile.getOriginalFilename();
        // 拼接目标文件名
        String targetFileName = filePath + originalFilename;
        // 初始化目标文件
        File saveFile = new File(targetFileName);
        // 判断文件夹是否存在
        File dir = new File(filePath);
        if(!dir.exists()){
            dir.mkdir();
        }
        if(!saveFile.exists()){
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        OutputStream out = null;
        try {
            // 获取目标文件输出流
            out = new FileOutputStream(saveFile);
            // 获取原始文件字节数组
            byte[] ss = originalFile.getBytes();
            for (int i = 0; i < ss.length; i++) {
                // 一个一个字节写入目标输出流
                out.write(ss[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    /**
     * 恢复
     *
     * @param httpServletResponse
     */
    public void restore(HttpServletResponse httpServletResponse) {
        // 响应头设置
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("content-Type", "application/octet-stream");
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + SongGuoConstant.DATABASE_NAME);
        ServletOutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            outputStream = httpServletResponse.getOutputStream();
            // 获取选项参数
            List<String> pathPath = args.getOptionValues(OPTION_VALUES_KEY);
            // 获取文件路径
            String filePath = "";
            if(pathPath != null) {
                filePath = pathPath.get(0);
            }
            // 拼接文件路径
            // 拼接目标文件名
            String savedFileName = filePath + SongGuoConstant.DATABASE_NAME;
            File savedFile = new File(savedFileName);
            if(!savedFile.exists()){
                // 文件不存在则报错
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            fileInputStream = new FileInputStream(savedFile);
            // 读取到的长度
            int len = 0;
            // 暂存输入数据的数组
            byte[] s = new byte[1024];
            while ((len = fileInputStream.read(s)) != -1) {
                // s: 从哪个数组读取数据, 0: 从下标0开始, len: 到下标len结束;
                outputStream.write(s, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }

    }
}
