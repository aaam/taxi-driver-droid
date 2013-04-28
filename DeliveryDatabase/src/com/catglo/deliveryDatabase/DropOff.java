package com.catglo.deliveryDatabase;

import java.sql.Timestamp;
import java.util.Calendar;

public class DropOff {
	public int id;
	public int pickupId; //forign key
	public String address;
	public Timestamp time = new Timestamp(System.currentTimeMillis());
	public Float payment;
	public int paymentType;
	public Float meterAmount;
	public String account;
	public String authorization;
}
