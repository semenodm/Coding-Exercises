package org.alex.alg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

public class TruckScheduleAlgorithm {
	public static ScheduleItem[] calculateCarrierSchedule(Date currentTime, String[] carrierList) throws Exception {
		ScheduleItem input = new ScheduleItem(currentTime, carrierList[0]);
		TreeSet schedule = setupSchedule();
	
		Iterator it = schedule.iterator();
		ArrayList<ScheduleItem> result = new ArrayList<ScheduleItem>();
		
		while(result.size() < 2) {
			ScheduleItem current = (ScheduleItem) schedule.higher(input);;
			
			long diffDays = current.getPickupTime().getTime() - input.getPickupTime().getTime();
			
			if (current != null && diffDays/(60 * 60 * 1000) >= 3
					&& Arrays.asList(carrierList).contains(current.getCarrier())) {
				result.add(current);
			}
			
			if (current != null) {
				schedule.remove(current);
			} else {
				schedule = setupSchedule();
				Calendar cal = Calendar.getInstance();
				cal.setTime(input.getPickupTime());
				cal.add(Calendar.DAY_OF_MONTH, -7);
				input.setPickupTime(cal.getTime());
			}
		}
		
		return result.toArray(new ScheduleItem[result.size()]);
	}
	
	private static TreeSet setupSchedule() throws Exception {
		TreeSet schedule = new TreeSet();
		
		// Carrier A
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Monday-00-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Monday-12-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Tuesday-00-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Tuesday-12-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Wednesday-00-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Wednesday-12-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Thursday-00-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Thursday-12-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Friday-00-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Friday-12-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Saturday-12-00"), "A"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Sunday-12-00"), "A"));
		
		// Carrier B
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Monday-19-00"), "B"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Tuesday-19-00"), "B"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Wednesday-19-00"), "B"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Thursday-19-00"), "B"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Sunday-19-00"), "B"));
		
		// Carrier C
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Monday-15-00"), "C"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Monday-21-00"), "C"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Tuesday-15-00"), "C"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Wednesday-15-00"), "C"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Wednesday-21-00"), "C"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Thursday-15-00"), "C"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Friday-15-00"), "C"));
		schedule.add(new ScheduleItem(new SimpleDateFormat("yyyy-MM-E-HH-mm").parse("2012-02-Friday-21-00"), "C"));
		
		return schedule;
	}
}
