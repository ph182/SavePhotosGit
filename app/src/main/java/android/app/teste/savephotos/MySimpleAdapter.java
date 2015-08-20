package android.app.teste.savephotos;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PH on 06/08/2015.
 */
public class MySimpleAdapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater = null;
    private int resource;

    public MySimpleAdapter(Context context , List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        this.resource = resource;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(resource, null);

        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);

        ImageView image = (ImageView) vi.findViewById(R.id.thumb);
        //image.setImageBitmap( (Bitmap) data.get("thumb")  );
        image.setImageBitmap( (Bitmap) data.get("fotoStandard")  );

        //Button buttonFoto = (Button) vi.findViewById(R.id.buttonFoto);
        //buttonFoto.setBackground((Drawable) data.get("draw"));

        TextView tvURL = (TextView) vi.findViewById(R.id.url);
        tvURL.setText( data.get("url").toString() );

        TextView tvUsername = (TextView) vi.findViewById(R.id.username);
        tvUsername.setText( data.get("username").toString() );

        return vi;
    }
}