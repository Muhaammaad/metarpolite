package com.muhaammaad.metarpolite.ui.main.util;

import android.util.Xml;
import com.muhaammaad.metarpolite.model.AviationResponse;
import com.muhaammaad.metarpolite.model.METAR;
import com.muhaammaad.metarpolite.model.Station;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import static com.muhaammaad.metarpolite.global.constant.Constants.AviationResponseTags.*;
import static com.muhaammaad.metarpolite.global.util.XmlParserUtil.*;

/**
 * Aviation Api Responses Xml parsing package-private utils
 */
public class AviationResponseParser {
    /**
     * finds the data tag. If it encounters a data tag, parse it otherwise keep looping
     */
    public static void parseAviationResponseStream(InputStream inputStream, AviationResponse aviationResponse) {
        if (inputStream == null)
            return;
        if (aviationResponse == null)
            aviationResponse = new AviationResponse();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            while (true) {
                parser.next();
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals(TAG_DATA)) {
                    readData(parser, aviationResponse);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the contents of Data tag. If it encounters a METAR or Station tag, hands them off
     * to "read" method for processing and save the result. Otherwise, skips the tag.
     */
    private static void readData(XmlPullParser parser, AviationResponse aviationResponse) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_DATA);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TAG_METARS)) {
                METAR metar = readMETAR(parser, name);
                metar.station = aviationResponse.response.data.stations.get(metar.stationId);
                aviationResponse.response.data.metars.add(metar);
            } else if (name.equals(TAG_STATIONS)) {
                Station station = readStation(parser, name);
                aviationResponse.response.data.stations.put(station.station_id, station);
            } else skip(parser);
        }

    }

    /**
     * Parses the contents of METAR tag and returns Metar. Take wanted tags otherwise skip the unwanted ones.
     */
    private static METAR readMETAR(XmlPullParser parser, String TAG) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        METAR metar = new METAR();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "station_id":
                    metar.stationId = readTag(parser, name);
                    break;
                case "temp_c":
                    metar.tempC = readTag(parser, name);
                    break;
                case "elevation_m":
                    metar.elevationM = readTag(parser, name);
                    break;
                case "visibility_statute_mi":
                    metar.visibilityStatuteMi = readTag(parser, name);
                    break;
                case "flight_category":
                    metar.flightCategory = readTag(parser, name);
                    break;
                case "altim_in_hg":
                    metar.altimInHg = readTag(parser, name);
                    break;
                case "observation_time":
                    metar.observationTime = readTag(parser, name);
                    break;
                case "dewpoint_c":
                    metar.dewpointC = readTag(parser, name);
                    break;
                case "raw_text":
                    metar.rawText = readTag(parser, name);
                    break;
                case "wind_dir_degrees":
                    metar.windDirDegrees = readTag(parser, name);
                    break;
                case "wind_gust_kt":
                    metar.windGustKt = readTag(parser, name);
                    break;
                case "wind_speed_kt":
                    metar.windSpeedKt = readTag(parser, name);
                    break;
                case "wx_string":
                    metar.wxString = readTag(parser, name);
                    break;
                case "longitude":
                    metar.longitude = readTag(parser, name);
                    break;
                case "latitude":
                    metar.latitude = readTag(parser, name);
                    break;
                case "metar_type":
                    metar.metarType = readTag(parser, name);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return metar;
    }

    /**
     * Parses the contents of Station tag and returns station. Take wanted tags otherwise skip the unwanted ones.
     */
    private static Station readStation(XmlPullParser parser, String TAG) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        Station station = new Station();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "country":
                    station.country = readTag(parser, name);
                    break;
                case "elevation_m":
                    station.elevation_m = readTag(parser, name);
                    break;
                case "site":
                    station.site = readTag(parser, name);
                    break;
                case "station_id":
                    station.station_id = readTag(parser, name);
                    break;
                case "state":
                    station.state = readTag(parser, name);
                    break;
                case "longitude":
                    station.longitude = readTag(parser, name);
                    break;
                case "latitude":
                    station.latitude = readTag(parser, name);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return station;
    }
}