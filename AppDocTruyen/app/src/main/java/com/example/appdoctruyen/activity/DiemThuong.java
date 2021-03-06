package com.example.appdoctruyen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.DangNhap;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.TheLoai;
import com.example.appdoctruyen.TimKiem;
import com.example.appdoctruyen.XepHang;
import com.example.appdoctruyen.adapter.DocChapterAdapter;
import com.example.appdoctruyen.database.Database;
import com.example.appdoctruyen.model.Chapter;
import com.example.appdoctruyen.model.NoiDungChapter;
import com.example.appdoctruyen.model.TaiKhoan;

import java.util.ArrayList;

public class DiemThuong extends AppCompatActivity implements View.OnClickListener{

    LinearLayout ll_cuahang,ll_lichsu;
    Button bt_diemdanh;
    Database db;
    String email;
    TaiKhoan taiKhoan;
    TextView tv_diemtichluy,tv_songaydd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diemthuong);
        db=new Database(this);
        Anhxa();

        Intent i = getIntent();
        email=i.getStringExtra("email");

        taiKhoan = db.getTaiKhoan(email);

        setData();
        setOnClickListener();
    };

    private void Anhxa(){
        ll_cuahang=findViewById(R.id.ll_cuahang);
        ll_lichsu=findViewById(R.id.ll_lichsu);
        bt_diemdanh=findViewById(R.id.bt_diemdanh);
        tv_diemtichluy=findViewById(R.id.tv_diemtichluy);
        tv_songaydd=findViewById(R.id.tv_songaydd);
    }

    private void setOnClickListener(){
        ll_cuahang.setOnClickListener(this);
        bt_diemdanh.setOnClickListener(this);
        ll_lichsu.setOnClickListener(this);
    }

    private void reload(){
        Intent intent = getIntent();
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0,0);
        startActivity(intent);
    }

    private void setData(){
        if(db.getThuHienTai(taiKhoan)!=0){
            tv_songaydd.setText( db.getThuHienTai(taiKhoan)+" ng??y li??n ti???p");
        }else{
            tv_songaydd.setText( db.getThu(taiKhoan)+" ng??y li??n ti???p");
        }
        tv_diemtichluy.setText(""+taiKhoan.getDiemthuong());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_cuahang:
                Intent intent = new Intent(this, CuaHang.class);
                intent.putExtra("email",email);
                startActivity(intent);
                break;
            case R.id.bt_diemdanh:
            {
                    Boolean checkDiemDanh = db.checkDiemDanh(taiKhoan);
                    if (checkDiemDanh == false) {
                        int thu = db.getThu(taiKhoan);
                        if (thu == 2) {
                            Boolean diemdanh = db.updateDiemThuong(taiKhoan, 10);
                            Boolean capnhat = db.insertDiemThuong(taiKhoan.getId(), 10, thu + 1);
                            if (diemdanh == true && capnhat == true) {
                                Toast.makeText(this, "??i???m danh th??nh c??ng! +10 ??i???m", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "X???y ra l???i, Vui l??ng th??? l???i sau!", Toast.LENGTH_SHORT).show();
                            }
                        } else if (thu == 6) {
                            Boolean diemdanh = db.updateDiemThuong(taiKhoan, 15);
                            Boolean capnhat = db.insertDiemThuong(taiKhoan.getId(), 15, thu + 1);
                            if (diemdanh == true && capnhat == true) {
                                Toast.makeText(this, "??i???m danh th??nh c??ng! +15 ??i???m", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "X???y ra l???i, Vui l??ng th??? l???i sau!", Toast.LENGTH_SHORT).show();
                            }
                        } else if (thu == 7) {
                            Boolean diemdanh = db.updateDiemThuong(taiKhoan, 5);
                            Boolean capnhat = db.insertDiemThuong(taiKhoan.getId(), 5, 1);
                            if (diemdanh == true && capnhat == true) {
                                Toast.makeText(this, "??i???m danh th??nh c??ng! +5 ??i???m", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "X???y ra l???i, Vui l??ng th??? l???i sau!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Boolean diemdanh = db.updateDiemThuong(taiKhoan, 5);
                            Boolean capnhat = db.insertDiemThuong(taiKhoan.getId(), 5, thu + 1);
                            if (diemdanh == true && capnhat == true) {
                                Toast.makeText(this, "??i???m danh th??nh c??ng! +5 ??i???m", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "X???y ra l???i, Vui l??ng th??? l???i sau!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        reload();
                    } else {
                        Toast.makeText(this, "H??m nay b???n ???? ??i???m danh, ch??? ?????n ng??y mai nh??!", Toast.LENGTH_SHORT).show();
                    }
                break;
            }
            case R.id.ll_lichsu:
                Intent intent1 = new Intent(this, LichSuNhanDiem.class);
                intent1.putExtra("email",email);
                startActivity(intent1);
                break;
        }
    }
}
