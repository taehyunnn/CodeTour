package com.example.codetour;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class sePosSetting extends AppCompatActivity implements SePosSettingContract.View {
    //사용되는 객체. sePos는 이후 일정에 대한 클래스로 대체 예정
    sePos sepos;
    SePosSettingContract.Presenter presenter;
    Intent seIntent;
    int[] stPosID;
    int[] edPosID;

    TableLayout seEdit;
    TripSchedule tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_pos_setting);
        presenter = new SePosSettingPresenter();
        presenter.setView(this);

        this.InitializeView();
        this.GetDataFromPrevView();
        this.MakeTable();
        this.FillTable();
    }

    public void InitializeView() {
        //테이블 틀 생성
        seEdit = (TableLayout) findViewById(R.id.seEdit);
    }

    public void GetDataFromPrevView(){
        //이전 페이지로부터 데이터들을 받아옴
        seIntent = getIntent();
        tour=(TripSchedule) seIntent.getSerializableExtra("class");

        //sePos가 내부적으로 Exception을 throw하므로, try-catch를 사용
        try {
            //정보들을 따로 getExtra로 안건넨받고 Tour class에 담아서 객체를 위에서 받았다. 따라서 여기부터 tour의 필드값 이용하면 된다.
            sepos = new sePos(tour.difdays, tour.startPoss, tour.endPoss);
        }
        catch(Exception e){
            sepos = new sePos();
        }
        stPosID = new int[sepos.days];
        edPosID = new int[sepos.days];
    }

    public void  MakeTable(){
        int tmpID;
        //InitializeView()에서 생성해 둔 테이블 틀을 채움;
        //날짜 만큼 row를 생성해서 위에 만든 테이블에 추가함
        for(int i=0; i<sepos.days; ++i){
            //row 생성
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tmpRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 4);
            tr.setLayoutParams(tmpRowParams);

            //몇일째인지 날짜를 보여주는 텍스트를 row에 추가
            TextView text = new TextView(this);
            text.setText(new String((i+1)+"일째"));
            text.setGravity(Gravity.CENTER_VERTICAL);
            tr.addView(text, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));

            //아직 위치값을 넣지 않았기 때문에 실행해보기 위해서 주석처리 해놓음

            //i번째날 출발지 입력칸을 row에 추가
            EditText stPos = new EditText(this);
            stPos.setText(sepos.startPos.get(i));
            tmpID = View.generateViewId();
            stPos.setId(tmpID);
            stPosID[i] = tmpID;
            tr.addView(stPos, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3));

            //i번째날 도착지 입력칸을 row에 추가
            EditText edPos = new EditText(this);
            edPos.setText(sepos.endPos.get(i));
            tmpID = View.generateViewId();
            edPos.setId(tmpID);
            edPosID[i] = tmpID;
            tr.addView(edPos, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3));

            //row를 테이블에 추가
            seEdit.addView(tr);
        }
    }

    public void FillTable(){
        EditText tmp;
        for(int i=0; i<this.tour.difdays; ++i){
            tmp = (EditText)findViewById(stPosID[i]);
            tmp.setText(tour.startPoss.get(i));

            tmp = (EditText)findViewById(edPosID[i]);
            tmp.setText(tour.endPoss.get(i));
        }
    }

    public void onClick(View view){
        if(view.getId()==R.id.sePosSettingConfirm){
            EditText tmp;
            for(int i=0; i<sepos.days; ++i){
                tmp = (EditText)findViewById(stPosID[i]);
                sepos.startPos.set(i, tmp.getText().toString());

                tmp = (EditText)findViewById(edPosID[i]);
                sepos.endPos.set(i, tmp.getText().toString());
            }

            Intent intent = new Intent(getApplicationContext(), RouteCheck.class);
            intent.putExtra("sepos", sepos);
            startActivity(intent);
        }
    }
}
