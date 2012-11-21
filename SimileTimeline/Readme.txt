In order to compile and run the source code from within Eclipse you will have to define the 
classpath variable GWT_HOME. This must point at the directory where the GWT jars reside. 
E.g on my computer I place the GWT files in:

C:\Development\website\gwt\sdk\gwt-windows-1.4.61

So my GWT_HOME=C:/Development/website/gwt/sdk/gwt-windows-1.4.61

Pain in the arse, right? Trust me, you will thank me for this later when you have lots of 
GWT apps and you need to updgrade the toolkit version :)

HOWTO:

1) Open the project properties dialog.

2) Click on 'Java Build Path'.

3) Select the existing GWT_HOME reference and click 'Edit'

4) Click on 'Variable'

5) Click on 'Edit'

6) Set the path to your GWT jar directory.

