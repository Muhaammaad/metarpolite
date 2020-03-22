package com.muhaammaad.metarpolite;

import com.muhaammaad.metarpolite.global.constant.Constants;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.ui.main.util.AviationResponseParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Metar XML Parser unit test, which will execute on the development machine.
 */
public class MetarParserUnitTest {
    private static final String XML_SAMPLE =
            "<METAR>\n" +
                    "<raw_text>\n" +
                    "KDEN 181153Z 22005KT 10SM FEW200 03/M02 A2984 RMK AO2 SLP079 T00281017 10061 20017 55001\n" +
                    "</raw_text>\n" +
                    "<station_id>KDEN</station_id>\n" +
                    "<observation_time>2020-03-18T11:53:00Z</observation_time>\n" +
                    "<latitude>39.85</latitude>\n" +
                    "<longitude>-104.65</longitude>\n" +
                    "<temp_c>2.8</temp_c>\n" +
                    "<dewpoint_c>-1.7</dewpoint_c>\n" +
                    "<wind_dir_degrees>220</wind_dir_degrees>\n" +
                    "<wind_speed_kt>5</wind_speed_kt>\n" +
                    "<visibility_statute_mi>10.0</visibility_statute_mi>\n" +
                    "<altim_in_hg>29.840551</altim_in_hg>\n" +
                    "<sea_level_pressure_mb>1007.9</sea_level_pressure_mb>\n" +
                    "<quality_control_flags>\n" +
                    "<auto_station>TRUE</auto_station>\n" +
                    "</quality_control_flags>\n" +
                    "<sky_condition sky_cover=\"FEW\" cloud_base_ft_agl=\"20000\"/>\n" +
                    "<flight_category>VFR</flight_category>\n" +
                    "<three_hr_pressure_tendency_mb>-0.1</three_hr_pressure_tendency_mb>\n" +
                    "<maxT_c>6.1</maxT_c>\n" +
                    "<minT_c>1.7</minT_c>\n" +
                    "<metar_type>METAR</metar_type>\n" +
                    "<elevation_m>1640.0</elevation_m>\n" +
                    "</METAR>";

    private XmlPullParser parser;

    @Before
    public void createParser() throws XmlPullParserException, IOException {
        InputStream inputStream = new ByteArrayInputStream(XML_SAMPLE.getBytes(Charset.forName("UTF-8")));
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        parser = factory.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(inputStream, null);
        int eventType = parser.next();
        while (eventType != XmlPullParser.START_TAG) {
            eventType = parser.next();
        }
    }

    @Test
    public void readMetar() {
        try {
            Metar metar = AviationResponseParser.readMETAR(parser, Constants.AviationResponseTags.TAG_METARS);
            Assert.assertNotNull(metar);
            Assert.assertNotNull(metar.tempC);
            Assert.assertNotNull(metar.rawText);
            Assert.assertNotNull(metar.stationId);
            Assert.assertNotNull(metar.skyCondition);
            Assert.assertNotNull(metar.skyCondition.get(0));
            Assert.assertNotNull(metar.qualityControlFlags);
            Assert.assertNotNull(metar.qualityControlFlags.autoStation);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
