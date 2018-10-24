#Tiene que agregar al proyecto de Netbeans:
# primero que nada esta carpeta scripts dentro del proyecto
# la carpeta bin, 
# la carpeta build
# la carpeta WSClienteSA dentro de build
# el archivo README en la capeta principal
cd ../build/WSClienteSA/
cp  ../../web/* .

mkdir -p WEB-INF/classes
cd WEB-INF/classes
wsimport -keep -p servicios http://127.0.0.1:8580/ServicioU?wsdl http://127.0.0.1:8580/ServicioC?wsdl http://127.0.0.1:8580/ServicioP?wsdl

cd ../../
javac -cp "/usr/share/tomcat8/lib/servlet-api.jar:WEB-INF/classes" -d WEB-INF/classes/ ../../src/java/Servlets/*.java

jar cvf ../../bin/WSClienteSA.war .
#opcional: deploy directo de la aplicación en tomcat
#cp -R ../../build/WSClienteSA /var/lib/tomcat8/webapps/WSClienteSA

