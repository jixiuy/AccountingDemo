package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.backup;

import android.os.AsyncTask;
import android.os.Environment;

import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetUtilsForBackup extends AsyncTask<String,Void,String > {
    public static final String BACKUP = "backup";
    public static final String RESTORE = "restore";
    private static final OkHttpClient okHttpClient =
            new OkHttpClient.Builder()
                    .connectTimeout(5,TimeUnit.SECONDS)
                    .build();


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        EasyUtils.showOneToast(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        switch (strings[0]){
            case BACKUP:{
                String url = GlobalInfo.settingInfo.getBackupUrl();
                File dbFile = new File(Environment.getDataDirectory().getAbsolutePath()+"/data/"+ GlobalConstant.APPLICATION_PACKAGE_NAME+"/databases/" +GlobalConstant.DATABASE_NAME);
                if(upload(url,dbFile)){
                    return "备份成功";
                }else {
                    return "备份失败,请检查服务器是否在线且正常运行";
                }

            }
            case RESTORE:{
                String url = GlobalInfo.settingInfo.getRestoreUrl();
                File dbFile = new File(Environment.getDataDirectory().getAbsolutePath()+"/data/"+ GlobalConstant.APPLICATION_PACKAGE_NAME+"/databases/" +GlobalConstant.DATABASE_NAME);
                if(download(url,dbFile)){
                    return "恢复成功";
                }else {
                    return "恢复失败,请检查服务器是否在线且正常运行";
                }

            }
            default:{
                return "error";
            }
        }

    }

    /**
     * 上传文件
     * @param url
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean upload(String url, File file) {
        // 获取客户端
        OkHttpClient client = okHttpClient;

        // 填充请求体
        RequestBody requestBody = new MultipartBody.Builder()
                // 设置类型为表单
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        // 建立请求
        Request request = new Request.Builder()
                .header("Authorization", "ClientID" + UUID.randomUUID())
                .url(url)
                .post(requestBody)
                .build();
        // 执行请求
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return false;
        }

        if (!response.isSuccessful()) {
            return false;
        }
        return true;
    }
    /**
     * 下载文件
     * @param url
     * @param file
     * @return
     * @throws IOException
     */
    public static Boolean download(String url, File file) {
        // 获取客户端
        OkHttpClient client = okHttpClient;
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        // 执行请求
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return false;
        }
        if (response.isSuccessful()){
            InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                return false;
            }
            try {
                byte[] buffer = new byte[2048];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.flush();
            } catch (IOException e) {
                return false;
            }
        }else{
            // 响应不成功
            return false;
        }
        return true;
    }
}
