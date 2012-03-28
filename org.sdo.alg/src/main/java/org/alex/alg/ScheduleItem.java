package org.alex.alg;

import java.util.Date;

public class ScheduleItem implements Comparable {
	private String carrier;
	private Date pickupTime;
	
	public ScheduleItem(Date pickupTime, String carrier) {
		this.pickupTime = pickupTime;
		this.carrier = carrier;
	}
	
	public String getCarrier() {
		return carrier;
	}
	
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	
	public Date getPickupTime() {
		return pickupTime;
	}
	
	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public int compareTo(Object o) {
		if (o instanceof ScheduleItem) {
			return (int) (getPickupTime().getTime() - ((ScheduleItem) o).getPickupTime().getTime());
		}
		
		return -1;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carrier == null) ? 0 : carrier.hashCode());
		result = prime * result
				+ ((pickupTime == null) ? 0 : pickupTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleItem other = (ScheduleItem) obj;
		if (carrier == null) {
			if (other.carrier != null)
				return false;
		} else if (!carrier.equals(other.carrier))
			return false;
		if (pickupTime == null) {
			if (other.pickupTime != null)
				return false;
		} else if (!pickupTime.equals(other.pickupTime))
			return false;
		return true;
	}

	public String toString() {
		return "ScheduleItem=[pickupTime=" 
				+ pickupTime 
				+ ", carrier="
				+ carrier
				+ "]";
	}
}
