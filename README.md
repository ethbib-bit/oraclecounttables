# Count Tables for Oracle

Application that writes count from an Oracle into a text files. Output file location and queries can be configured in conf/tables_config.xml

## Application use

* Use startcount.sh to run application
* add queries to `conf/tables_config.xml`
  * you can use CDATA blocks in xml if you want to use special characters
  * `<![CDATA[SELECT COUNT(*) FROM INGEST_TRACKING_RECORD_E_PUB_Q WHERE SIP_STATUS<11;]]>`

## Current Version: 1.0.1

### History

### 1.01 (2019-07-18)

* create release
* minor fixes to path logic

### 1.00 (2019-02-18)

* implement xml readout
* implement Oracle connection
* write to file into defined directory
* Shell script for start in cron
