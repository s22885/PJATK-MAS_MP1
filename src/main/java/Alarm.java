import io.github.coordinates2country.Coordinates2Country;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Alarm implements Serializable {
    private static List<Alarm> alarms = new ArrayList<>();

    private final ISensor alarmInfo;

    public Alarm(ISensor alarmInfo) {
        this.alarmInfo = alarmInfo;
        addMySelf();
    }

    public Alarm(String name) {
        this.alarmInfo = new AlarmInfo(name);
        addMySelf();
    }

    private void addMySelf() {
        alarms.add(this);
    }

    public String getInfo() {
        return alarmInfo.getInfo();
    }

    public static void showAlarmsInfo() {
        alarms.forEach(alarm -> System.out.println(alarm.getInfo()));
    }

    public static void saveAlarms(String path) {
        try (FileOutputStream f = new FileOutputStream(path);
             ObjectOutputStream o = new ObjectOutputStream(f);) {
            o.writeObject(alarms);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadAlarms(String path) {
        try (FileInputStream fi = new FileInputStream(path);
             ObjectInputStream oi = new ObjectInputStream(fi);) {
            alarms = (List<Alarm>) oi.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

class AlarmInfo implements ISensor,Serializable {
    private final String name;
    private boolean state;

    public AlarmInfo(String name) {
        this.name = name;
        this.state = false;
    }

    @Override
    public String getInfo() {
        var res = new StringBuilder();
        res.append("name: ").append(name).append("\ncurrent state: ").append(state);
        return res.toString();
    }

    public void setState(boolean state) {
        this.state = state;
    }


}

class AlarmInfoWeb extends AlarmInfo {
    private final String httpAddress;
    private final String ipAddress;

    public AlarmInfoWeb(String name, String httpAddress, String ipAddress) {
        super(name);
        this.httpAddress = httpAddress;
        this.ipAddress = ipAddress;
    }

    @Override
    public String getInfo() {
        var res = new StringBuilder(super.getInfo());
        res.append("\naddress http: ").append(httpAddress).append("\naddress ip: ")
                .append(ipAddress != null ? ipAddress : "does not exists");
        return res.toString();
    }
}

class AlarmInfoPhysical extends AlarmInfo {

    private final double coordinateX;
    private final double coordinateY;

    public AlarmInfoPhysical(String name, double coordinateX, double coordinateY) {
        super(name);
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    @Override
    public String getInfo() {
        var res = new StringBuilder(super.getInfo());
        var country = Coordinates2Country.country(coordinateX, coordinateY);
        res.append("\ncountry: ").append(country);
        return res.toString();
    }

}