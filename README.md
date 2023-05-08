# Trip Finder

The Trip Finder application reads a list of tap-ons and tap-offs from a csv file, and calculates the costs of each trip. Finally it writes the trips to a csv file.

### Sample Input CSV File Containing Taps
```
ID,DateTimeUTC,TapType,StopId,CompanyId,BusID,PAN
1,22-01-2018 13:00:00,ON,Stop1,Company1,Bus37,5500005555555559
2,22-01-2018 13:05:00,OFF,Stop2,Company1,Bus37,5500005555555559
```
### Sample Output CSV File Containing Trips
```
Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status
22-01-2018 13:00:00,22-01-2018 13:05:00900,Stop1,Stop2,$3.25,Company1,B37,5500005555555559,COMPLETED
```

## Requirements

For building and running the application you need:

- [JDK 20](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running The Application

To run this application, go to the project directory and run the following commands:

#### Setup
```shell
mkdir -p /tmp/tripfinder/taps
mkdir -p /tmp/tripfinder/trips
cp -r src/main/resources/taps/* /tmp/tripfinder/taps
```

#### Generate Trips
```shell
mvn spring-boot:run -Dspring-boot.run.arguments=--file.name=2-mult-taps-mixed-trips.csv
cat /tmp/tripfinder/taps/2-mult-taps-mixed-trips.csv
cat /tmp/tripfinder/trips/2-mult-taps-mixed-trips.csv
```

#### Generate Trips For Multiple Passengers
```shell
mvn spring-boot:run -Dspring-boot.run.arguments=--file.name=3-multi-taps-mix-trips-multi-passengers.csv
cat /tmp/tripfinder/taps/3-multi-taps-mix-trips-multi-passengers.csv
cat /tmp/tripfinder/trips/3-multi-taps-mix-trips-multi-passengers.csv
```

#### Generate Trips For Non Sequential Taps 
```shell
mvn spring-boot:run -Dspring-boot.run.arguments=--file.name=4-3-multi-taps-mix-trips-multi-passengers-non-seq.csv
cat /tmp/tripfinder/taps/4-3-multi-taps-mix-trips-multi-passengers-non-seq.csv
cat /tmp/tripfinder/trips/4-3-multi-taps-mix-trips-multi-passengers-non-seq.csv
```

#### Generate Trips For Your Sample 'mysample.csv'
```shell
cp <path-to-your-file>mysample.csv /tmp/tripfinder/taps
mvn spring-boot:run -Dspring-boot.run.arguments=--file.name=mysample.csv
cat /tmp/tripfinder/trips/mysample.csv 
```

## Testing The Application
```shell
mvn test
```

## Assumption
- The duration between tap-on and tap-off is excluded to reduce complexity. For example, a tap-on on `22-01-2022` and tap-off on `26-01-2022` will be considered a completed trip.
- It is assumed that there won't be any tap-off without a preceding tap-on.
- For multiple tap-ons at the same stop, only the first tap will be considered.
- For multiple tap-offs at the same stop, only the first tap will be considered.
- It is assumed that leading spaces in sample input/output files are added for readability. Since this is not a standard practice, spaces will be omitted in output files. For example, instead of `Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status` the header of the output file will be `Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status`.
- A tap-on and tap-off are considered a completed trip when CompanyId, BusId, and PAN are the same.
- Different cards are considered as different passengers.
- The same fare is assumed for all companies.

## Future Plan
Following are considered for future enhancement:
- Add unit tests for TapReader and TapWriter
- Add integration tests for TapToTripCsvGenerator.java
