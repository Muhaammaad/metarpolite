
package com.muhaammaad.metarpolite.persistence.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.muhaammaad.metarpolite.model.QualityControlFlags;
import com.muhaammaad.metarpolite.model.SkyCondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Field #	Field Name	Description	Field Type	Units
 * 1	raw_text	The raw Metar	string
 * 2	station_id	Station identifier; Always a four character alphanumeric( A-Z, 0-9)	string
 * 3	observation_time	Time( in ISO8601 date/time format) this Metar was observed.	string	ISO 8601 date/ time
 * 4	latitude	The latitude (in decimal degrees )of the station that reported this Metar	float	decimal degrees
 * 5	longitude	The longitude (in decimal degrees) of the station that reported this Metar	float	decimal degrees
 * 6	temp_c	Air temperature	float	C
 * 7	dewpoint_c	Dewpoint temperature	float	C
 * 8	wind_dir_degrees	Direction from which the wind is blowing.
 * 0 degrees=variable wind direction.	integer	degrees
 * 9	wind_speed_kt	Wind speed; 0 degree wdir and 0 wspd = calm winds	integer	kts
 * 10	wind_gust_kt	Wind gust	integer	kts
 * 11	visibility_statute_mi	Horizontal visibility	float	statute miles
 * 12	altim_in_hg	Altimeter	float	inches of Hg
 * 13	sea_level_pressure_mb	Sea-level pressure	float	mb
 * 14	quality_control_flags	Quality control flags (see below) provide useful information about the Metar station(s) that provide the data.	string
 * 15	wx_string	wx_string descriptions	string
 * 16	sky_condition	sky_cover - up to four levels of sky cover and base can be reported under the sky_conditions field; OVX present when vert_vis_ft is reported.
 * Allowed values: SKC|CLR|CAVOK|FEW|SCT|BKN|OVC|OVX	string
 * cloud_base_ft_agl - height of cloud base in feet AGL. Up to four levels can be reported under the sky_conditions field. A value exists when the corresponding sky_cover='FEW','SCT','BKN', 'OVC'	integer	ft (AGL)
 * 17	flight_category	Flight category of this Metar. Values: VFR|MVFR|IFR|LIFR
 * See http://www.aviationweather.gov/metar/help?page=plot#fltcat	string
 * 18	three_hr_pressure_tendency_mb	Pressure change in the past 3 hours	float	mb
 * 19	maxT_c	Maximum air temperature from the past 6 hours	float	C
 * 20	minT_c	Minimum air temperature from the past 6 hours	float	C
 * 21	maxT24hr_c	Maximum air temperature from the past 24 hours	float	C
 * 22	minT24hr_c	Minimum air temperature from the past 24 hours	float	C
 * 23	precip_in	Liquid precipitation since the last regular Metar	float	in
 * 24	pcp3hr_in	Liquid precipitation from the past 3 hours. 0.0005 in = trace precipitation	float	in
 * 25	pcp6hr_in	Liquid precipitation from the past 6 hours. 0.0005 in = trace precipitation	float	in
 * 26	pcp24hr_in	Liquid precipitation from the past 24 hours. 0.0005 in = trace precipitation	float	in
 * 27	snow_in	Snow depth on the ground	float	in
 * 28	vert_vis_ft	Vertical Visibility	integer	ft
 * 29	metar_type	Metar or SPECI	string
 * 30	elevation_m	The elevation of the station that reported this Metar	float	meters
 * <p>
 * Metar Quality Control Flag Description
 * Quality control flags provide useful information on the Metar station sensors. Please see the Federal Meteorological Handbook (FMH-1) for more information.
 * <p>
 * The table below describes the available values that the dataserver can provide:
 * <p>
 * Reported Value Name	Corresponding description
 * corrected	Corrected
 * auto	Fully automated
 * auto_station	Indicates that the automated station type is one of the following: A01|A01A|A02|A02A|AOA|AWOS
 * <p>
 * NOTE: The type of station is not returned. This simply indicates that this station is one of the six stations enumerated above.
 * maintenance_indicator	Maintenance check indicator - maintenance is needed
 * no_signal	No signal
 * lightning_sensor_off	The lightning detection sensor is not operating- thunderstorm information is not available.
 * freezing_rain_sensor_off	The freezing rain sensor is not operating
 * present_weather_sensor_off	The present weather sensor is not operating
 */

@Entity(tableName = "Metar", indices = {@Index(value = {"rawText"}, unique = true)})
public class Metar implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;
    public String rawText;
    public String visibilityStatuteMi;
    public String flightCategory;
    public String observationTime;
    public String dewpointC;
    public String windDirDegrees;
    public String stationId;
    public String tempC;
    public String windSpeedKt;
    public String altimInHg;
    public String windGustKt;
    public String wxString;

    @Ignore
    public Station station;
    @Ignore
    public QualityControlFlags qualityControlFlags;

    @Ignore
    public List<SkyCondition> skyCondition = new ArrayList<>();

    public String getTime() {
        return observationTime;//"" + TimeUtil.getMillies(observationTime);
    }

    @Override
    public String toString() {
        return "Metar{" +
                "rawText='" + rawText + '\'' +
                ", visibilityStatuteMi='" + visibilityStatuteMi + '\'' +
                ", flightCategory='" + flightCategory + '\'' +
                ", observationTime='" + observationTime + '\'' +
                ", skyCondition=" + skyCondition +
                ", dewpointC='" + dewpointC + '\'' +
                ", windDirDegrees='" + windDirDegrees + '\'' +
                ", stationId='" + stationId + '\'' +
                ", tempC='" + tempC + '\'' +
                ", windSpeedKt='" + windSpeedKt + '\'' +
                ", altimInHg='" + altimInHg + '\'' +
                ", qualityControlFlags=" + qualityControlFlags +
                ", windGustKt='" + windGustKt + '\'' +
                ", wxString='" + wxString + '\'' +
                '}';


    }
}
