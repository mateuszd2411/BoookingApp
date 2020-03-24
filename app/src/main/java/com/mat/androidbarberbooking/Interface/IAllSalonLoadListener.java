package com.mat.androidbarberbooking.Interface;

import java.util.List;

public interface IAllSalonLoadListener {
    void onAllSallonLoadSuccess(List<String> areaNameList);
    void onAllSallonLoadFailes(String message);
}
