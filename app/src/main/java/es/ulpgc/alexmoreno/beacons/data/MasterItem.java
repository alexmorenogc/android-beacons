package es.ulpgc.alexmoreno.beacons.data;

/**
 * Class to control de Master Screen
 */
public class MasterItem {
    public final int id;
    public String name;
    public String uuid;

    public MasterItem(int id, String name, String uuid) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
    }

}
