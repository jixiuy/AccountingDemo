package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.moresetting.exports;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.ExportItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.other.ExcelUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ExportsActivity extends BasicActivity {
    TextView backTextView;
    LinearLayout exportExcelLinearLayout;
    EazyDatabaseHelper songGuoDatabaseHelper;
    AccountItemMapper accountItemMapper;
    public static final int REQUEST_PERMISSIONS_CODE = 1;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, ExportsActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exports);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(ExportsActivity.this);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_exports_back_textview);
        exportExcelLinearLayout = findViewById(R.id.activity_exports_excel);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExportsActivity.this.finish();
            }
        });
        exportExcelLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useEasyPermission();
            }
        });
    }


    @AfterPermissionGranted(REQUEST_PERMISSIONS_CODE)
    public void export() {
        // 这里找不到文件异常，原因是权限问题
        EasyUtils.showOneToast( "正在导出账单.........");
        String filePath = Environment.getExternalStorageDirectory() + "/SongGuoExportExcel";
        File file = new File(Environment.getExternalStorageDirectory(), "SongGuoExportExcel");
        if (!file.exists()) {
            file.mkdirs();
        }
//        if (file.exists()) {
//            System.out.println("yes");
//        }
        String excelName = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒导出账单").format(new Date());
        String accountBookName = GlobalInfo.currentAccountBook.getAccountBookName();

        String excelFileName = excelName + ".xls";

        String sheetName = accountBookName;

        List<ExportItem> exportItems = accountItemMapper.exportToExcel();
        if(!exportItems.isEmpty()){
            ExcelUtil.initExcel(filePath, excelFileName);
            ExcelUtil.writeToExcel(exportItems, filePath, excelFileName, ExportsActivity.this);
        }else {
            EasyUtils.showOneToast("您没有账单可以导出");
        }


    }

    public void useEasyPermission() {
        // 这里最小sdk设置了23，所以权限都是需要申请的
        // 所以这里的判断其实没有用处
        if (Build.VERSION.SDK_INT >= 23) {

            String[] perms = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(this, perms)) {
                export();
            } else {
                // Do not have permissions, request them now
                EasyPermissions.requestPermissions(this, "请允许本应用的读写文件权限", REQUEST_PERMISSIONS_CODE, perms);
            }
        } else {
            export();
        }
    }

    /**
     * 申请权限后的回调方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }
}