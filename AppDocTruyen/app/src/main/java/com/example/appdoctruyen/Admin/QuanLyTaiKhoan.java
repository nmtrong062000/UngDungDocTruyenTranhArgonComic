package com.example.appdoctruyen.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.DangNhap;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.AdapterAdmin.QLTaiKhoanAdapter;
import com.example.appdoctruyen.database.Database;
import com.example.appdoctruyen.model.TaiKhoan;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuanLyTaiKhoan extends AppCompatActivity implements View.OnClickListener {
    TaiKhoan taiKhoan;
    Database db;
    String email;
    private RecyclerView rcv;
    private QLTaiKhoanAdapter adapter;
    ImageView img_newtk;
    Button bt_them,bt_huy;
    EditText edt_email,edt_matkhau,edt_ten,edt_dienthoai;
    CardView cv_themtaikhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlytaikhoan);

        Anhxa();
        db=new Database(this);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        taiKhoan=db.getTaiKhoan(email);

        setOnClickListener();
        recyclerViewQLTaiKhoan();
    }

    private void recyclerViewQLTaiKhoan(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        ArrayList<TaiKhoan> list=db.getListTaiKhoan();
        adapter=new QLTaiKhoanAdapter(this,list,db);
        rcv.setAdapter(adapter);
    }

    private void Anhxa() {
        rcv=findViewById(R.id.rcv_quanlytaikhoan);
        img_newtk=findViewById(R.id.img_newtaikhoan);
        bt_huy=findViewById(R.id.bt_huy_newtk);
        bt_them=findViewById(R.id.bt_them_newtk);
        edt_email=findViewById(R.id.edt_email_newtk);
        edt_matkhau=findViewById(R.id.edt_mk_newtk);
        edt_ten=findViewById(R.id.edt_ten_newtk);
        edt_dienthoai=findViewById(R.id.edt_dienthoai_newtk);
        cv_themtaikhoan=findViewById(R.id.cv_themtk);
    }

    private void setOnClickListener(){
        bt_them.setOnClickListener(this);
        bt_huy.setOnClickListener(this);
        img_newtk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_newtaikhoan:
                cv_themtaikhoan.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_huy_newtk:
                cv_themtaikhoan.setVisibility(View.GONE);
                break;
            case R.id.bt_them_newtk:
                String email=edt_email.getText().toString().trim();
                email=removeAccent(email);
                String matkhau=edt_matkhau.getText().toString();
                String ten=edt_ten.getText().toString().trim();
                String dienthoai=edt_dienthoai.getText().toString();

                if(!email.isEmpty() && !matkhau.isEmpty()){
                    if(!validateEmail(email)){
                        Toast.makeText(this,"Email kh??ng h???p l???",Toast.LENGTH_SHORT).show();
                    }else if(db.ckeckEmail(email)==true || email.equals("argoncomic@gmail.com")){
                        Toast.makeText(this,"Email ???? t???n t???i",Toast.LENGTH_SHORT).show();
                    }else if(!vailidatePass(matkhau)){
                        Toast.makeText(this,"M???t kh???u kh??ng h???p l??? (??t nh???t 8 k?? t??? ph???i bao g???m ch??? in hoa, ch??? s??? v?? k?? t??? ?????c bi???t)",Toast.LENGTH_SHORT).show();
                    }else {
                        db.insertNewTaiKhoan(email,matkhau,ten,dienthoai);
                        Toast.makeText(this,"Th??m t??i kho???n th??nh c??ng",Toast.LENGTH_SHORT).show();
                        recyclerViewQLTaiKhoan();
                        cv_themtaikhoan.setVisibility(View.GONE);
                    }
                }else if(email.isEmpty()){
                    Toast.makeText(this,"Vui l??ng nh???p email",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"Vui l??ng nh???p m???t kh???u",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public static boolean vailidatePass(String pass) {
        String expression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }

    private boolean validateEmail(String email){
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else {
            return false;
        }
    }

    public static String removeAccent(String s){
        s=s.toLowerCase();
        s=s.replaceAll("??", "d");
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
