package com.hanbit.kakaotalk;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        final Context context=Index.this;
        Handler handler=new Handler();
        SqLiteHelper helper=new SqLiteHelper(context);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context,Main.class));
                finish();
            }
        },2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    public static class SqLiteHelper extends SQLiteOpenHelper{
        public SqLiteHelper(Context context) {
            super(context,"hanbit.db",null,1);
            this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql=String.format("CREATE TABLE IF NOT EXISTS %s(" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT, %s TEXT, %s TEXT, %s TEXT, " +
                    "%s TEXT, %s TEXT " +
                    ");",Cons.MEM_TBL,Cons.MEM_SEQ,Cons.MEM_PASS,Cons.MEM_NAME,Cons.MEM_EMAIL,Cons.MEM_PHONE,Cons.MEM_PIMG,Cons.MEM_ADDR);
            db.execSQL(sql);
           /*  for (int i=0; i<6; i++){
                db.execSQL(String.format("INSERT INTO %s(%s,%s,%s,%s,%s,%s) " +
                        "VALUES('%s','%s','%s','%s','%s','%s');"
                        ,Cons.MEM_TBL,Cons.MEM_PASS,Cons.MEM_NAME,Cons.MEM_EMAIL,Cons.MEM_PHONE,Cons.MEM_PIMG,Cons.MEM_ADDR
                        ,"1","홍"+i,"hong"+i+"@test","010-1234-123"+i,"서울"+i,"default_img"));
            }*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    public static abstract class QueryFactory{
        Context context;

        public QueryFactory(Context context) {
            this.context = context;
        }
        public abstract SQLiteDatabase getDatabase();
    }
}
