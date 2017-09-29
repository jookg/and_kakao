package com.hanbit.kakaotalk;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Context context=Login.this;
        final EditText inputid=(EditText) findViewById(R.id.id);
        final EditText inputpass=(EditText) findViewById(R.id.pass);
        final MemberLogin login= new MemberLogin(context);
        findViewById(R.id.sbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id=inputid.getText().toString().equals("")? "2":inputid.getText().toString();
                final String pass=inputpass.getText().toString().equals("")? "2":inputpass.getText().toString();
                Toast.makeText(context,id,Toast.LENGTH_LONG).show();
                new Service.iPredicate() {
                    @Override
                    public void execute() {
                        if(login.excute(id,pass)){
                            startActivity(new Intent(context,MemberList.class));
                        }else{
                            Toast.makeText(context,"로그인 실패",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(context,Login.class));
                        }
                    }
                }.execute();

            }
        });

        findViewById(R.id.rbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputid.setText(null);
                inputpass.setText(null);
            }
        });
    }
    private abstract class LoginQuery extends Index.QueryFactory{
        Index.SqLiteHelper helper;
        private LoginQuery(Context context) {
            super(context);
            helper=new Index.SqLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class MemberLogin extends LoginQuery {
        private MemberLogin(Context context) {
            super(context);
        }
        public boolean excute(String id, String pass){
            return super.getDatabase().rawQuery(String.format("select * from %s where %s=%s and %s=%s",Cons.MEM_TBL,Cons.MEM_SEQ,id,Cons.MEM_PASS,pass),null).moveToNext();
        }
    }
}
