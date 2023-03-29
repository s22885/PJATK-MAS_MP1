public class Main {
    public static void main(String[] args) {
        if ( args.length != 0) {
            Alarm.loadAlarms("./data.ser");
            Alarm.showAlarmsInfo();
            System.out.println("kolejna próba \n");
            var ai1 = new AlarmInfoPhysical("fizyczny_czujnik_2", 55.1, 10.2);
            var a1 = new Alarm(ai1);
            Alarm.showAlarmsInfo();
        }else {
            System.out.println("start");
            Alarm.showAlarmsInfo();
            System.out.println();
            var ai1 = new AlarmInfoPhysical("fizyczny_czujnik_1", 50.1, 10.2);
            var a1 = new Alarm(ai1);
            Alarm.showAlarmsInfo();
            System.out.println("kolejna próba \n");
            var ai2 = new AlarmInfoWeb("internetowy_czujnik1", "www.wp.pl", null);
            var ai3 = new AlarmInfoWeb("internetowy_czujnik1", "www.wp.pl", "1.2.3.4");
            var a2 = new Alarm(ai2);
            var a3 = new Alarm(ai3);
            Alarm.showAlarmsInfo();
            Alarm.saveAlarms("./data.ser");
        }
    }
}