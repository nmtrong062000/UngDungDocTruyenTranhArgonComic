package com.example.appdoctruyen;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appdoctruyen.database.Database;
import com.google.android.material.navigation.NavigationView;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DangKy extends AppCompatActivity implements View.OnClickListener{

    TextView textView;
    EditText edt_dk_email, edt_dk_pass,edt_dk_nlpass;
    Button bt_dk;
    ImageView imgv_logo;
    RadioButton rb_check;
    Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky);

        Anhxa();
        setOnClickListener();

        db=new Database(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.tv_cotk:
                Intent dialog_box = new Intent(this, DangNhap.class);
                startActivity(dialog_box);
                finish();
                break;
            case R.id.imgv_logo:
                Intent dialog_box1 = new Intent(this, TrangChu.class);
                startActivity(dialog_box1);
                finish();
                break;
            case R.id.bt_dk:
            {
                String email=edt_dk_email.getText().toString();
                email=removeAccent(email);
                String pass=edt_dk_pass.getText().toString();
                String nlpass=edt_dk_nlpass.getText().toString();

                if(email.length()!=0 && pass.length()!=0 && nlpass.length()!=0){
                    if(validateEmail(email)==false){
                        Toast.makeText(this,"Email kh??ng h???p l???!",Toast.LENGTH_SHORT).show();
                    }
                    else if(vailidatePass(pass)==false){
                        Toast.makeText(this,"M???t kh???u kh??ng h???p l??? (??t nh???t 8 k?? t??? ph???i bao g???m ch??? in hoa, ch??? s??? v?? k?? t??? ?????c bi???t)",Toast.LENGTH_SHORT).show();
                    }
                    else if(!nlpass.equals(pass)){
                        Toast.makeText(this,"M???t kh???u kh??ng tr??ng nhau",Toast.LENGTH_SHORT).show();
                    }
                    else if(!rb_check.isChecked()){
                        Toast.makeText(this,"Vui l??ng ?????ng ?? v???i c??c ??i???u kho???n!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Boolean checkEmail=db.ckeckEmail(email);
                        if(checkEmail==false && !email.equals("argoncomic@gmail.com")){
                                Boolean insert = db.insertTaikhoan(email,pass);
                                if(insert==true){
                                    Toast.makeText(this,"????ng k?? th??nh c??ng!",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(this,DangNhap.class);
                                    intent.putExtra("email",email);
                                    intent.putExtra("pass",edt_dk_pass.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(this,"????ng k?? th???t b???i!",Toast.LENGTH_SHORT).show();
                                }
                        }else {
                            Toast.makeText(this, "Email ???? t???n t???i !", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else{
                    if(edt_dk_email.getText().length()==0){
                        Toast.makeText(this,"Vui l??ng nh???p email",Toast.LENGTH_SHORT).show();
                    }
                    else if(edt_dk_pass.getText().length()==0){
                        Toast.makeText(this,"Vui l??ng nh???p m???t kh???u",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this,"Vui l??ng nh???p nh???p l???i m???t kh???u",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }

    private void setOnClickListener(){
        textView.setOnClickListener(this);
        imgv_logo.setOnClickListener(this);
        bt_dk.setOnClickListener(this);
    }

    private void Anhxa(){
        textView = findViewById(R.id.tv_cotk);
        edt_dk_email=findViewById(R.id.edt_dk_email);
        edt_dk_pass=findViewById(R.id.edt_dk_pass);
        edt_dk_nlpass=findViewById(R.id.edt_dk_nlpass);
        bt_dk=findViewById(R.id.bt_dk);
        imgv_logo=findViewById(R.id.imgv_logo);
        rb_check=findViewById(R.id.rb_check);

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
