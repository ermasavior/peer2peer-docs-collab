package com.sister.app;

import com.sister.app.CRDT.CRDT;
import com.sister.app.VersionVector.VersionVector;

public class Controller {
    // bikin input array crdt langsung input version vectornya
    // counter dari node
    private CRDT crdt[];
    private Operation deleteBuffer[];
    private Operation operation[];
    private VersionVector versionVector[];

    public Controller(CRDT crdt[], Operation deleteBuffer[], Operation operation[], VersionVector versionVector[]) {
        this.crdt = crdt;
        this.deleteBuffer = deleteBuffer;
        this.operation = operation;
        this.versionVector = versionVector;
    }

    public Controller() {
        this.crdt = new CRDT[0];
        this.versionVector = new VersionVector[0];
        this.deleteBuffer = new Operation[0];
        this.operation = new Operation[0];
    }

    public void printCRDT(){
        System.out.println("<<< PRINT CRDT (site_id, value, position) >>>");
        for (int i = 0; i < this.crdt.length; i++) {
            System.out.println(this.crdt[i].site_id);
            System.out.println(this.crdt[i].value);
            System.out.println(this.crdt[i].position);
            System.out.println("----------");
        }
    }

    public void printDeleteBuffer(){
        System.out.println("<<< PRINT Delete Buffer (site_id, position, counter) >>>");
        for (int i = 0; i < this.deleteBuffer.length; i++) {
            System.out.println(this.deleteBuffer[i].site_id);
            System.out.println(this.deleteBuffer[i].position);
            System.out.println(this.deleteBuffer[i].counter);
            System.out.println("----------");
        }
    }

    public void printVersionVector(){
        System.out.println("<<< PRINT Version Vector (site_id, counter) >>>");
        for (int i = 0; i < this.versionVector.length; i++) {
            System.out.println(this.versionVector[i].site_id);
            System.out.println(this.versionVector[i].counter);
            System.out.println("----------");
        }
    }

    public void printOperation(){
        System.out.println("<<< PRINT Operation (site_id, value, type, position, counter) >>>");
        for (int i = 0; i < this.operation.length; i++) {
            System.out.println(this.operation[i].site_id);
            System.out.println(this.operation[i].value);
            System.out.println(this.operation[i].type);
            System.out.println(this.operation[i].position);
            System.out.println(this.operation[i].counter);
            System.out.println("----------");
        }
    }

    // Allocate array baru untuk updated app.CRDT
    public void copyCRDT(CRDT crdt[]) {
        this.crdt = new CRDT[crdt.length];
        for (int i = 0; i < crdt.length; i++) {
            this.crdt[i] = crdt[i];
        }
    }

    // Allocate array baru untuk updated version vector
    public void copyVersionVector(VersionVector vv[]) {
        this.versionVector = new VersionVector[vv.length];
        for (int i = 0; i < vv.length; i++) {
            this.versionVector[i] = vv[i];
        }
    }

    // Allocate array baru untuk updated delete buffer
    public void copyDeleteBuffer(Operation db[]) {
        this.deleteBuffer = new Operation[db.length];
        for (int i = 0; i < db.length; i++) {
            this.deleteBuffer[i] = db[i];
        }
    }

    // Allocate array baru untuk updated delete buffer
    public void copyOperation(Operation op[]) {
        this.operation = new Operation[op.length];
        for (int i = 0; i < op.length; i++) {
            this.operation[i] = op[i];
        }
    }

    // Melakukan pembaharuan Version Vector
    public void updateVersionVector(Operation op) {
        int i = 0;
        boolean found = false;
        if (this.versionVector.length != 0) {
            while (i < this.versionVector.length && !found) {
                if (op.site_id == this.versionVector[i].site_id) {
                    found = true;
                    this.versionVector[i].counter++;
                }
                i++;
            }
        }

        if (!found) {
            VersionVector versionVectorTemp[] = new VersionVector[i + 1];
            for(int j = 0; j < this.versionVector.length; j++) {
                versionVectorTemp[j] = this.versionVector[j];
            }
            versionVectorTemp[i] = new VersionVector(op.site_id, 1);
            copyVersionVector(versionVectorTemp);
        }
    }

    // Melakukan pembaharuan app.CRDT
    public void updateCRDT(Operation OP) {
        if (OP.type == "Insert") {
            CRDT tempCRDT[] = new CRDT[this.crdt.length + 1];
            for (int i = 0; i < this.crdt.length; i++) {
                if (i >= OP.position) {
                    this.crdt[i].position ++;
                    tempCRDT[i + 1] = this.crdt[i];
                } else {
                    tempCRDT[i] = this.crdt[i];
                }
            }
            tempCRDT[OP.position] = new CRDT(OP.site_id, OP.value, OP.position);
            copyCRDT(tempCRDT);
            updateVersionVector(OP);
        } else if (OP.type == "Delete") {
            CRDT tempCRDT[] = new CRDT[this.crdt.length - 1];
            for (int i = 0; i < this.crdt.length; i++) {
                if (i > OP.position) {
                    this.crdt[i].position --;
                    tempCRDT[i - 1] = this.crdt[i];
                } else if (i < OP.position){
                    tempCRDT[i] = this.crdt[i];
                }
            }
            copyCRDT(tempCRDT);
            updateVersionVector(OP);
        }
    }

    public void addOperation(Operation op) {
        Operation operationTemp[] = new Operation[this.operation.length + 1];
        for (int i = 0; i < this.operation.length; i++) {
            operationTemp[i] = this.operation[i];
        }
        operationTemp[this.operation.length] = op;
        copyOperation(operationTemp);
    }

    public void delOperation(Operation op) {
        Operation operationTemp[] = new Operation[this.operation.length - 1];
        int j = 0;
        for (int i = 0; i < this.operation.length; i++) {
            if (op.site_id == this.operation[i].site_id && op.value == this.operation[i].value && op.position == this.operation[i].position && op.type == this.operation[i].type && op.counter == this.operation[i].counter) {
                j++;
            }
            if(j < this.operation.length) {
                operationTemp[i] = this.operation[j];
            }
            j++;
        }
        copyOperation(operationTemp);
    }

    // Parameter operasi selalu 'Delete'
    public void addDeleteBuffer(Operation op) {
        Operation deleteBufferTemp[] = new Operation[this.deleteBuffer.length + 1];
        for (int i = 0; i < this.deleteBuffer.length; i++) {
            deleteBufferTemp[i] = this.deleteBuffer[i];
        }
        deleteBufferTemp[this.deleteBuffer.length] = op;
        copyDeleteBuffer(deleteBufferTemp);
        delOperation(op);
    }

    // Parameter operasi selalu 'Delete'
    public void delDeleteBuffer() {
        Operation deleteBufferTemp[] = new Operation[this.deleteBuffer.length - 1];
        for (int i = 0; i < this.deleteBuffer.length - 1; i++) {
            deleteBufferTemp[i] = this.deleteBuffer[i+1];
        }
        copyDeleteBuffer(deleteBufferTemp);
    }

    // Menyimpan Deletion Buffer yang digunakan untuk mempertahankan causality jika terjadi kasus di mana operasi delete seharusnya dilakukan belakangan belakangan dibanding insert terhadap suatu karakter di posisi tertentu, namun karena latency operasi delete masuk terlebih dahulu.
    // Setiap ketemu delete panggil ini
    // Menerapkan operasi delete pada Deletion Buffer jika sudah memenuhi syarat !isDeleteNotAllowed()
    public boolean isDeleteNotAllowed() {
        int i = 0;
        boolean found = false;
        while (i < this.operation.length && !found) {
            if(this.operation[i].type == "Insert") {
                boolean ada = false;
                int j = 0;
                while(j < this.versionVector.length && !ada){
                    if (this.operation[i].site_id == this.versionVector[j].site_id) {
                        ada = true;
                        if (this.operation[i].counter < this.versionVector[j].counter) {
                            return true;
                        }
                    }
                    j++;
                }
                if (!ada) {
                    return true;
                }
            }
            i++;
        }
        return found;
    }

    // Melakukan verifikasi operasi yang diterima terhadap app.CRDT dan Version Vector miliknya
    // Verifikasi position di app.CRDT
    // Verifikasi counter di Version Vector
    public boolean isVerified(Operation op) {
        // Max Position app.CRDT
        float max = 0;
        if (this.crdt.length != 0) {
            for(int i = 0; i < this.crdt.length; i++) {
                if (this.crdt[i].position > max) {
                    max = this.crdt[i].position;
                }
            }
            max++;
        }

        // Version Vector
        boolean verified = false;
        boolean found = false;
        int j = 0;
        while(j < this.versionVector.length && !found){
            if (op.site_id == this.versionVector[j].site_id) {
                found = true;
                if (op.counter == this.versionVector[j].counter + 1) {
                    verified = true;
                }
            }
            j++;
        }
        if (!found && op.counter == 1) {
            verified = true;
        }
        return (op.position <= max && verified);
    }

    public String CRDTToString() {
        String editorText = "";
        for(int i = 0; i < this.crdt.length; i++) {
            editorText += this.crdt[i].value;
        }
        return editorText;
    }

    //    Menerima notify operasi-operasi yang diterapkan ke text editor
    //    Menerapkan perubahan ke text editor

    //    Mengubah operasi-operasi ke dalam objek operasi yang siap dikirim oleh messenger
    //    Meminta messenger untuk mengirim operasi-operasi tersebut
    //    Menerima notify operasi dari messenger

    public static void main (String[] args) {
        System.out.println("COBAAA");
        CRDT crdt[] = new CRDT[0];
        VersionVector v[] = new VersionVector[0];
        Operation d[] = new Operation[0];
        Operation o[] = new Operation[0];

        Controller c = new Controller(crdt, d, o, v);

        int counter1 = 1;
        int counter2 = 1;

        c.addOperation(new Operation(1, 'K', "Insert", 0, counter1));
        counter1++;
        c.addOperation(new Operation(1, ' ', "Delete", 1, counter1));
        counter1++;
        c.addOperation(new Operation(2, 'O', "Insert", 1, counter2));
        counter2++;

//        c.updateCRDT(c.operation[0]);
//        c.delOperation(c.operation[0]);
//        c.updateCRDT(c.operation[1]);
//        c.delOperation(c.operation[1]);

//        if(c.operation[0].type == "Delete" && !c.isDeleteNotAllowed()) {
//            System.out.println("CIK");
//            c.updateCRDT(c.operation[0]);
//            c.delOperation(c.operation[0]);
//        } else {
//            System.out.println("CEK");
//            c.addDeleteBuffer(c.operation[0]);
//        }

        if (c.isVerified(c.operation[0])) {
            System.out.println("Verified");
            c.updateCRDT(c.operation[0]);
            c.delOperation(c.operation[0]);
        } else {
            System.out.println("Unverified");
        }

        c.printOperation();
        c.printCRDT();
        c.printVersionVector();
        c.printDeleteBuffer();
    }
}
