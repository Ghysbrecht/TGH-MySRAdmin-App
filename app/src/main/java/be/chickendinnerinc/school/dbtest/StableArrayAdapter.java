package be.chickendinnerinc.school.dbtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Thomas on 7/12/2017.
 */


public class StableArrayAdapter extends ArrayAdapter<Score> {
    private final Context context;
    private final List<Score> values;

    public StableArrayAdapter(Context context, List<Score> values) {
        super(context, R.layout.list_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_layout, parent, false);
        TextView firstLine = (TextView) rowView.findViewById(R.id.timeText);
        TextView secondLine = (TextView) rowView.findViewById(R.id.userText);
        TextView placeText = (TextView) rowView.findViewById(R.id.placeText);
        ImageView img = (ImageView) rowView.findViewById(R.id.scoreImage);

        firstLine.setText(values.get(position).getTime()+"s");
        secondLine.setText(values.get(position).getName());
        placeText.setText((position + 1)+ "");

        switch(position+1){
            case 1: img.setImageResource(R.drawable.ic_gold_medal); break;
            case 2: img.setImageResource(R.drawable.ic_silver_medal); break;
            case 3: img.setImageResource(R.drawable.ic_bronze_medal); break;
        }


        //Hier kan je beginnen spelen met een rij, de layout kan je aanpassen in list_layout.xml

        return rowView;
    }
}
