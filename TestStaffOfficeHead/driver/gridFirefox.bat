java -Dwebdriver.gecko.driver=geckodriver.exe -jar selenium-server-standalone-3.0.1.jar -port 5557 -role node -hub http://localhost:4444/grid/register -browser "browserName=firefox, version=ANY, maxInstances=2"