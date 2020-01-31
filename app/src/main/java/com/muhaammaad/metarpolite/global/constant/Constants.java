package com.muhaammaad.metarpolite.global.constant;

/**
 * Responsible for all kind of constants used in app globally
 */
public class Constants {
    /**
     * Constants for Network Apis
     */
    public static class Network {
        public static final String BASE_URL = "https://aviationweather.gov/";
        public static final String METAR_URL = "adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&stationString=~DE&hoursBeforeNow=1";
        public static final String STATIONS_URL = "adds/dataserver_current/httpparam?dataSource=stations&requestType=retrieve&format=xml&stationString=~DE&hoursBeforeNow=1";
    }

    /**
     * Constants for Aviation Apis
     */
    public static class AviationResponseTags {
        public static final String TAG_DATA = "data";
        public static final String TAG_METARS = "METAR";
        public static final String TAG_STATIONS = "Station";
    }


}
