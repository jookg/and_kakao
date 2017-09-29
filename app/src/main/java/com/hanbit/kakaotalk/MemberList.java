package com.hanbit.kakaotalk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        final Context context=MemberList.this;
        ListView listView=(ListView)findViewById(R.id.listView);
        final FriendList friendList=new FriendList(context);
        ArrayList<Member> friends = (ArrayList<Member>)new Service.iList() {
            @Override
            public ArrayList<?> excute() {
                return friendList.excute();
            }
        }.excute();
        listView.setAdapter(new MemberAdapter(context,friends));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        findViewById(R.id.detailbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,MemberDetail.class));
            }
        });

        findViewById(R.id.addbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,MemberAdd.class));
            }
        });

        findViewById(R.id.logoutbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,Login.class));
            }
        });
    }

    private abstract class ListQuery extends Index.QueryFactory{
        Index.SqLiteHelper helper;
        private ListQuery(Context context) {
            super(context);
            helper=new Index.SqLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class FriendList extends ListQuery {
        private FriendList(Context context) {
            super(context);
        }
        public ArrayList<Member> excute(){
            ArrayList<Member> list=new ArrayList<>();
            String sql=String.format("select * from %s ",Cons.MEM_TBL);
            Cursor cursor=super.getDatabase().rawQuery(sql,null);
            Member member=null;
            if(cursor!=null){
                if(cursor.moveToFirst()) {
                    do {
                        member = new Member();
                        member.setSeq(cursor.getString(cursor.getColumnIndex(Cons.MEM_SEQ)));
                        member.setName(cursor.getString(cursor.getColumnIndex(Cons.MEM_NAME)));
                        member.setPass(cursor.getString(cursor.getColumnIndex(Cons.MEM_PASS)));
                        member.setEmail(cursor.getString(cursor.getColumnIndex(Cons.MEM_EMAIL)));
                        member.setPhone(cursor.getString(cursor.getColumnIndex(Cons.MEM_PHONE)));
                        member.setAddr(cursor.getString(cursor.getColumnIndex(Cons.MEM_ADDR)));
                        member.setProfileimage(cursor.getString(cursor.getColumnIndex(Cons.MEM_PIMG)));
                        list.add(member);
                    } while (cursor.moveToNext());
                }
            }
            return list;
        }
    }
    class MemberAdapter extends BaseAdapter{
        ArrayList<Member> list;
        LayoutInflater inflater;
        public  MemberAdapter(Context context, ArrayList<Member>list){
            this.list=list;
            this.inflater=LayoutInflater.from(context);
        }
        private int[] photos={
                R.drawable.cupcake,
                R.drawable.donut,
                R.drawable.eclair,
                R.drawable.froyo,
                R.drawable.gingerbread,
                R.drawable.honeycomb
        };
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder viewHolder;
            if(v==null){
                v=inflater.inflate(R.layout.member_adapter,null);
                viewHolder=new ViewHolder();
                viewHolder.imageView=(ImageView)v.findViewById(R.id.imageView);
                viewHolder.name=(TextView)v.findViewById(R.id.name);
                viewHolder.phone=(TextView)v.findViewById(R.id.phone);
                v.setTag(viewHolder);

            }else{
                viewHolder=(ViewHolder)v.getTag();
            }
            viewHolder.imageView.setImageResource(photos[i]);
            viewHolder.name.setText(list.get(i).getName());
            viewHolder.phone.setText(list.get(i).getPhone());
            return v;
        }
    }
    static class ViewHolder{
        ImageView imageView;
        TextView name;
        TextView phone;
    }

}
