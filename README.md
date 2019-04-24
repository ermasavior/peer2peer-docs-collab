# Tugas Besar 
# IF3230 Sistem Paralel dan Terdistribusi
# CRDT Collaborative Editing

### 13516036	Ilma Alifia Mahardika
### 13516072	Erma Safira Nurmasyita
### 13516099    Raka Hadhyana

---

CRDT Collaborative Editing adalah aplikasi text editor sederhana yang menggunakan peer-to-peer sehingga dapat melakukan edit dokumen secara bersamaan.

## Cara menjalankan program
1. Import project pada IDE Intellij.
2. Jalankan program Main pada package `com.sister`.
3. Text editor siap dipakai.

## Cara Kerja Program
Setiap pengguna mengetikkan karakter atau menghapus karakter, messenger akan melakukan broadcast operasi ke semua node. Ketika node menerima pesan broadcast, pesan yang berupa operasi tersebut akan di-apply menjadi CRDT. Kemudian GUI akan menampilkan teks sesuai CRDT.

## Arsitektur Program
Program terdiri dari dua package utama, yaitu `app` dan `gui`. Package `app` terdiri dari kelas CRDT, VersionVector, Controller, Operation, MainApp, dan Messenger. Package `gui` terdiri dari GUIController dan layout EditorWindow dengan format `.fxml`.

## Fungsi dan Desain Struktur Data
1. CRDT: site_id (integer), value (char), position (integer) <br> Fungsi: menyimpan karakter beserta indeks posisinya pada text editor.
2. VersionVector: site_id (integer), counter (integer) <br>
Fungsi: menangani konflik pada urutan operasi akibat latency.
3. Deletion Buffer: site_id (integer), value (char), position (integer), counter (char) <br> Fungsi: menyimpan operasi delete yang belum dijalankan untuk mengatasi konflik.

## Analisis Solusi
Pada program ini, komputer harus dalam satu jaringan karena program ini menggunakan multicast. Untuk solusi lebih baik, dapat dilakukan antar jaringan.

## Kasus Uji dan Screenshot
Terdapat pada folder img

## Pembagian Tugas
| NIM      | Nama                   | Deskripsi Tugas                        | Persentase |
|----------|------------------------|----------------------------------------|------------|
| 13516036 | Ilma Alifia Mahardika  | Membuat Controller, membuat laporan    | 33.3%      |
| 13516072 | Erma Safira Nurmasyita | Membuat GUI & MainApp, membuat laporan | 33.3%      |
| 13516099 | Raka Hadhyana          | Membuat Messenger, membuat laporan     | 33.3%      |



