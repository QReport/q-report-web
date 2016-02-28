# q-report-web
Web control panel for managing tickets stored by QReport mod

##Installing & Configuring

There two possible way of installation:

#### As Forge modification
This method is recommended if you have only one Minecraft server running

You need to have Forgelin and QReport installed on your server

1. Download [QReport Web](https://github.com/QReport/q-report-web/releases/download/v1.2.0/qreport-web-v1.2.0.jar) without 'standalone' cliassifier`
2. Download [sl4j api](http://central.maven.org/maven2/org/slf4j/slf4j-api/1.7.18/slf4j-api-1.7.18.jar) from Maven Central (for some reasons we can't include it in main jar)
3. Put both downloaded jars in the `mods` directory along with q-report and forgelin

You can configure database path, login, password and port in `config/qreport-server.cfg` file after first start

#### As Standalone server

This method should be used when you have two or more servers with QReport ticket system, this web panel will collect tickets from all of them

1. Download [QReport Web standalone](https://github.com/QReport/q-report-web/releases/download/v1.2.0/qreport-web-v1.2.0-standalone.jar)
2. Put it anywhere you want
3. Run it using `java -jar qreport-web-v1.2.0-standalone.jar`

To configure it you must provided values with command argument. Available args: 
* `-url` - database url. E.g `-url jdbc:mysql://localhost:3306/qreport` or `-url jdbc:sqlite:/home/qreport/tickets.sqlite`
* `-login` - database login, set it to blank if no authorization required. E.g. `-login root`
* `-pass` - database password, set it to blank if no authorization required. E.g. `-pass password`
* `-port` - port, on which this web server will be running. E.g. `-port 4567`

##Usage
After starting this server you can access it throught browser on port which you've specified, by default it is 4567.
Default login is `root`, default password is `rainbow`
