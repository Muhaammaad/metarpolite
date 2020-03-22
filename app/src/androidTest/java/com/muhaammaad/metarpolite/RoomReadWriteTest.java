package com.muhaammaad.metarpolite;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.muhaammaad.metarpolite.persistence.dao.MetarDao;
import com.muhaammaad.metarpolite.persistence.dao.QualityControlFlagsDao;
import com.muhaammaad.metarpolite.persistence.dao.SkyConditionDao;
import com.muhaammaad.metarpolite.persistence.dao.StationDao;
import com.muhaammaad.metarpolite.persistence.db.AviationDatabase;
import com.muhaammaad.metarpolite.persistence.entity.Metar;
import com.muhaammaad.metarpolite.persistence.entity.MetarAndStation;
import com.muhaammaad.metarpolite.persistence.entity.QualityControlFlags;
import com.muhaammaad.metarpolite.persistence.entity.SkyCondition;
import com.muhaammaad.metarpolite.persistence.entity.Station;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 * <p>
 * Room database insertion fetching and transactions test class
 */
@RunWith(AndroidJUnit4.class)
public class RoomReadWriteTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MetarDao metarDao;
    private StationDao stationDao;
    private QualityControlFlagsDao qualityControlFlagsDao;
    private SkyConditionDao skyConditionDao;
    private AviationDatabase db;
    private long metarID = 1;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AviationDatabase.class).build();
        metarDao = db.metarDao();
        stationDao = db.stationDao();
        qualityControlFlagsDao = db.qualityControlFlagsDao();
        skyConditionDao = db.skyConditionDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeMetarAndReadInList() {
        Metar metar = new Metar();
        metar.id = metarID;
        metar.rawText = "KSEA 181412Z 13006KT 7SM SCT005 02/01 A2988 RMK AO2 T00220011";
        metar.visibilityStatuteMi = "12";
        metar.flightCategory = "2";
        metar.observationTime = "123123";
        metar.dewpointC = "12";
        metar.windDirDegrees = "111";
        metar.stationId = "KDEN";
        metar.tempC = "0";
        metar.windSpeedKt = "0";
        metar.altimInHg = "11";
        metar.windGustKt = "223";
        metar.wxString = "";
        metarID = metarDao.insertId(metar);
        List<Metar> metars = metarDao.getMetars();
        Assert.assertEquals(metars.get(0).toString(), metar.toString());
    }

    @Test
    public void writeStationAndReadInList() {
        Station station = new Station();
        station.stationId = "KDEN";
        station.elevation = "72565";
        station.country = "US";
        station.site = "";
        station.latitude = "11.11";
        station.state = "CO";
        station.longitude = "11.22";
        stationDao.insert(station);
        Station stations = stationDao.getStation(station.stationId);
        Assert.assertEquals(stations.toString(), station.toString());
    }


    @Test
    public void writeFlagsAndReadInList() {
        writeMetarAndReadInList();
        QualityControlFlags qualityControlFlags = new QualityControlFlags();
        qualityControlFlags.metarId = metarID;
        qualityControlFlags.auto = "true";
        qualityControlFlags.noSignal = "";
        qualityControlFlags.autoStation = "";
        qualityControlFlags.corrected = "";
        qualityControlFlags.maintenanceIndicator = "";
        qualityControlFlags.lightningSensorOff = "true";
        qualityControlFlags.freezingRainSensorOff = "";
        qualityControlFlags.presentWeatherSensorOff = "";
        qualityControlFlagsDao.insert(qualityControlFlags);
        QualityControlFlags qualityControlFlags1 = qualityControlFlagsDao.getMetarQualityFlags(String.valueOf(qualityControlFlags.metarId));
        Assert.assertEquals(qualityControlFlags1.toString(), qualityControlFlags.toString());
    }


    @Test
    public void writeSkyConditionsAndReadInList() {
        writeMetarAndReadInList();
        SkyCondition skyCondition = new SkyCondition();
        skyCondition.metarId = metarID;
        skyCondition.cloudBaseFtAgl = "12";
        skyCondition.skyCover = "true";
        skyConditionDao.insert(skyCondition);
        List<SkyCondition> metars = skyConditionDao.getMetarSkyConditions(String.valueOf(skyCondition.metarId));
        Assert.assertEquals(metars.get(0).toString(), skyCondition.toString());
    }


    @Test
    public void checkMetarTransaction() {
        writeMetarAndReadInList();
        writeStationAndReadInList();
        List<MetarAndStation> metars;
        try {
            metars = LiveDataTestUtil.getValue(metarDao.getMetarAndStation());
            Assert.assertNotNull(metars);
            Assert.assertNotNull(metars.get(0));
            Assert.assertNotNull(metars.get(0).metar);
            Assert.assertNotNull(metars.get(0).station);
        } catch (InterruptedException e) {
            Assert.fail();
        }

    }
}