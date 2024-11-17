package com.blackorangejuice.songguojizhang.db.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackorangejuice.songguojizhang.bean.Tag;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TagMapper {
    public static final String INIT =
            "insert into t_tag (tag_name,tag_img_name,tag_img_color) values (?,?,?);\n";
    public static final String INSERT_TAG = "insert into t_tag \n" +
            "(tag_name, tag_img_name,tag_img_color)\n" +
            "values\n" +
            "(?,?,?)";
    public static final String SELECT_BY_TID = "select * from t_tag where tid = ?";
    public static final String DELETE_BY_TID = "delete from t_tag where tid = ?";
    public static final String SELECT_BY_TAG_NAME = "select * from t_tag where tag_name = ?";

    public static final String SELECT_ALL = "select * from t_tag";

    EazyDatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public TagMapper(EazyDatabaseHelper songGuoDatabaseHelper) {
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();
    }

    /**
     * 初始化tag
     *  0xFF0099CC 4278229452;
     *  0xFF99CC66 4288296414;
     *  0xFFCC9933 4291598643;
     *  0xFF33CC33 4281584691;
     *  0xFFFF6666 4294927974;
     */
    public void init() {
        String strs[][] = {
                {"交通","jiaotong","4278229452"},
                {"超市","chaoshi","4288296414"},
                {"充值","chongzhi","4291598643"},
                {"服饰","fushi","4281584691"},
                {"吃喝","chihe","4294927974"},
                {"买菜","maicai","4278229452"},
                {"化妆","huazhuang","4288296414"},
                {"日用","riyong","4281584691"},
                {"红包","hongbao","4291598643"},
                {"话费","huafei","4294927974"},
                {"娱乐","yvle","4278229452"},
                {"医疗","yiliao","4288296414"},
                {"学习","xuexi","4278229452"},
                {"微信","weixin","4281584691"},
                {"支付宝","zhifubao","4288296414"},
                {"工资","gongzi","4294927974"},
                {"投资","touzi","4288296414"},
                {"奖金","jiangjin","4278229452"},
                {"其他","qita","4291598643"}

        };
        for(String s[]:strs){
            sqLiteDatabase.execSQL(INIT, new String[]{s[0],s[1]+".jpg",s[2]});
        }
    }

    public Tag insertTag(Tag tag) {
        Tag tag0 = selectByTagName(tag.getTagName());
        // 如果不存在该名称的tag,则插入
        if (tag0 == null) {
            sqLiteDatabase.execSQL(INSERT_TAG, new String[]{tag.getTagName(), tag.getTagImgName(),String.valueOf(tag.getTagImgColor())});
        }else {
            return null;
        }
        return selectByTagName(tag.getTagName());
    }

    /**
     * 按tid查找标签
     *
     * @param tid
     * @return
     */
    public Tag selectByTid(Integer tid) {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_TID, new String[]{String.valueOf(tid)});
        Tag tag = null;
        if (cursor.moveToFirst()) {
            tag = new Tag();
            tag.setTid(cursor.getInt(cursor.getColumnIndex("tid")));
            tag.setTagName(cursor.getString(cursor.getColumnIndex("tag_name")));
            tag.setTagImgName(cursor.getString(cursor.getColumnIndex("tag_img_name")));
            tag.setTagImgColor(cursor.getInt(cursor.getColumnIndex("tag_img_color")));
        }
        cursor.close();
        return tag;
    }

    /**
     * 按标签名查找标签
     *
     * @param tagName
     * @return
     */
    public Tag selectByTagName(String tagName) {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_TAG_NAME, new String[]{tagName});
        Tag tag = null;
        if (cursor.moveToFirst()) {
            tag = new Tag();
            tag.setTid(cursor.getInt(cursor.getColumnIndex("tid")));
            tag.setTagName(cursor.getString(cursor.getColumnIndex("tag_name")));
            tag.setTagImgName(cursor.getString(cursor.getColumnIndex("tag_img_name")));
            tag.setTagImgColor(cursor.getInt(cursor.getColumnIndex("tag_img_color")));
        }
        cursor.close();
        return tag;
    }

    /**
     * 查找所有tag
     *
     * @return
     */
    public List<Tag> selectAll() {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL, null);
        List<Tag> tags = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setTid(cursor.getInt(cursor.getColumnIndex("tid")));
                tag.setTagName(cursor.getString(cursor.getColumnIndex("tag_name")));
                tag.setTagImgName(cursor.getString(cursor.getColumnIndex("tag_img_name")));
                tag.setTagImgColor(cursor.getInt(cursor.getColumnIndex("tag_img_color")));
                tags.add(tag);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return tags;
    }

    /**
     * 删除tag
     *  这里不是简单的删除那么简单，因为会有账单已经使用了该标签，所以需要先进行判断
     * @return
     */
    public boolean deleteByTid(Tag tag) {

        AccountItemMapper accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
        if(! accountItemMapper.ifExistAccountItemUseThisTag(tag)){
            sqLiteDatabase.execSQL(DELETE_BY_TID, new String[]{String.valueOf(tag.getTid())});
            return true;
        }else {
            return false;
        }

    }

}
