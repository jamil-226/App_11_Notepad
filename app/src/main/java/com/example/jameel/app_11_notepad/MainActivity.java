package com.example.jameel.app_11_notepad;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Button btnSave;
    Button btnLoad;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath();
   // private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textViewLoad);
        editText = (EditText)findViewById(R.id.editText);
        btnLoad = (Button)findViewById(R.id.btnLoad);
        btnSave = (Button)findViewById(R.id.btnSave);
        File dir = new File(path);
        dir.mkdir();
    }
    public void onSave(View view)
    {
        if(editText.getText().toString().length()==0)
        {
            editText.setError("Please Enter Some Text here");
            editText.requestFocus();
        }
        File file = new File(path+"/savedFile.txt");
        String[] saveText = String.valueOf(editText.getText()).split(System.getProperty("line.separator"));
        editText.setText("");
        Toast.makeText(getApplicationContext(),"Document has bee Saved Successfully",Toast.LENGTH_LONG).show();
        Save (file, saveText);
    }
    public void onLoad(View view)
    {
        File file = new File (path + "/savedFile.txt");
        String [] loadText = Load(file);

        String finalString = "";
       // int i;
        for (int i = 0; i < loadText.length; i++)
        {

            finalString += loadText[i] + System.getProperty("line.separator");
        }
        textView.setText(finalString);
    }
    public static void Save(File file, String[] data)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }


    public static String[] Load(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int a=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                a++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[a];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
