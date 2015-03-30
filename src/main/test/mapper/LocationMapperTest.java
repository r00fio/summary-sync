package mapper;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocationMapperTest {

    @Test
    public void testMap() throws Exception {
        LocationMapper locationMapper = new LocationMapper();
        locationMapper.init();
        String cityId = locationMapper.map("Харьков");
        assertNotNull(cityId);
    }
}