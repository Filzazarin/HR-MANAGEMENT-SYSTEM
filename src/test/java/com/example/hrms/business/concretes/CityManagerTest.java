package com.example.hrms.business.concretes;

import com.example.hrms.core.utilities.results.DataResult;
import com.example.hrms.core.utilities.results.ErrorResult;
import com.example.hrms.core.utilities.results.Result;
import com.example.hrms.dataAccess.abstracts.CityDao;
import com.example.hrms.entities.concretes.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityManagerTest {

    @Mock
    private CityDao cityDao;

    @InjectMocks
    private CityManager cityManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        City city1 = new City();
        city1.setId(1);
        City city2 = new City();
        city2.setId(2);

        when(cityDao.findAll()).thenReturn(Arrays.asList(city1, city2));

        DataResult<List<City>> result = cityManager.getAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getData().size());
        assertEquals("All cities are listed.", result.getMessage());
        verify(cityDao, times(1)).findAll();
    }

    @Test
    void testAdd_Success() {
        City city = new City();
        city.setCityName("Istanbul");

        when(cityDao.existsCitiesByCityName("Istanbul")).thenReturn(false);
        when(cityDao.save(city)).thenReturn(city);

        Result result = cityManager.add(city);

        assertTrue(result.isSuccess());
        assertEquals("City is added.", result.getMessage());
        verify(cityDao, times(1)).save(city);
    }

    @Test
    void testAdd_Failure_Duplicate() {
        City city = new City();
        city.setCityName("Istanbul");

        when(cityDao.existsCitiesByCityName("Istanbul")).thenReturn(true);

        Result result = cityManager.add(city);

        assertFalse(result.isSuccess());
        assertEquals("City has already exist!", result.getMessage());
        verify(cityDao, never()).save(city);
    }

    @Test
    void testDelete_Success() {
        City city = new City();
        city.setId(1);
        city.setCityName("Istanbul");

        when(cityDao.existsCitiesByCityName("Istanbul")).thenReturn(true);
        when(cityDao.existsById(1)).thenReturn(true);
        doNothing().when(cityDao).delete(city);

        Result result = cityManager.delete(city);

        assertTrue(result.isSuccess());
        assertEquals("City is deleted.", result.getMessage());
        verify(cityDao, times(1)).delete(city);
    }

    @Test
    void testDelete_Failure_NotFound() {
        City city = new City();
        city.setId(1);
        city.setCityName("Istanbul");

        when(cityDao.existsCitiesByCityName("Istanbul")).thenReturn(false);

        Result result = cityManager.delete(city);

        assertFalse(result.isSuccess());
        assertEquals("City has been not found.", result.getMessage());
        verify(cityDao, never()).delete(city);
    }
}
