package tng.convertnumbertest;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	private int m_mode = 0;
	private int m_curretPosition = 0;
	ArrayAdapter<String> adapter;
	ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //listセットアップ
        data = new ArrayList<String>();
        data.add("0");
        data.add("0");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        //listクリック時処理
        list.setOnItemClickListener(
    		new AdapterView.OnItemClickListener() {
    			public void onItemClick(AdapterView<?> av, View view, int position, long id) {
    				m_curretPosition = position;
    			}
			}
		);
        //m_selectedView = (TextView)list.getItemAtPosition(0);
        
        //モード切り替えラジオボタンセットアップ
        RadioGroup cnvRadioGroup = (RadioGroup)this.findViewById(R.id.cnvRadioGroup);
        cnvRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				MainActivity.this.convertbutton_onClick(group, checkedId);
			}
		});
        //初期値は10進数
        cnvRadioGroup.check(R.id.decRadio);
        m_mode = R.id.decRadio;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } 
        return super.onOptionsItemSelected(item);
    }

    //数字ボタンが押された時
    public void numbutton_onClick(View view) {
    	ListView list = (ListView)findViewById(R.id.list);
    	TextView itemView = (TextView)list.getAdapter().getView(m_curretPosition, null, list);
    	itemView.append("AA");
    	String str0 = (String)list.getAdapter().getItem(m_curretPosition);

    	data.set(0, "BB");
    	//adapter.notifyDataSetChanged();
    	adapter.add("AA");
    	String str = adapter.getItem(0);
    	
        //Object obj = list.getItemAtPosition(m_curretPosition);//m_selectedView;//(TextView)findViewById(R.id.txt0);
        //TextView txt0 = (TextView)obj;
        // TextView txt0 = (TextView)list.getItemAtPosition(m_curretPosition);//m_selectedView;//(TextView)findViewById(R.id.txt0);
        TextView txtBin = (TextView)findViewById(R.id.txtBin);
        
        RadioGroup cnvRadioGroup = (RadioGroup)this.findViewById(R.id.cnvRadioGroup);
        int selectedId = cnvRadioGroup.getCheckedRadioButtonId();

        // 16進のときのみ有効なボタン
        if (selectedId == R.id.hexRadio) {
        	switch(view.getId()){
            case R.id.buttonFF:
                //txt0.append("FF");
            	str0 = str0.concat("FF");
                break;
    		default:
    			break;
        	}
        }

        // 16 or 10進
        if (selectedId == R.id.hexRadio ||
            selectedId == R.id.decRadio) 
        {
        	/*
        	switch(view.getId()){
            case R.id.button0:
                txt0.append("0");
                break;
            case R.id.button00:
                txt0.append("00");
                break;
            case R.id.button1:
                txt0.append("1");
                break;
    		case R.id.button2:
    	        txt0.append("2");
    			break;
    		case R.id.button3:
    	        txt0.append("3");
    	        break;
    		default:
    			break;
        	}
        	*/
    	}

    	String curNumStr = str0;//txt0.getText().toString();
        int curVal = 0;
        if (selectedId == R.id.hexRadio) {
            curVal = Integer.parseInt(curNumStr, 16);
        }
        else if (selectedId == R.id.decRadio) {
            curVal = Integer.parseInt(curNumStr);
        }
        
    	String curBinStr = Integer.toBinaryString(curVal);
    	txtBin.setText(curBinStr);
    }
    
    //クリアが押された
    public void clearbutton_onClick(View view) {
        TextView txt0 = (TextView)findViewById(R.id.txt0);
        txt0.setText("0");
        //TextView txtBin = (TextView)findViewById(R.id.txtBin);
        //txtBin.setText(atext);
    }
    
    //変換radioボタンが押されて変更された時
    public void convertbutton_onClick(RadioGroup group, int checkedId) {
        TextView txt0 = (TextView)findViewById(R.id.txt0);
        String curStr = txt0.getText().toString();
        
        // 変換前の値を取得
        int value = 0;
        switch(m_mode) {
        case R.id.binRadio:
        	value = Integer.parseInt(curStr, 2);
        	break;
        case R.id.decRadio:
        	value = Integer.parseInt(curStr);
        	break;
        case R.id.hexRadio:
        	value = Integer.parseInt(curStr, 16);
        	break;
        }
        
        // 変更後の文字列取得
        switch(checkedId) {
	    case R.id.binRadio:
	    	txt0.setText( Integer.toBinaryString(value) );
	    	break;
	    case R.id.decRadio:
	    	txt0.setText( String.valueOf(value) );
	    	break;
	    case R.id.hexRadio:
	    	txt0.setText( Integer.toHexString(value) );
	    	break;
	    }
        
        m_mode = checkedId;
    }
}
