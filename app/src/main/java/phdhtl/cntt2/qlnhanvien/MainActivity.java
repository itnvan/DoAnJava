package phdhtl.cntt2.qlnhanvien;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    int flag;
    EditText nv_ma, nv_ten, nam_sinh, hsl, chuc_vu;
    Button bPrev, bNext, bFirst, bLast;
    SQLiteDatabase db;
    Cursor cs;
    ListView lvnv;
    ArrayList<NhanVien> arrnv;
    CustomAdapter adapter;
    Button btnthem,btnxoa,btnsua,btnluu,btnlamlai,btncsv;
    EditText txts;
    DBHelperDatabase dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar2);
        toolbar.setTitle("DANH SÁCH NHÂN VIÊN");
        toolbar.setBackgroundColor(Color.parseColor("#FFFF00"));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        dbh=new DBHelperDatabase(this);
        lvnv=findViewById(R.id.lvnhanvien);
        initView();
        cs=dbh.initRecordFisrtDB();
        updateRecord();
        db=dbh.ketNoiDBRead();
        showDataListView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout){
            SharedPreferences settings= getSharedPreferences("login",MODE_PRIVATE);
            settings.edit().clear().commit();
            Intent in= new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }
    private void showDataListView(){
        db=dbh.ketNoiDBRead();
        String s=txts.getText().toString();
        arrnv =new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM NV where nv_id like '%"+s+"%' or nv_name like '%"+s+"%'", null);
        try {
            while (cursor.moveToNext()) {
                NhanVien b=new NhanVien(cursor.getString(0),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        Float.parseFloat(cursor.getString(3)),
                        cursor.getString(4),
                        cursor.getString(5));
                Log.d("NhanVien",b.toString());
                arrnv.add(b);
                adapter= new CustomAdapter(this, arrnv);
                lvnv.setAdapter(adapter);
            }
        } finally {
            cursor.close();
        }
    }

    void updateRecord() {
        nv_ma.setText(cs.getString(0));
        nv_ten.setText(cs.getString(1));
        nam_sinh.setText(cs.getString(2));
        hsl.setText(cs.getString(3));
        chuc_vu.setText(cs.getString(4));

        bPrev.setEnabled(!cs.isFirst());
        bNext.setEnabled(!cs.isLast());
        bFirst.setEnabled(!cs.isLast());
        bLast.setEnabled(!cs.isLast());
    }
    void initView(){
        nv_ma = (EditText) findViewById(R.id.nv_id);
        nv_ten = (EditText) findViewById(R.id.nv_name);
        nam_sinh = (EditText) findViewById(R.id.txtns);
        hsl = (EditText) findViewById(R.id.nv_hsl);
        chuc_vu = (EditText) findViewById(R.id.nv_cv);

        bPrev = (Button) findViewById(R.id.btnprev);
        bNext = (Button) findViewById(R.id.btnnext);
        bFirst = (Button) findViewById(R.id.btnfirst);
        bLast = (Button) findViewById(R.id.btnlast);
        btnthem=findViewById(R.id.btnthem);
        btnxoa=findViewById(R.id.btnxoa);
        btnsua=findViewById(R.id.btnsua);
        btnluu=findViewById(R.id.btnluu);
        btnlamlai=findViewById(R.id.btnlamlai);
        txts=findViewById(R.id.txts);
        txts.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                showDataListView();
                return false;
            }
        });
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                setview();
                nv_ma.requestFocus();
                btnthem.setEnabled(false);
                btnsua.setEnabled(false);
            }
        });
        btnlamlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setview();
                flag=0;
                //nv_id.requestFocus();
                btnthem.setEnabled(true);
                btnsua.setEnabled(true);
            }
        });
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=2;
                btnthem.setEnabled(false);
                btnsua.setEnabled(false);
            }
        });
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    Log.d("Thao tac","them");
                    String nv_id=nv_ma.getText().toString();
                    String nv_name=nv_ten.getText().toString();
                    String nv_namsinh=nam_sinh.getText().toString();
                    String nv_hsl=hsl.getText().toString();
                    String nv_chucvu=chuc_vu.getText().toString();
                    String nvimage="";
                    String sql="INSERT INTO NV" +
                            " VALUES('"+nv_id+"','"+nv_name+"','"+nv_namsinh+"','"+nv_hsl+"','"+nv_chucvu+"','"+nvimage+"')";
                    try{
                        SQLiteDatabase db1= dbh.ketNoiDBWrita();
//                      SQLiteDatabase db1=openOrCreateDatabase("nvs.db",MODE_PRIVATE,null);
                        db1.execSQL(sql);
                        db1.close();
                        showDataListView();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Khong thanh cong",Toast.LENGTH_LONG).show();
                    }
                } else if (flag==2) {
                    Log.d("Thao tac","sua");
                    String nv_id=nv_ma.getText().toString();
                    String nv_name=nv_ten.getText().toString();
                    String nv_namsinh=nam_sinh.getText().toString();
                    String nv_hsl=hsl.getText().toString();
                    String nv_chucvu=chuc_vu.getText().toString();
                    String sql="update NV set nv_name='"+nv_name+"',nv_namsinh='"+nv_namsinh+"',nv_hsl='"+nv_hsl+"',nv_chucvu='"+nv_chucvu+"' where nv_id='"+nv_id+"'";
                    try{
                        SQLiteDatabase db1= dbh.ketNoiDBWrita();
                        db1.execSQL(sql);
                        db1.close();
                        showDataListView();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Khong thanh cong",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"chua chon thao tac them hoac sua",Toast.LENGTH_LONG);
                }
            }
        });
            btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nvid=nv_ma.getText().toString();
                String namenv=nv_ten.getText().toString();
                showDialogconfirm(namenv,nvid);
            }
        });
        btncsv = findViewById(R.id.btncsv);
        btncsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performExportToCSV();

            }
        });
    }
    private void setview(){
        nv_ma.setText("");
        nv_ten.setText("");
        nam_sinh.setText("");
        hsl.setText("");
        chuc_vu.setText("");
    }
    void createnvDatabase() {
        String sqltext = "DROP TABLE IF EXISTS NhanVien;\n"
                + "CREATE TABLE NVien(nv_id integer PRIMARY KEY, nv_name text, nv_namsinh integer, nv_hsl Float, nv_chucvu text, nvimage text);\n"
                + "INSERT INTO NV VALUES(1, 'Van An', 2002, 99.99, 'Giám Đốc','');\n"
                + "INSERT INTO NV VALUES(2, 'Andiez', 2004, 3.99, 'Nhân Viên','');\n"
                + "INSERT INTO NV VALUES(3, 'Quan', 2003, 1.99, 'Nhân Viên','');\n"
                + "INSERT INTO NV VALUES(4, 'Đuc Thien', 2002, 0.99, 'Nhân Viên','');\n"
                + "INSERT INTO NV VALUES(5, 'Khanh Duy', 2003, 0.199, 'Nhân Viên','');\n";
        // tạo DB và thực hiện một số câu SQL
        SQLiteDatabase db = openOrCreateDatabase("NhanVien.db", MODE_PRIVATE, null);
        for (String sql : sqltext.split("\n"))
            db.execSQL(sql);
        db.close();
    }
    private void showDialogconfirm(String s, String nv_ma){
        //Tạo đối tượng
        AlertDialog.Builder b = new AlertDialog.Builder(this);
//Thiết lập tiêu đề
        b.setTitle("Xác nhận");
        b.setMessage("Bạn có đồng ý xóa mục  "+s+" này không ?");
// Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                String sql = "delete from NV where nv_id='"+nv_ma+"'";
                try{
                    SQLiteDatabase db1=dbh.ketNoiDBRead();
                    db1.execSQL(sql);
                    db1.close();
                    showDataListView();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Khong thanh cong",Toast.LENGTH_LONG).show();
                }
            }
        });
//Nút Cancel
        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
//Tạo dialog
        AlertDialog al = b.create();
//Hiển thị
        al.show();
    }
    private static final int REQUEST_WRITE_STORAGE = 112;
    // Phương thức xuất dữ liệu ra file CSV
    private void performExportToCSV() {
        File exportDir = new File(getExternalFilesDir(null), "CSVFiles");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "NhanVien.csv");
        FileWriter fileWriter = null;
        Cursor cursor = null;

        try {
            file.createNewFile();
            fileWriter = new FileWriter(file);
            SQLiteDatabase db = dbh.ketNoiDBRead();
            cursor = db.rawQuery("SELECT * FROM NhanVien", null);

            // Write header
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                fileWriter.append(columnName).append(",");
            }
            fileWriter.append("\n");

            // Write rows
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    fileWriter.append(cursor.getString(i)).append(",");
                }
                fileWriter.append("\n");
            }

            Toast.makeText(this, "Xuất CSV thành công: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Xuất CSV thất bại", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            // Đóng cursor và FileWriter
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (IOException e) {
                Log.e("MainActivity", "Error closing writer: " + e.getMessage());
            }
        }
    }
    // Handle the permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                performExportToCSV();
            } else {
                Toast.makeText(this, "Từ chối quyền. Không thể xuất CSV.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==200)
        {
            showDataListView();
        }
    }
    //Sự kiện click vào nut Next phải khai báo ở activity_main.xml
    public void btnNext(View v) {
        cs.moveToNext();
        updateRecord();
    }
    public void btnPrev(View v) {
        cs.moveToPrevious();
        updateRecord();

    }
    public void btnLast(View v) {
        cs.moveToLast();
        updateRecord();

    }
    public void btnFirst(View v) {
        cs.moveToFirst();
        updateRecord();

    }

}