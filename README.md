# geolookup
Reverse geolookup restful service will return a formatted address for a given valid logitude and latitude.

it has two rest apis deployed on port 8110
/getAddressedForGeoCoord
/getGeolookUpHistory

this application can be run using mvn plugin:

mvn spring-boot:run or

java -jar target/demo-0.0.1-SNAPSHOT.jar



here is an example how to use it: 

http://localhost:8110/getAddressedForGeoCoord?longitude=33.969601&latitude=-84.100033

will return following address: 

2651 Satellite Blvd, Duluth, GA 30096, USA

and 

http://localhost:8110/getGeolookUpHistory

[
{
id: 15,
latitude: -84.100033,
longitude: 33.969601,
address: "2651 Satellite Blvd, Duluth, GA 30096, USA",
timeStamp: 1487001026376
},
{
id: 14,
latitude: -84.100033,
longitude: 33.969601,
address: "2651 Satellite Blvd, Duluth, GA 30096, USA",
timeStamp: 1487001000681
},
{
id: 13,
latitude: -84.100033,
longitude: 33,
address: "501-653 Community House Rd, Yatesville, GA 31097, USA",
timeStamp: 1487000983228
},
{
id: 12,
latitude: -84.100033,
longitude: 33.969601,
address: "2651 Satellite Blvd, Duluth, GA 30096, USA",
timeStamp: 1487000971713
},
{
id: 11,
latitude: -84.100033,
longitude: 33.969601,
address: "2651 Satellite Blvd, Duluth, GA 30096, USA",
timeStamp: 1487000562088
},
{
id: 10,
latitude: -84.100033,
longitude: 33.969601,
address: "2651 Satellite Blvd, Duluth, GA 30096, USA",
timeStamp: 1487000538056
}
]





