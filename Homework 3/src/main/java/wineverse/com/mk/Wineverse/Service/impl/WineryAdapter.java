package wineverse.com.mk.Wineverse.Service.impl;

import wineverse.com.mk.Wineverse.Model.Winery;

public class WineryAdapter {
    public static String convertToString(Winery winery) {
        return String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName());
    }
}

