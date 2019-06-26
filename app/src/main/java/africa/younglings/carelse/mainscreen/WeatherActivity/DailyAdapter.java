package africa.younglings.carelse.mainscreen.WeatherActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass.Data;
import africa.younglings.carelse.mainscreen.R;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    Data[] data;
    Context context;
    Date date;

    public DailyAdapter(Data[] data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.daily_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyAdapter.ViewHolder viewHolder, int i) {
        if(data.length != 0){
            viewHolder.tvDescription.setText(data[i].getSummary().replace('.',' '));
            int temp = convertTemperature(Double.parseDouble(data[i].getApparentTemperatureLow()));
            viewHolder.tvTemp.setText( temp + "\u2103");
            date = new Date(data[i].getTime() * 1000);
            viewHolder.tvDate.setText(convertDate(date));
        }else{

        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvDate, tvDescription, tvTemp;
        public ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvListDate);
            tvDescription = itemView.findViewById(R.id.tvListDescription);
            tvTemp = itemView.findViewById(R.id.tvListTemp);
            icon = itemView.findViewById(R.id.imgListIcon);
        }
    }

    public int convertTemperature(double temp){
        int tempNew = (int) Math.round((temp - 32)/1.8000);
        return tempNew;
    }

    public String convertDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("E, dd");
//        Date date = new Date(time * 1000);
        return dateFormat.format(date);
    }
}
