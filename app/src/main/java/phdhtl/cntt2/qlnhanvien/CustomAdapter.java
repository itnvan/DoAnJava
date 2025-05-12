package phdhtl.cntt2.qlnhanvien;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<NhanVien> {
    private ArrayList<NhanVien> arrnv;
    private final Activity context;
    private int lastPosition = -1;

    public CustomAdapter(Activity context, ArrayList<NhanVien> arrsv) {
        super(context, R.layout.item_nv, arrsv);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.arrnv = arrsv;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_nv, null, true);
        NhanVien nhanVien = arrnv.get(position);

        TextView txtmanv = rowView.findViewById(R.id.txtmanv);
        ImageView imgnv = (ImageView) rowView.findViewById(R.id.imganhnv);
        TextView txttennv = (TextView) rowView.findViewById(R.id.txttennv);
        TextView txtns = (TextView) rowView.findViewById(R.id.txtns);
        TextView txthsl = (TextView) rowView.findViewById(R.id.txthsl);
        txtmanv.setText(arrnv.get(position).getNv_id());
        txttennv.setText(arrnv.get(position).getNv_name());
        txtns.setText("" + arrnv.get(position).getNv_ns());
        txthsl.setText("" + arrnv.get(position).getNv_hsl());
        Button btnchitiet = rowView.findViewById(R.id.btnchitiet);
        Log.d("chuoihinhanh",""+StringToBitMap(nhanVien.getAnhnv()));
        imgnv.setImageBitmap(StringToBitMap(nhanVien.getAnhnv()));
        btnchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, DeTailActivity.class);
                in.putExtra("ObjectNV", nhanVien);
                context.startActivityForResult(in, 200);
//                context.startActivity(in);
            }
        });
        Button btnxoa = rowView.findViewById(R.id.btnxoa);
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrnv.remove(position);
                notifyDataSetChanged();
            }
        });
        // imganhsv.setImageResource(sv.getAnhsv());
        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        rowView.startAnimation(animation);
        lastPosition = position;

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abcd", "" + lastPosition);
                EditText nv_id = rowView.getRootView().findViewById(R.id.nv_id);
                EditText nv_name = rowView.getRootView().findViewById(R.id.nv_name);
                EditText nv_ns = rowView.getRootView().findViewById(R.id.txtns);
                EditText nv_hsl = rowView.getRootView().findViewById(R.id.nv_hsl);
                EditText nv_cv = rowView.getRootView().findViewById(R.id.nv_cv);
                nv_id.setText(arrnv.get(position).getNv_id());
                nv_name.setText(arrnv.get(position).getNv_name());
                nv_ns.setText("" + arrnv.get(position).getNv_ns());
                nv_hsl.setText("" + arrnv.get(position).getNv_hsl());
                nv_cv.setText(arrnv.get(position).getNv_cv());
            }
        });
//        imganhsv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("POSIOON","ok"+arrsv.get(position).getTensv());
//            }
//        });
        return rowView;

    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    };
}