package com.adityaandroid.weatherviewapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaandroid.weatherviewapp.R;
import com.adityaandroid.weatherviewapp.model.Forecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ForecastDataAdapter extends RecyclerView.Adapter<ForecastDataAdapter.ForecastDataViewHolder> {

    private Context mContext;
    private ArrayList<Forecast>mForecastList;

    public ForecastDataAdapter(Context mContext, ArrayList<Forecast> mForecastList) {
        this.mContext = mContext;
        this.mForecastList = mForecastList;
    }

    @NonNull
    @Override
    public ForecastDataAdapter.ForecastDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ForecastDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastDataViewHolder holder, int position) {
      Forecast currentForecast = mForecastList.get(position);
      String iconUrl = currentForecast.getIconUrl();
      String forecastDay = currentForecast.getForecastDay();
      String weatherDescription = currentForecast.getWeatherDescription();
      String lowTemp = currentForecast.getLowTemp();
      String highTemp = currentForecast.getHighTemp();
      String humidity = currentForecast.getHumidity();
      String windSpeed = currentForecast.getWindSpeed();
      String forecastTime = currentForecast.getForecastTime();

      holder.mForecastDay.setText(forecastDay);
      holder.mDescription.setText(weatherDescription);
      holder.mLowTemp.setText("Min.: "+lowTemp);
      holder.mHighTemp.setText("Max.: "+highTemp);
      holder.mHumidity.setText("Humidity: "+humidity);
      holder.mWindSpeed.setText("Wind Speed: "+windSpeed);
      holder.mForecastTime.setText("Forecast Time:  "+forecastTime);

      Picasso.get().load(iconUrl).fit().centerInside().into(holder.mWeatherIcon);

    }

    @Override
    public int getItemCount() {
        return mForecastList.size();
    }

    public class ForecastDataViewHolder extends RecyclerView.ViewHolder {
        public ImageView mWeatherIcon;
        public TextView mDescription;
        public TextView mForecastDay;
        public TextView mForecastTime;
        public TextView mLowTemp;
        public TextView mHighTemp;
        public TextView mWindSpeed;
        public TextView mHumidity;


        public ForecastDataViewHolder(@NonNull View itemView) {
            super(itemView);
            mWeatherIcon = itemView.findViewById(R.id.conditionImageView);
            mForecastDay = itemView.findViewById(R.id.day_textView);
            mForecastTime = itemView.findViewById(R.id.forecastTimeText);
            mDescription = itemView.findViewById(R.id.description_textView);
            mLowTemp = itemView.findViewById(R.id.low_temp_textView);
            mHighTemp = itemView.findViewById(R.id.high_temp_textView);
            mWindSpeed = itemView.findViewById(R.id.wind_speed_textView);
            mHumidity = itemView.findViewById(R.id.humidity_textView);
        }
    }
}
