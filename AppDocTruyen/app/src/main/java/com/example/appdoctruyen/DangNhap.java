package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.appdoctruyen.database.Database;
import com.google.android.material.navigation.NavigationView;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class DangNhap extends AppCompatActivity implements View.OnClickListener {

    TextView textView, textView2;
    ImageView img_logodn;
    EditText edt_dn_email,edt_dn_pass;
    Button bt_dn;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        db=new Database(this);

        Anhxa();
        setOnClickListener();

        Intent i =getIntent();

        String email=i.getStringExtra("email");
        String pass=i.getStringExtra("pass");

        edt_dn_email.setText(email);
        edt_dn_pass.setText(pass);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_logodn: {
                Intent dialog_box = new Intent(DangNhap.this, TrangChu.class);
                startActivity(dialog_box);
                finish();
                break;
            }
            case R.id.tv_chuatk:
                Intent dialog_box1 = new Intent(DangNhap.this, DangKy.class);
                startActivity(dialog_box1);
                break;
            case R.id.tv_quenmk:
                Intent dialog_box2 = new Intent(DangNhap.this, QuenMK.class);
                startActivity(dialog_box2);
                break;
            case R.id.bt_dn: {
                String email=edt_dn_email.getText().toString();
                email=removeAccent(email);
                String pass=edt_dn_pass.getText().toString();

                if (email.length() != 0 && pass.length() != 0) {
                    Boolean checkemailpass= db.checkEmailMatkhau(email,pass);
                    if(checkemailpass==true) {
                        int kt=db.checkTrangThai(email);
                        if(kt!=0){
                            Toast.makeText(this,"????ng nh???p th??nh c??ng!",Toast.LENGTH_SHORT).show();
                            Intent dialog_box = new Intent(DangNhap.this, TrangChu.class);
                            dialog_box.putExtra("email", email);
                            startActivity(dialog_box);
                            finish();
                        }
                        else {
                            Toast.makeText(this,"T??i kho???n b??? kh??a",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(this,"Email ho???c m???t kh???u kh??ng ch??nh x??c!",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (email.length() == 0) {
                        Toast.makeText(this, "Vui l??ng nh???p Email", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Vui l??ng nh???p M???t kh???u", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }

    private void setOnClickListener(){
        img_logodn.setOnClickListener(this);
        textView.setOnClickListener(this);
        textView2.setOnClickListener(this);
        bt_dn.setOnClickListener(this);
    }

    private void Anhxa(){
        img_logodn=(ImageView) findViewById(R.id.img_logodn);
        textView = (TextView) findViewById(R.id.tv_chuatk);
        textView2= (TextView) findViewById(R.id.tv_quenmk);
        edt_dn_email= findViewById(R.id.edt_dn_email);
        edt_dn_pass=findViewById(R.id.edt_dn_pass);
        bt_dn=findViewById(R.id.bt_dn);
    }

    public static String removeAccent(String s){
        s=s.toLowerCase();
        s=s.replaceAll("??", "d");
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}