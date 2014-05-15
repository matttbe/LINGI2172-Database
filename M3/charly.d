TYPE U#       POSSREP {USERID INTEGER};
TYPE P#       POSSREP {PARKINGID INTEGER};
TYPE PASS#    POSSREP {PASSID INTEGER};
TYPE DATE     POSSREP {DATE CHAR};
TYPE DATETIME POSSREP {DATETIME CHAR};
TYPE TIME     POSSREP {TIME CHAR};

VAR USER          BASE RELATION {USERID U#, USERTYPE INTEGER, USERNAME CHAR, PASSWORD CHAR}  KEY {USERID};
VAR PARKING       BASE RELATION {PARKINGID P#, NAME CHAR, DEFIBRILLATOR BOOLEAN, FREEPLACES INTEGER, TOTALPLACES INTEGER, HEIGHT INTEGER}  KEY {PARKINGID};
VAR PASS          BASE RELATION {PASSID PASS#, DATESTART DATE, DATAEND DATE, USERID U#}  KEY {PASSID};
VAR CAR           BASE RELATION {NAME CHAR, HEIGHT INTEGER, FUEL CHAR, USERID U#, LATITUDE RATIONAL, LONGITUDE RATIONAL}  KEY {};
VAR HISTORY       BASE RELATION {USERID U#, DATETIMESTART DATETIME, DATETIMEEND DATETIME, PARKINGID P#}  KEY {USERID, DATETIMESTART, DATETIMEEND};
VAR PASSPARKING   BASE RELATION {PARKINGID P#, PASSID PASS#}  KEY {};
VAR LOCATION      BASE RELATION {PARKINGID P#, LATITUDE RATIONAL, LONGITUDE RATIONAL}  KEY {LATITUDE, LONGITUDE};
VAR ADDRESS       BASE RELATION {PARKINGID P#, STREET CHAR, NUMBER INTEGER, CITY CHAR, ZIP INTEGER, COUNTRY CHAR}  KEY {STREET, NUMBER, CITY, ZIP, COUNTRY};
VAR OPENINGHOURS  BASE RELATION {DAYSTART INTEGER, DAYEND INTEGER, HOURSTART CHAR, HOUREND CHAR, PARKINGID P#}  KEY {};
VAR HOURLYRATE    BASE RELATION {TIMESTART TIME, TIMEEND TIME, COST RATIONAL, PARKINGID P#}  KEY {TIMESTART, TIMEEND, COST, PARKINGID};
VAR FORBIDDENFUEL BASE RELATION {PARKINGID P#, FUEL CHAR}  KEY {PARKINGID, FUEL};

CONSTRAINT C1  PASS {USERID}    <= USER {USERID};
CONSTRAINT C2  CAR {USERID}     <= USER {USERID};
CONSTRAINT C3  HISTORY {USERID} <= USER {USERID};
CONSTRAINT C6  PASSPARKING {PASSID} <= PASS {PASSID};
CONSTRAINT C4  HISTORY {PARKINGID}       <= PARKING {PARKINGID};
CONSTRAINT C5  PASSPARKING {PARKINGID}   <= PARKING {PARKINGID};
CONSTRAINT C7  LOCATION {PARKINGID}      <= PARKING {PARKINGID};
CONSTRAINT C8  ADDRESS {PARKINGID}       <= PARKING {PARKINGID};
CONSTRAINT C9  OPENINGHOURS {PARKINGID}  <= PARKING {PARKINGID};
CONSTRAINT C10 HOURLYRATE {PARKINGID}    <= PARKING {PARKINGID};
CONSTRAINT C11 FORBIDDENFUEL {PARKINGID} <= PARKING {PARKINGID};
CONSTRAINT C12 IS_EMPTY (OPENINGHOURS{DAYSTART} WHERE (DAYSTART < 0 OR DAYSTART > 6));
CONSTRAINT C13 IS_EMPTY (OPENINGHOURS{DAYEND} WHERE (DAYEND < 0 OR DAYEND > 6));
CONSTRAINT C14 IS_EMPTY (PARKING WHERE FREEPLACES > TOTALPLACES);
CONSTRAINT C15 IS_EMPTY (PARKING{HEIGHT} WHERE HEIGHT < 0);
CONSTRAINT C16 IS_EMPTY (CAR{HEIGHT} WHERE HEIGHT < 0);
