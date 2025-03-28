FROM tomcat:9.0
COPY . /usr/local/tomcat/webapps/demo
EXPOSE 8080
