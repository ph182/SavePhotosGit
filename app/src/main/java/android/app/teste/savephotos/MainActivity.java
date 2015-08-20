package android.app.teste.savephotos;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import util.HttpUtil;
import util.InstagramUtil;


public class MainActivity extends ActionBarActivity {


    EditText pesquisa;
    Button buscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pesquisa = (EditText) findViewById(R.id.pesquisa);
        buscar = (Button) findViewById(R.id.buttonBuscar);
        View.OnClickListener btBuscar = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonBuscar) {
                    new InstagramTask().execute(pesquisa.getText().toString());
                }
            }
        };
        buscar.setOnClickListener(btBuscar);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class InstagramTask extends AsyncTask<String, Void, List<Map<String,Object>>> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Aguarde...");
            dialog.show();
        }

        @Override
        protected List<Map<String,Object>>  doInBackground(String... param) {
            String pesquisa = param[0].trim();
            String link = InstagramUtil.recuperarURLconsultarTAG(pesquisa);
            String conteudo = HttpUtil.acessarURL(link);
            JSONArray resultados =  InstagramUtil.recuperarResultadoConsulta(conteudo);
            List<Map<String,Object>> fotos = InstagramUtil.converterJSONListMap(resultados);
            return fotos;
        }

        protected void onPostExecute(List<Map<String,Object>> fotos) {
            String[] de = {"url"     , "username"    , "thumb"    };
            int[] para =  { R.id.url , R.id.username , R.id.thumb };

            MySimpleAdapter adapter = new MySimpleAdapter(MainActivity.this , fotos , R.layout.activity_layout_foto, de , para);

            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter((ListAdapter) adapter);

            dialog.dismiss();
        }
    }
}

