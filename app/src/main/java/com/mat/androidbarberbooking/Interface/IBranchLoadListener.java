package com.mat.androidbarberbooking.Interface;

import com.mat.androidbarberbooking.Model.Salon;

import java.util.List;

public interface IBranchLoadListener {
    void onBranchLoadSuccess(List<Salon> salonList);
    void onBranchLoadFailes(String message);
}
