package com.drpweb;

import com.drpweb.user.UserService;
import com.drpweb.util.DailyDiet;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ADMIN on 9/15/2016.
 */
public class DietPlanControllerTest {

    @Test
    public void testGetBmi() throws Exception {
        UserService userService = mock(UserService.class);

        when(userService.getCurrentUser().getUsername()).thenReturn("Wirunya");
        when(userService.getCurrentUser().getWeight()).thenReturn(48.0);
        when(userService.getCurrentUser().getHeight()).thenReturn(163.0);
        DailyDiet dailyDiet = mock(DailyDiet.class);

       // when(dailyDiet.calBMI(48.0,163.0)).thenReturn("["+"\""+"18.07"+"\","+"\""+"Underweight"+"\""+"]");
        assertThat(dailyDiet.calBMI(48.0,163.0),is("18.07"));
    }
}