package com.example.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;


public class CustomListItemAdapter extends ArrayAdapter<Item> {
    Context context;
    Item[] items;
    int positionses;

    public CustomListItemAdapter(Context context, int layoutTobeInflated, Item[] items){
        super(context, R.layout.list_item_lnk_img, items);
        this.context = context;
        this.items = items;
     }

    private CompoundButton.OnCheckedChangeListener itemChangeChecked = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                int i = Integer.parseInt(buttonView.getTag().toString());
                items[i].setSelected(true);
            }
            else {
                int i = Integer.parseInt(buttonView.getTag().toString());
                items[i].setSelected(false);
            }

        }
    };
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.list_item_lnk_img, null);
        TextView date = (TextView) row.findViewById(R.id.tvDate);
        ImageView Image = (ImageView) row.findViewById(R.id.ivimg);

        date.setText(items[position].getNgay());
        date.setTag(position);
        items[position].setPosition(position);
        Image.setImageBitmap(items[position].getImage());
        CheckBox cbSelected = row.findViewById(R.id.cbDelete);
        cbSelected.setChecked(items[position].isSelected());
        cbSelected.setTag(position);

        cbSelected.setOnCheckedChangeListener( itemChangeChecked);
        cbSelected.setOnCheckedChangeListener( itemChangeChecked);
        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, imageget.class);
                intent.putExtra("Chi tiet", items[position].getName());
                context.startActivity(intent);
            }
        });
        return row;

    }


}
