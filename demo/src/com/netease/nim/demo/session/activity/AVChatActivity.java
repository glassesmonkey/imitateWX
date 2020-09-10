package com.netease.nim.demo.session.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.netease.nim.avchatkit.AVChatKit;
import com.netease.nim.demo.R;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class AVChatActivity extends AppCompatActivity {
    private Button joinMeeting;
    private TextView meeting_members;
    private String ext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avchat);
        joinMeeting = findViewById(R.id.join_button);
        meeting_members = findViewById(R.id.meeting_members);
        Intent intent =getIntent();
        String teamId = intent.getStringExtra("sessionId");
        searchExt(teamId);
    }
    //查询群扩展字段来判断是否有人在群
    private void searchExt(String teamId) {
        NIMClient.getService(TeamService.class).searchTeam(teamId).setCallback(new RequestCallback<Team>() {
            @Override
            public void onSuccess(Team team) {
                ext = team.getExtension();
                if(null == ext||ext.isEmpty()||"".equals(ext)){
                    meeting_members.setText("当前没有群视频");
                    joinMeeting.setEnabled(false);
                    joinMeeting.setText("无法加入");
                }else{
                    sortEXT();
                    meeting_members.setText("参会成员： "+ ext);
                    joinMeeting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String[] array = ext.split(",");
                            List<String> arrList = Arrays.asList(array);
                            arrList.remove(arrList.get(0));
                            //加入群聊
                            AVChatKit.outgoingTeamCall(AVChatActivity.this, true, teamId, array[0], (ArrayList<String>) arrList, teamId);
                        }
                    });
                }

            }

            @Override
            public void onFailed(int code) {
                // 失败
            }

            @Override
            public void onException(Throwable exception) {
                // 错误
            }
        });

    }
    private void sortEXT(){
        List<String> arrList = Arrays.asList(ext.split(","));
        List joinList = new ArrayList(arrList);
        joinList.remove(joinList.get(0));
        String[] strArray = (String[]) joinList.toArray(new String[0]);
        arraysToString(strArray);
    }

    private void arraysToString(String[] strArray){
        ext = Arrays.toString(strArray);
        int len = ext.length();
        if(len == 2){
            ext = "";
        }else{
            ext = ext.substring(1, len-1);
        }

    }
}
