package util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PH on 08/08/2015.
 */
public class InstagramUtil {

    //JSONObject fotoJSON

    /**
     * Metodo para recuperar foto dos instagram
     * standard_resolution
     * @param json
     * @return
     */
    public static String recuperarFoto(JSONObject json){
        String fotoURL = "";
        try {
            fotoURL = json.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fotoURL;
    }

    /**
     * Metodo para recuperar url das foto dos instagram
     * standard_resolution
     * @param json
     * @return
     */
    public static String recuperarURL(JSONObject json){
        String url = "";
        try {
            url = json.getString("link");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Metodo para recuperar user name
     * standard_resolution
     * @param json
     * @return
     */
    public static String recuperarUserName(JSONObject json){
        String user = "";
        try {
            user = json.getJSONObject("user").getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Metodo para recuperar Thumb dos instagram
     * thumb
     * @param json
     * @return
     */
    public static String recuperarThumb(JSONObject json){
        String fotoURL = "";
        try {
            fotoURL = json.getJSONObject("images").getJSONObject("thumbnail").getString("url") ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fotoURL;
    }

    public static Bitmap loadBitmap(String url) {
        try {
            URL newurl = new URL(url);
            Bitmap b = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            return b;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Monta a urla de consulta por TAG
     * @param tag
     * @return
     */
    public static String recuperarURLconsultarTAG(final String tag){
        return Constante.CONSULTAR_TAG + tag + Constante.CONSULTAR_TAG_MEDIA + Constante.TOKEN;
    }

    public static JSONArray recuperarResultadoConsulta(final String conteudo){
        JSONArray resultados = null;
        try {
            JSONObject jsonObject = new JSONObject( conteudo );
            resultados = jsonObject.getJSONArray("data");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    public static List<Map<String,Object>> converterJSONListMap(final JSONArray resultados){
        List<Map<String,Object>> fotos = new ArrayList< Map<String,Object> >();
        Integer x = resultados.length();
        try {
        for(int i = 0 ; i <x ; i++){
            JSONObject fotoJSON = null;
            fotoJSON = resultados.getJSONObject(i);
            Map<String,Object> mapa = new HashMap<String,Object>();
            String foto = InstagramUtil.recuperarFoto(fotoJSON);
            String url = InstagramUtil.recuperarURL(fotoJSON);
            String username = InstagramUtil.recuperarUserName(fotoJSON);
            String thumb =  InstagramUtil.recuperarThumb(fotoJSON);
            mapa.put( "url" , url );
            mapa.put( "username" , username );
            mapa.put( "thumb" , InstagramUtil.loadBitmap(thumb) );
            mapa.put( "fotoStandard" , InstagramUtil.loadBitmap(foto) );
            mapa.put("draw", InstagramUtil.drawableFromUrl(foto));
            fotos.add(mapa);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  fotos;
    }

    public static Drawable drawableFromUrl(String url){
        Bitmap x = null;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            x = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new BitmapDrawable(x);
    }



}
