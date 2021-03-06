package mk.fpvracing.osdtimer;

import java.util.Enumeration;
import java.util.HashSet;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello Timer");
    HashSet<CommPortIdentifier> ports = getAvailableSerialPorts();
    for (CommPortIdentifier commPortIdentifier : ports) {
      System.out.println(commPortIdentifier.getName());
    }
    
    
  }
  /**
   * @return    A HashSet containing the CommPortIdentifier for all serial ports that are not currently being used.
   */
  public static HashSet<CommPortIdentifier> getAvailableSerialPorts() {
      HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
      Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
      while (thePorts.hasMoreElements()) {
          CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
          switch (com.getPortType()) {
          case CommPortIdentifier.PORT_SERIAL:
              try {
                  CommPort thePort = com.open("CommUtil", 50);
                  thePort.close();
                  h.add(com);
              } catch (PortInUseException e) {
                  System.out.println("Port, "  + com.getName() + ", is in use.");
              } catch (Exception e) {
                  System.err.println("Failed to open port " +  com.getName());
                  e.printStackTrace();
              }
          }
      }
      return h;
  }
}
