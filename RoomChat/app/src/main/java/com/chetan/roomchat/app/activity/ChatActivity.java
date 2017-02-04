package com.chetan.roomchat.app.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chetan.roomchat.app.R;
import com.chetan.roomchat.app.SocketConnection;
import com.chetan.roomchat.app.adapter.ChatAdapter;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_chat)
    RecyclerView rvChat;
    @Bind(R.id.edit_text)
    EditText editText;
    @Bind(R.id.iv_send)
    ImageView ivSend;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @Bind(R.id.rl_main)
    RelativeLayout rlMain;
    private String userName;
    List<String> mMsg;
    List<String> mTime;
    private ChatAdapter adapter;
    private Socket mSocket;
    private boolean isExited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        userName = getIntent().getStringExtra("name");
        toolbarTitle.setText(userName);
        mMsg = new ArrayList<>();
        mTime = new ArrayList<>();
        adapter = new ChatAdapter(ChatActivity.this, mMsg, mTime);
        LinearLayoutManager lm = new LinearLayoutManager(ChatActivity.this);
        lm.setStackFromEnd(true);
        rvChat.setLayoutManager(lm);
        rvChat.setAdapter(adapter);
        rvChat.scrollToPosition(rvChat.getAdapter().getItemCount() - 1);
        joinEmit();
    }


    @OnClick(R.id.iv_send)
    public void onClick() {
        String message = editText.getText().toString().trim();
        if(!message.equalsIgnoreCase("")){
            editText.setText("");
            emitSocket(message);
            adapter.addNewMsg(message,0,getTime(),"");
            rvChat.scrollToPosition(rvChat.getAdapter().getItemCount() - 1);
        }
    }

    private void emitSocket(String message){
        JSONObject sendMessage = new JSONObject();
        try {
            sendMessage.put("message",message);
            sendMessage.put("type",1);
            sendMessage.put("name",userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("chat message",sendMessage);
    }

    private void joinEmit(){
        mSocket = SocketConnection.getInstance().getSocket();
        mSocket.on("receive message",onNewMessage);
        mSocket.connect();
        Log.d("mytag", "joinEmit: "+mSocket.connected());
        JSONObject joinMessage = new JSONObject();
        try {
            joinMessage.put("message","joined this room");
            joinMessage.put("type",2);
            joinMessage.put("name",userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("chat message",joinMessage);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!isExited){
            exit();
        }
    }

    private void exit(){
        JSONObject leaveMessage = new JSONObject();
        try {
            leaveMessage.put("message","left this room");
            leaveMessage.put("type",2);
            leaveMessage.put("name",userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.off("chat message");
        mSocket.emit("disconnect room",leaveMessage);
        SocketConnection.getInstance().disconnect();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(ChatActivity.this)
                .setMessage("Are you sure you want to exit this room?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isExited = true;
                        exit();
                        ChatActivity.this.finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            ChatActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = null;
                    try {
                        data = (JSONObject) args[0];
                    }catch (ClassCastException e){
                        String data2 = (String) args[0];
                        try {
                            data = new JSONObject(data2);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    Log.d("mytag", "run: "+data);

                    String message = null;
                    try {
                        message = data.getString("message");
                        int type = data.getInt("type");
                        String name = data.getString("name");
                        adapter.addNewMsg(message,type,getTime(),name);
                        rvChat.scrollToPosition(rvChat.getAdapter().getItemCount() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private String getTime(){
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
        String timeText = outputFormat.format(new Date());
        return timeText;
    }
}
