package com.blackorangejuice.songguojizhang.utils.other;

import android.content.Context;

import com.blackorangejuice.songguojizhang.bean.ExportItem;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @author dmrfcoder
 * @date 2018/8/9
 */
public class ExcelUtil {
    private static WritableFont arial14font = null;

    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";

    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Excel
     *
     * @param filePath 导出excel存放的地址（目录）
     * @param fileName 导出excel文件名及表格名
     */
    public static void initExcel(String filePath, String fileName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(filePath + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet(fileName, 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, fileName, arial14format));
            String [] colName = new String[]{"收入/支出","金额","标签","备注","记账时间","绑定事件"};
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            EasyUtils.showOneToast("出现未知问题，请退出应用重试"+e.toString());
            EazyActivityController.finishAll();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出到excel
     *
     * @param exportItems
     * @param filePath
     * @param fileName
     * @param c
     */
    @SuppressWarnings("unchecked")
    public static void writeToExcel(List<ExportItem> exportItems, String filePath, String fileName, Context c) {
        String filePathAndName = filePath + "/" + fileName;
        if (exportItems != null && exportItems.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                // 获取表格对象
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(filePathAndName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(filePathAndName), workbook);
                WritableSheet sheet = writebook.getSheet(0);

                for (int j = 0; j < exportItems.size(); j++) {
                    ExportItem exportItem = exportItems.get(j);
                    List<String> list = new ArrayList<>();
                    switch (exportItem.getIncomeOrExpenditure()){
                        case GlobalConstant.INCOME:
                            list.add("收入");
                            break;
                        case GlobalConstant.EXPENDITURE:
                            list.add("支出");
                            break;

                    }

                    list.add(exportItem.getSum().toString());
                    list.add(exportItem.getTagName());
                    list.add(exportItem.getRemark());
                    list.add(exportItem.getTime());
                    if(exportItem.getEventItemTitle() != null){
                        list.add(exportItem.getEventItemTitle());
                    }else {
                        list.add("");
                    }


                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j+1, list.get(i), arial12format));
                        if (list.get(i).length() <= 4) {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length()+8);
                        } else {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length()+5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(j+1, 350);
                }

                writebook.write();
                EasyUtils.showOneToast("导出成功，请前往/手机内部存储根目录/SongGuoExportExcel/下查看");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}