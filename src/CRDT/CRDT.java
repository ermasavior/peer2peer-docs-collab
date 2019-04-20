package CRDT;

public class CRDT {
    public int site_id;
    public char value;
    public int position;

    public CRDT(int site_id, char value, int position, int counter) {
        this.site_id = site_id;
        this.value = value;
        this.position = position;
    }
}
