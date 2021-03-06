package com.example.appdoctruyen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.TaiKhoanFragment;
import com.example.appdoctruyen.TrangChu;
import com.example.appdoctruyen.database.Database;
import com.example.appdoctruyen.model.TaiKhoan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoiMatKhau extends AppCompatActivity implements View.OnClickListener {

    Database db;
    TaiKhoan taiKhoan;
    String email;
    EditText edt_dmk_mkht,edt_dmk_mkm,edt_dmk_nlmk;
    Button bt_xndmk,bt_huy;
    TrangChu trangChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doimatkhau);

        db=new Database(this);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        taiKhoan=db.getTaiKhoan(email);

        Anhxa();
        setOnClickListener();
    }

    private void Anhxa() {
        edt_dmk_mkht=findViewById(R.id.edt_dmk_mkht);
        edt_dmk_mkm=findViewById(R.id.edt_dmk_mkm);
        edt_dmk_nlmk=findViewById(R.id.edt_dmk_nlmk);
        bt_huy=findViewById(R.id.bt_huy);
        bt_xndmk=findViewById(R.id.bt_xndmk);
    }

    private void setOnClickListener(){
        bt_xndmk.setOnClickListener(this);
        bt_huy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_huy:
                Intent intent = new Intent(this, TrangChu.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_xndmk:
                String mkht=edt_dmk_mkht.getText().toString();
                String mkm=edt_dmk_mkm.getText().toString();
                String nlmk=edt_dmk_nlmk.getText().toString();

                if(mkht.length()!=0 && mkm.length()!=0 && nlmk.length()!=0){
                    Boolean kt=db.checkEmailMatkhau(email,edt_dmk_mkht.getText().toString());
                    if(kt==true){
                        if(vailidatePass(mkm)==false){
                            Toast.makeText(this,"M???t kh???u kh??ng h???p l??? (??t nh???t 8 k?? t??? ph???i bao g???m ch??? in hoa, ch??? s??? v?? k?? t??? ?????c bi???t)",Toast.LENGTH_SHORT).show();
                        }
                        else if(!nlmk.equals(mkm)){
                            Toast.makeText(this,"M???t kh???u kh??ng tr??ng nhau",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            db.updateMK(taiKhoan.getEmail(),edt_dmk_mkm.getText().toString());
                            Toast.makeText(this,"?????i m???t kh???u th??nh c??ng",Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(this, TrangChu.class);
                            intent1.putExtra("email",email);
                            startActivity(intent1);
                            finish();
                        }
                    }else {
                        Toast.makeText(this,"M???t kh???u hi???n t???i kh??ng ch??nh x??c",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if(mkht.length()==0){
                        Toast.makeText(this,"Vui l??ng nh???p m???t kh???u hi???n t???i",Toast.LENGTH_SHORT).show();
                    }
                    else if(mkm.length()==0){
                        Toast.makeText(this,"Vui l??ng nh???p m???t kh???u m???i",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this,"Vui l??ng nh???p nh???p l???i m???t kh???u",Toast.LENGTH_SHORT).show();
                    }
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
}
