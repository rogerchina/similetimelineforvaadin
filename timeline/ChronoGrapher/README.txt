ChronoGrapher Add-On and Demo 

ChronoGrapher is Vaadin implementation of BSD licensed SimileTimeline 
(http://www.simile-widgets.org/timeline/). Most java scripts are from 
SimileTimeline project (with some modifications). Some of the gwt client 
side code (also with some modifications) are from Apache 2.0 licensed 
gwtsimiletimeline project (https://code.google.com/p/gwtsimiletimeline/). 
Code from gwtsimiletimeline project is located under the package 
org.vaadin.chronographer.gwt.client.netthreads. This README file and
maven project configuration is adapted from corresponding files of 
FancyLayouts project by Sami Viitanen (https://github.com/alump/
FancyLayouts).

Source code: https://github.com/johannest/similetimelineforvaadin
Vaadin Directory: http://vaadin.com/directory#addon/chronographer
License: Apache License 2.0

Simple Maven tutorials:


***** How to compile add on jar package for your project *****

> cd chronographer-addon
> mvn package

add on can be found at: chronographer-addon/target/chronographer-addon-<version>.jar

***** How to install ChronoGrapher to your Maven repository *****

To install addon to your local repository, run:

> cd chronographer-addon
> mvn install


***** How to run test application *****

First compile and install addon (if not already installed)
> cd chronographer-addon
> mvn install

Then compile demo widgetset and start HTTP server
> cd ../chronographer-demo
> mvn vaadin:compile
> mvn jetty:run

Demo application is running at http://localhost:8080/chronographer


***** How to compile test application WAR *****

First compile and install addon (if not already installed)
> cd chronographer-addon
> mvn install

Then construct demo package (this should automatically compile widgetset)
> cd ../chronographer-demo
> mvn package

War package can be now found at chronographer-demo/target/ChronoGrapherDemo.war
