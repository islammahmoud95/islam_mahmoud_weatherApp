package com.task.weatherapp.common;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.task.weatherapp.R;
import com.task.weatherapp.data.network.model.ErrorsMessage;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class Utilities {

    @BindingAdapter("image")
    public static void loadImage(ImageView view,String imageUrl) {
        Glide.with(view.getContext())
                .applyDefaultRequestOptions(
                        new RequestOptions().placeholder(R.drawable.location_city_24px).error(R.drawable.location_city_24px)
                )
                .load(Const.BASEIMAGEURL+imageUrl+".png").apply(new RequestOptions().centerInside())
                .into(view);
    }
    public static ErrorsMessage GetErrorResponse( HttpException throwable ) {
        JSONObject jObjError = null;
        try {
          //  jObjError = new JSONObject(throwable.response().errorBody().toString());
            Gson gson = new Gson();
            ErrorsMessage erorResponse=gson.fromJson(throwable.response().errorBody().toString(), ErrorsMessage.class);
            return erorResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
