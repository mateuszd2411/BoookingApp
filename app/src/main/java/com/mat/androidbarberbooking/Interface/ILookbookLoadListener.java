package com.mat.androidbarberbooking.Interface;

import com.mat.androidbarberbooking.Model.Banner;

import java.util.List;

public interface ILookbookLoadListener {
    void onLookbookLoadSuccess(List<Banner> banners);
    void onLookbookLoadFailed(String message);

}
