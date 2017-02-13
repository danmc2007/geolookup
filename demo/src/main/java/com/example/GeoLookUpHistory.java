/*
 * © 2002-2017 AT&T Knowledge Ventures, L.P. All rights reserved.
 */
package com.example;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class GeoLookUpHistory implements Serializable
{


   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   protected long id;

   @Version
   protected int version;

   double latitude;
   double longitude;
   String address;

   Date timeStamp;


   public double getLatitude()
   {
      return latitude;
   }

   public void setLatitude(double latitude)
   {
      this.latitude = latitude;
   }

   public double getLongitude()
   {
      return longitude;
   }

   public void setLongitude(double longitude)
   {
      this.longitude = longitude;
   }

   public long getId()
   {
      return id;
   }

   public void setId(long id)
   {
      this.id = id;
   }

   public Date getTimeStamp()
   {
      return timeStamp;
   }

   public void setTimeStamp(Date timeStamp)
   {
      this.timeStamp = timeStamp;
   }

   public String getAddress()
   {
      return address;
   }

   public void setAddress(String address)
   {
      this.address = address;
   }


}
