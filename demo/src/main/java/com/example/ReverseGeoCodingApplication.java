package com.example;


import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class ReverseGeoCodingApplication
{

   public static void main(String[] args)
   {
      SpringApplication.run(ReverseGeoCodingApplication.class, args);
   }

}


@RestController
class ReverseGeoCodingRestController
{

   private static Logger logger = LogManager.getLogger();

   @Autowired
   GeoLookUpHistoryDao geoLookUpHistoryDao;
   RestTemplate restTemplate = new RestTemplate();


   @Value("${message}")
   private String msg;

   @RequestMapping("/message")
   public String welcomeMsg()
   {
      return this.msg;
   }

   @RequestMapping(value = "/getAddressedForGeoCoord", method = RequestMethod.GET)
   public String getAddressedForGeoCoord(@RequestParam("longitude") double longitude,
      @RequestParam("latitude") double latitude)
   {

      if (!(Double.toString(longitude).matches("[1-9][0-9]*(\\.[0-9]{1,2})?")))
         throw new ConstraintViolationException("Input Error, please enter valid longitude",
            Collections.emptySet());

      if (!(Double.toString(latitude).matches("[1-9][0-9]*(\\.[0-9]{1,2})?")))
         throw new ConstraintViolationException("Input Error, please enter valid latitude",
            Collections.emptySet());


      logger.info(" inSide getAddressedForGeoCoord: " + longitude + " ," + latitude);
      GeoLookUpHistory geoLookUpHistory = new GeoLookUpHistory();


      String URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + longitude + "," + latitude
         + "&key=AIzaSyAIRbPC9G3GsBaKBQ_3ydi8IOeNnpVYizI";

      logger.info("URL " + URL);

      ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(URL, String.class);

      System.out.print("responseEntity " + responseEntity);

      JSONObject geoObject = new JSONObject();
      geoObject = new JSONObject(responseEntity.getBody());


      String address = geoObject.getJSONArray("results").getJSONObject(0).getString("formatted_address");

      geoLookUpHistory.setLatitude(latitude);
      geoLookUpHistory.setLongitude(longitude);
      geoLookUpHistory.setAddress(address);
      geoLookUpHistory.setTimeStamp(new Date());

      geoLookUpHistoryDao.save(geoLookUpHistory);

      return address;

   }

   @RequestMapping(value = "/getGeolookUpHistory", produces = "application/json", method = RequestMethod.GET)
   public Collection<GeoLookUpHistory> getGeolookUpHistory()
   {
      return geoLookUpHistoryDao.findLastTenLookUps();

   }


}

@Repository("geoLookUpHistoryDao")
@RepositoryRestResource
interface GeoLookUpHistoryDao extends CrudRepository<GeoLookUpHistory, Long>
{

   GeoLookUpHistory findById(Long id);

   @SuppressWarnings("unchecked")
   GeoLookUpHistory save(GeoLookUpHistory lookUpHistory);


   @Query("select a from GeoLookUpHistory a")
   List<GeoLookUpHistory> findAll();

   @Query("select a from GeoLookUpHistory a order by a.timeStamp desc")
   List<GeoLookUpHistory> findLastTenLookUps();


}

