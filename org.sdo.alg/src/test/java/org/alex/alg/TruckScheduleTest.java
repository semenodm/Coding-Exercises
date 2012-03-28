package org.alex.alg;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TruckScheduleTest {
	private final Date inputDate;
	private final String[] carrierList;
	private final ScheduleItem[] output;

	@Parameters
	public static Collection<Object[]> data() throws Exception {
		return Arrays
				.asList(new Object[][] {
						{ new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Monday-11-00"), 
						  new String[] {"A", "B", "C"}, 
						  new ScheduleItem[]{
							new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Monday-15-00"), "C"), 
							new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Monday-19-00"), "B")} 
						}, // C, Monday 15:00; B, Monday 19:00
						  
						{ new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Wednesday-21-00"), 
						  new String[] {"B", "C"}, 
						  new ScheduleItem[]{
							new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Thursday-15-00"), "C"), 
							new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Thursday-19-00"), "B")} 
						}, // C, Monday 15:00; B, Monday 19:00
						
						{ new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Thursday-20-00"), 
						  new String[] {"B"}, 
						  new ScheduleItem[]{
							new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Sunday-19-00"), "B"), 
							new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Monday-19-00"), "B")} 
						}, // C, Monday 15:00; B, Monday 19:00
				});
	}
	
	public TruckScheduleTest(Date inputDate, String[] carrierList, ScheduleItem[] output) {
		this.inputDate = inputDate;
		this.carrierList = carrierList;
		this.output = output;
		
	}
	
	@Test
	public void testAlgo() throws Exception {
		assertThat("Check carriers list", 
				Arrays.asList(TruckScheduleAlgorithm.calculateCarrierSchedule(inputDate, carrierList)), 
				hasItems(output));
	}

}
