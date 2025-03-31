/**************************************************************************
 * Program description: model for storing each row of the csv
 * Goals:
 *      - make it easier to organize and access the features fr
 *      - fingers crossed hehe 
 * Date: 27/03/25
 * Author: Renee Low
 ************************************************************************/

public class Features {
    private String powerStatus; // f1
    private String networkSignal; // f2
    private String activity; // f3
    private String backgroundProcesses; // f4
    private String DeviceIsOnline; // label

    // Constructor
    public Features(String powerStatus, String networkSignal, String activity, String backgroundProcesses, String DeviceIsOnline) {
        setPowerStatus(powerStatus);
        setNetworkSignal(networkSignal);
        setActivity(activity);
        setBackgroundProcesses(backgroundProcesses);
        setDeviceIsOnline(DeviceIsOnline);
    }

    // setters
    public void setPowerStatus(String powerStatus) {
        this.powerStatus = powerStatus;
    }
    public void setNetworkSignal(String networkSignal) {
        this.networkSignal = networkSignal;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }
    public void setBackgroundProcesses(String backgroundProcesses) {
        this.backgroundProcesses = backgroundProcesses;
    }
    public void setDeviceIsOnline(String deviceIsOnline) {
        DeviceIsOnline = deviceIsOnline;
    }

    // getter
    public String getPowerStatus() {
        return powerStatus;
    }
    public String getNetworkSignal() {
        return networkSignal;
    }
    public String getActivity() {
        return activity;
    }
    public String getBackgroundProcesses() {
        return backgroundProcesses;
    }
    public String getDeviceIsOnline() {
        return DeviceIsOnline;
    }

    public String toString() {
        return powerStatus + ", " + networkSignal + ", " + activity + ", " + backgroundProcesses + ", " + DeviceIsOnline;
    }
}
