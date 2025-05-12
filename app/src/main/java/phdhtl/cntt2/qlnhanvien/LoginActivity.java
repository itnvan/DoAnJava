package phdhtl.cntt2.qlnhanvien;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    CheckBox ckrember;
    EditText edtuser,edtpass;
    Button btnsign;
    DBHelperDatabase dbh;
    SharedPreferences settings;
    TextView txtdk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ckrember=findViewById(R.id.ckrember);
        edtuser=findViewById(R.id.edtuser);
        edtpass=findViewById(R.id.edtpass);
        btnsign=findViewById(R.id.btnsign);
        txtdk =findViewById(R.id.txtdk);
        txtdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(getApplicationContext(), NewRegiterActivity.class);
                startActivity(in);
            }
        });
        dbh=new DBHelperDatabase(this);
        settings = getSharedPreferences("login",0);
        String username= settings.getString("user","");
        String password= settings.getString("pass","");
        if(username.isEmpty()){

        }else {
            Intent in=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(in);
        }
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u = edtuser.getText().toString();
                String p = edtpass.getText().toString();
                String sql = "select * from login where username='" + u + "' and password='" + p + "'";
                SQLiteDatabase db = dbh.ketNoiDBRead();
                Cursor cs = db.rawQuery(sql, null);
                if (cs.moveToNext()) {
                    if (ckrember.isChecked()) {
                        settings.edit().putString("user", u).apply();
                        settings.edit().putString("pass", p).apply();
                    }
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(), "Tài khoản chưa đúng", Toast.LENGTH_LONG).show();
                }
            }
        });
        ckrember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckrember.isChecked()){
                    Toast.makeText(getApplicationContext(),"Chon", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Khong chon", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}