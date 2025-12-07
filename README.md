# Environmental Data

[Deutsch](#deutsch) | [English](#english)
## Deutsch

### 📖 Beschreibung
Bei EnvironmentalData handelt es sich um eine kleine Java Applikation, um API Endpunkte von einer
bestimmten NOAA Wetter Station und von einer Messtation der USGS zu testen. Die Erfahrungen in zusammenhang mit diesen
Endpunkten soll dienen, um die Implementierung dieser Endpunkte im Repository CrocoManager zu vereinfachen.

### ⚙️ Funktionsumfang
- Abrufen von spezifischer NOAA Wetterstation in Florida
- Abrufen von Messdaten einer USGS Station aus Florida
- Anzeigen der Informationen in einem Terminal

###  ️📬 API-Schnittstelle

#### 🍃 NOAA
```
https://api.weather.gov/stations/KTMB/observations/latest
```

#### 💧 USGS
```
https://waterservices.usgs.gov/nwis/iv/?format=json&sites=251457080395802&parameterCd=00010,00095&period=PT2H
```

#### 📃 API-Docs
- USGS: https://waterservices.usgs.gov/docs/instantaneous-values/instantaneous-values-details/
- NOAA: https://www.weather.gov/documentation/api/point

### Installation
In Bearbeitung

## English
[German](#deutsch) | [English](#english)
### 📖 Description
EnvironmentalData is a small Java application for testing API endpoints from a
specific NOAA weather station and a USGS measuring station. The experience gained in connection with these
endpoints should serve to simplify the implementation of these endpoints in the CrocoManager repository.

### ⚙️ Range of functions
- Retrieving specific NOAA weather station in Florida
- Retrieve measurement data from a USGS station in Florida
- Display the information in a terminal

###  ️📬 API interface

#### 🍃 NOAA
```
https://api.weather.gov/stations/KTMB/observations/latest
```

#### 💧 USGS
```
https://waterservices.usgs.gov/nwis/iv/?format=json&sites=251457080395802&parameterCd=00010,00095&period=PT2H
```

#### 📃 API Docs
- USGS: https://waterservices.usgs.gov/docs/instantaneous-values/instantaneous-values-details/
- NOAA: https://www.weather.gov/documentation/api/point

### Installation
In progress