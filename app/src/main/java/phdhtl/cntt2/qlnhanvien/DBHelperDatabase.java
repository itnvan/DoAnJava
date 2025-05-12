package phdhtl.cntt2.qlnhanvien;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NhanVien.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_NV = "NV";
    private static final String TABLE_LOGIN = "login";
    public DBHelperDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqltext = "CREATE TABLE NV(nv_id integer PRIMARY KEY, nv_name text, nv_namsinh integer, nv_hsl Float, nv_chucvu text, NVimage text);\n"
                + "INSERT INTO NV VALUES(1, 'Van An', 2002, 99.99, 'Giám Đốc','');\n"
                + "INSERT INTO NV VALUES(2, 'Andiez', 2004, 3.99, 'Nhân Viên','');\n"
                + "INSERT INTO NV VALUES(3, 'Quan', 2003, 1.99, 'Nhân Viên','');\n"
                + "INSERT INTO NV VALUES(4, 'Đuc Thien', 2002, 0.99, 'Nhân Viên','');\n"
                + "INSERT INTO NV VALUES(5, 'Khanh Duy', 2003, 0.199, 'Nhân Viên','');\n";

        for (String sql : sqltext.split("\n"))
            db.execSQL(sql);
        String sqllogin="CREATE TABLE login(username text PRIMARY KEY, password text, fullname text,gender text,namsinh int)";
        db.execSQL(sqllogin);
//
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NV);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }

    Cursor initRecordFisrtDB(){
        try {
            SQLiteDatabase db=getReadableDatabase();
            Cursor cs = db.rawQuery("SELECT * FROM NV", null);
            cs.moveToNext();
            return cs;
        }
        catch (Exception e) {

        }
        return null;
    }
    SQLiteDatabase ketNoiDBRead(){
        SQLiteDatabase db= getReadableDatabase();
        return db;
    }
    SQLiteDatabase ketNoiDBWrita(){
        SQLiteDatabase db= getWritableDatabase();
        return db;
    }
}
