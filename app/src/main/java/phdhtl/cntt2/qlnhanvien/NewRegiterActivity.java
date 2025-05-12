package phdhtl.cntt2.qlnhanvien;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class NewRegiterActivity extends AppCompatActivity {

    RadioButton rdnam,rdnu;
    Button btntao;
    EditText edtu,edtpass,edtfullname;
    DBHelperDatabase dbh;
    Spinner spnamsinh;
    String valuenamsinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_regiter);
        rdnam=findViewById(R.id.rdnam);
        rdnu=findViewById(R.id.rdnu);
        btntao=findViewById(R.id.btntao);
        edtu=findViewById(R.id.edtu);
        edtpass=findViewById(R.id.edtpass);
        edtfullname=findViewById(R.id.edtfullname);
        spnamsinh=findViewById(R.id.spnamsinh);
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=1980;i<=2010;i++) {
            arrayList.add(""+i);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,                         android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnamsinh.setAdapter(arrayAdapter);
        dbh=new DBHelperDatabase(this);

        spnamsinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valuenamsinh = parent.getItemAtPosition(position).toString();
                //        Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        btntao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=edtu.getText().toString();
                String pass=edtpass.getText().toString();
                String fullname=edtfullname.getText().toString();
                String gender="Nam";
                Intent in= new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(in);
                if(rdnu.isChecked()){
                    gender="Nũ";
                }
                String sql="insert into login values('"+user+"','"+pass+"','"+fullname+"','"+gender+"','"+valuenamsinh+"')";
                SQLiteDatabase db=dbh.ketNoiDBRead();
                db.execSQL(sql);
                finish();
            }
        });
        rdnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdnu.setChecked(false);
            }
        });
        rdnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdnam.setChecked(false);
            }
        });
    }
}