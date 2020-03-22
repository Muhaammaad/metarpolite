package com.muhaammaad.metarpolite.ui.main.util;

import android.util.Xml;

import com.muhaammaad.metarpolite.persistence.entity.QualityControlFlags;
import com.muhaammaad.metarpolite.persistence.entity.SkyCondition;
import com.muhaammaad.metarpolite.persistence.AviationData;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.Station;
import com.muhaammaad.metarpolite.persistence.repo.AviationRepo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import static com.muhaammaad.metarpolite.global.constant.Constants.AviationResponseTags.TAG_DATA;
import static com.muhaammaad.metarpolite.global.constant.Constants.AviationResponseTags.TAG_METARS;
import static com.muhaammaad.metarpolite.global.constant.Constants.AviationResponseTags.TAG_STATIONS;
import static com.muhaammaad.metarpolite.global.util.XmlParserUtil.ns;
import static com.muhaammaad.metarpolite.global.util.XmlParserUtil.readTag;
import static com.muhaammaad.metarpolite.global.util.XmlParserUtil.skip;

/**
 * Aviation Api Responses Xml parsing package-private utils
 */
public class AviationResponseParser {
    /**
     * finds the data tag. If it encounters a data tag, parse it otherwise keep looping
     */
    public static void parseAviationResponseStream(InputStream inputStream, AviationData aviationResponse) {
        if (inputStream == null)
            return;
        if (aviationResponse == null)
            aviationResponse = new AviationData();
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
     * Parses the contents of Data tag. If it encounters a Metar or Station tag, hands them off
     * to "read" method for processing and save the result. Otherwise, skips the tag.
     */
    public static void readData(XmlPullParser parser, AviationData aviationResponse) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_DATA);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TAG_METARS)) {
                Metar metar = readMETAR(parser, name);
                AviationRepo.getInstance().insertMetar(metar);
                metar.station = aviationResponse.stations.get(metar.stationId);//AviationRepo.getInstance().getStationById(metar.stationId);//aviationResponse.stations.get(metar.stationId);
                aviationResponse.metars.add(metar);
            } else if (name.equals(TAG_STATIONS)) {
                Station station = readStation(parser, name);
//                AviationRepo.getInstance().insertStation(station);
                aviationResponse.stations.put(station.stationId, station);
            } else skip(parser);
        }

    }

    /**
     * Parses the contents of Metar tag and returns Metar. Take wanted tags otherwise skip the unwanted ones.
     */
    public static Metar readMETAR(XmlPullParser parser, String TAG) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        Metar metar = new Metar();
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
                case "quality_control_flags":
                    metar.qualityControlFlags = readQualityFlags(parser, name);
                    break;
                case "sky_condition":
                    metar.skyCondition.add(readSkyCondition(parser, name));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return metar;
    }

    /**
     * Parses the contents of Sky conditions tag and returns SkyCondition. Take wanted tags otherwise skip the unwanted ones.
     */
    private static SkyCondition readSkyCondition(XmlPullParser parser, String TAG) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        SkyCondition skyCondition = new SkyCondition();
        for (int index = 0; index < parser.getAttributeCount(); index++) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getAttributeName(index);
            switch (name) {
                case "sky_cover":
                    skyCondition.skyCover = parser.getAttributeValue(index);
                    break;
                case "cloud_base_ft_agl":
                    skyCondition.cloudBaseFtAgl = parser.getAttributeValue(index);
                    break;
            }
        }
        skip(parser);
        return skyCondition;
    }

    /**
     * Parses the contents of QualityControlFlags tag and returns QualityControlFlags. Take wanted tags otherwise skip the unwanted ones.
     */
    private static QualityControlFlags readQualityFlags(XmlPullParser parser, String TAG) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        QualityControlFlags qualityControlFlags = new QualityControlFlags();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "corrected":
                    qualityControlFlags.corrected = readTag(parser, name);
                    break;
                case "auto":
                    qualityControlFlags.auto = readTag(parser, name);
                    break;
                case "auto_station":
                    qualityControlFlags.autoStation = readTag(parser, name);
                    break;
                case "maintenance_indicator":
                    qualityControlFlags.maintenanceIndicator = readTag(parser, name);
                    break;
                case "no_signal":
                    qualityControlFlags.noSignal = readTag(parser, name);
                    break;
                case "lightning_sensor_off":
                    qualityControlFlags.lightningSensorOff = readTag(parser, name);
                    break;
                case "freezing_rain_sensor_off":
                    qualityControlFlags.freezingRainSensorOff = readTag(parser, name);
                    break;
                case "present_weather_sensor_off":
                    qualityControlFlags.presentWeatherSensorOff = readTag(parser, name);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return qualityControlFlags;
    }

    /**
     * Parses the contents of Station tag and returns station. Take wanted tags otherwise skip the unwanted ones.
     */
    public static Station readStation(XmlPullParser parser, String TAG) throws XmlPullParserException, IOException {
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
                    station.elevation = readTag(parser, name);
                    break;
                case "site":
                    station.site = readTag(parser, name);
                    break;
                case "station_id":
                    station.stationId = readTag(parser, name);
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
