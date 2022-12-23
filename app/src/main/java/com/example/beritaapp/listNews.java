package com.example.beritaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listNews extends AppCompatActivity {
    private RecyclerView recyclerview;
    private ArrayList<News> news_all = new ArrayList<>(); // menyimpan semua berita
    private newsAdapter adapter;
    private ArrayList<News> filter_news = new ArrayList<>(); // menyimpan berita berdasarkan inputan user

    String user_tag, user_email;
    int user_age;

    public static String key;

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Intent intent_detail = getIntent();
        user_age = intent_detail.getIntExtra(DetailUser.MESSAGE_AGE, 1);
        user_tag = intent_detail.getStringExtra(DetailUser.MESSAGE_TAG);
        user_email = intent_detail.getStringExtra("USER");

        showData();
        recyclerview = findViewById(R.id.rv_news);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_addData = new Intent(getApplicationContext(), InsertData.class);
                go_to_addData.putExtra("USER", user_email);
                go_to_addData.putExtra("KEY", key);
                startActivity(go_to_addData);
            }
        });
    }

    private void showData() {
        mDatabaseReference.child("News").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    key = item.getKey();
                    News item_news = item.getValue(News.class);
                    news_all.add(item_news);
                }
                for(News a: news_all) {
                    if (a.getTag().equals(user_tag) && a.getMinAge() <= user_age) {
                        findViewById(R.id.none).setVisibility(View.GONE);
                        filter_news.add(a);
                    }
                    else{
                        TextView info = findViewById(R.id.none);
                        info.setText("Tidak ada berita yang sesuai dengan anda");
                    }
                }
                adapter = new newsAdapter(listNews.this, filter_news);
                recyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void addData() {
//        news_all.add(new News("Tempat Wisata di Jogja yang Cocok Untuk Anak", "Hiburan", "- Lembah Rubuh\n" + "- Ice Cream World Jogja\n" + "- Kidzoona\n" + "- Desa Wisata Kasongan\n" + "- Museum and Factory by Chocolate Monggo\n" + "- Desa Wisata Brayut", 10));
//        news_all.add(new News("Rekomendasi Channel Youtube Untuk Anak", "Hiburan", "- Pinkfong! Kids’ Songs & Stories\n" + "- Nussa Official\n" + "- Amy Poehler’s Smart Girls\n" + "- Disney Junior\n" + "- Cocomelon", 6));
//        news_all.add(new News("Film Horor Indonesia yang Tayang Oktober", "Hiburan","Film horor masih menjadi salah satu genre yang paling diminati oleh masyarakat Indonesia. Semakin berkembangnya zaman, industri perfilman Indonesia semakin populer dan berkualitas, " +
//                "contohnya film Pengabdi Setan 2017 lalu. Berikut merupakan rekomendasi film horor yang akan tayang bulan Oktober" + "- Pamali\n" + "- Inang\n" + "- Kalian Pantas Mati\n" + "- Hidayah\n" + "- Qodrat", 15));
//        news_all.add(new News("Rekomendasi Pantai di Gunung Kidul", "Hiburan", "- Pantai Indrayanti\n" +
//                "- Pantai Pok Tunggal\n" + "- Pantai Drini\n" + "- Pantai Sadranan\n" + "- Pantai Krakal\n" + "- Pantai Ngandong\n" + "- Pantai Gesing\n" + "- Pantai Slili", 18));
//        news_all.add(new News("Tempat Wisata di Semarang yang Cocok Untuk Anak", "Hiburan", "- Cimory on the Valley\n" + "- Taman Bunga Celosia Bandungan\n" + "- Dusun Semilir\n" + "- Museum Kereta Api Ambarawa\n" + "- Old City 3D Trick Art Museum\n" +
//                "- Kampung Batik\n" + "- Puri Maerokoco", 13));
//        news_all.add(new News("Rekomendasi Wisata Ekstrim di Jogja", "Hiburan", "- Gondola Pantai Timang\n" + "- Cave Tubing Goa Pindul\n" + "- Sandboarding Parangkusumo\n" + "- Gua Jombang\n" +
//                "- Lava Tour Merapi\n" + "- Rafting Sungai Progo\n" + "- Cliff Swing Gunung Api Purba\n" + "- Jembatan Gantung Pantai Sinden", 22));
//        news_all.add(new News("Cafe Istagrammable di Jogja", "Hiburan", "Jogja terkenal sebagai kota wisata, tidak hanya wisata alamnya, tapi juga kafenya. Jumlah cafe di Jogja memang sangat banyak. Bahkan hampir di setiap ruas jalan kamu dapat menjumpai cafe tersebut. " +
//                "Berikut rekomendasi cafe yang instagrammable : \n- Carrol Kitchen\n" + "- Oppio Coffee Milk\n" + "- Kalluna\n" + "- Sepakat Kopi\n" + "- Unikologi\n" + "- Cafe Brick\n" + "- Blanco Coffee and Books", 14));
//        news_all.add(new News("Rekomendasi Gunung untuk Pendaki Pemula", "Hiburan", "- Gunung Andong, Jawa Tengah\n" + "- Gunung Prau, Jawa Tengah\n" + "- Gunung Gede, Jawa Barat\n" + "- Gunung Papandayan, Jawa Barat\n" +
//                "- Gunung Ijen, Jawa Timur\n" + "- Gunung Penanggungan, Jawa Timur\n" + "- Gunung Batur, Bali", 20));
//        news_all.add(new News("Rekomendasi SPA di Jogja", "Hiburan", "- de WAVE Spa Massage and Reflexology\n" + "- Griya Shiatsu\n" + "- Laseca Salon and Spa\n" + "- Jogja Traditional Treatment\n" + "- Nurkadhatyan Spa\n" +
//                "- The Healing Touch Nakamura Holistic Teraphy\n" + "- Tjan Spa\n" + "- Kakiku Family Health and Beauty", 21));
//        news_all.add(new News("Makanan Khas Jogja", "Hiburan", "- Saoto Batok Mbah Katro\n" + "- Oseng Mercon Bu Narti\n" + "- Soto Sampah\n" + "- Gudeg Bromo\n" + "- Gudeg Mbah Lindu Sosrowijayan\n" +
//                "- Gudeg Yu Djum\n" + "- Angkringan Pak Jabrik\n" + "- Sate Klatak Pak Pong", 16));
//

//        news_all.add(new News("Atlet PON Mengonsumsi Dopping", "Olahraga", "Lembaga Anti-Doping Indonesia (IADO) pada Jumat (14/10/2022) mengumumkan sebanyak lima atlet yang berlaga pada Pekan Olahraga Nasional (PON) Papua 2021 terbukti positif doping " +
//                "berdasarkan tes yang dilakukan selama pesta olahraga empat tahunan itu. Lima atlet tersebut kedapatan positif doping setelah IADO melakukan pengetesan terhadap 718 atlet dari total 7.038 atlet yang mengikuti PON Papua pada 2-15 Oktober tahun lalu.", 18));
//        news_all.add(new News("Kejurprov NPCI Jawa Tengah", "Olahraga", "Delapan cabang olahraga disabilitas bakal digelar dalam Kejuaraan Provinsi (Kejurprov) National Paralympic Committee Indonesia (NPCI) Jawa Tengah 2022. Persaingan dalam delapan " +
//                "cabang olahraga yang diikuti para atlet NPCI kota/kabupaten se-Jawa Tengah tersebut rencananya dipusatkan di Solo, 25-27 Oktober mendatang.", 7));
//        news_all.add(new News("Mempertahankan daya tahan tubuh", "Olahraga", "Endurance atau daya tahan tubuh merupakan hal penting yang harus diperhatikan setiap individu bila ingin mampu melakukan aktivitas sehari-hari dengan performa terbaik. Daya tahan " +
//                "tubuh yang baik membantu kita untuk fokus lebih lama dan tubuh tidak mudah lelah. Beberapa cara menjaga daya tahan tubuh, yaitu : bersepeda, jongging, yoga, dan skipping. ", 20));
//        news_all.add(new News("Jokowi Respons Ancaman STY Mundur: Jangan ke Mana-mana Dulu", "Olahraga", "Presiden Joko Widodo (Jokowi) merespons ancaman Shin Tae Yong yang siap mundur jika Ketua Umum PSSI Mochamad Iriawan atau Iwan " +
//                "Bule mundur sebagai bentuk tanggung jawab Tragedi Kanjuruhan.\n" +
//                "Jokowi meminta Shin Tae Yong bersabar karena dirinya mengaku belum mendapat laporan dari Tim Gabungan Independen Pencari Fakta (TGIPF) Tragedi Kanjuruhan.", 10));
//        news_all.add(new News("Kemenpora Melakukan Senam Rutin", "Olahraga", "Menuju Indonesia bugar di  tahun  2045, Kementerian Pemuda dan Olahraga Republik Indonesia (Kemenpora RI) terus melaksanakan agenda rutin senam di hari Krida. " +
//                "Senam ini dilaksanakan di halaman Kantor Kemenpora, Jumat (14/10).", 13));
//        news_all.add(new News("Kazakhstan Ingin Belajar Pencak Silat dari Indonesia", "Olahraga", "Dubes Fadjroel yang juga merupakan Penasehat Asosiasi Pencak Silat Kazakhstan ini meyakini bahwa Pencak Silat merupakan jalan untuk membangun hubungan baik antara Indonesia dan Kazakhstan. Menurutnya bahwa Masyarakat Kazakhstan " +
//                "sangat menggemari olahraga bela diri, oleh karena itu Pencak Silat merupakan salah satu pengikat persahabatan antara Indonesia dengan Kazakhstan.", 14));
//        news_all.add(new News("Olahraga Untuk Mengecilkan Perut Buncit", "Olahraga", "- Lari\n" + "- Kickboxing\n" + "- Boxing\n" + "- Bersepeda\n" + "- Renang\n" + "- Basket\n" + "- High intensity interval training (HIIT)", 21));
//        news_all.add(new News("Olahraga Untuk Membentuk ABS", "Olahraga", "- Bent over rows\n" + "- Bicycle crunch\n" + "- Hanging leg raises\n" + "- Crunch\n" + "- Deadlift\n" + "- Fitness ball rollouts\n" + "- Seated cable rows", 22));
//
//        news_all.add(new News("Partai Politik Lolos Verifikasi", "Politik", "Total, 18 partai politik calon peserta Pemilu 2024 dinyatakan lolos. Kemudian sebanyak 6 partai politik calon peserta Pemilu 2024 lainnya gugur. Partai tersebut, yaitu : " +
//                "PPP, PKB, PDI Perjuangan, artai Nasdem, Partai Demokrat, PAN, Partai Gerindra, PSI, Partai Golkar,  Perindo,  PKN, PKS, Partai Gelora Indonesia, PBB, Partai Hanura, Partai Ummat, Partai Buruh, Partai Garuda", 12));
//        news_all.add(new News("Keterlibatan Perempuan dalam Pemilu", "Politik", "\"Tren pencalonan keterwakilan perempuan terus meningkat dalam pemilu, di Kota Semarang pada Pemilu 2009 keterlibatan perempuan di angka 31,8 persen, pada 2014 menjadi " +
//                "37,4 persen dan kembali naik menjadi 40 persen pada Pemilu 2019,\" ujar wanita yang akrab Tia ini." + "Tia menuturkan, semakin tinggi keterwakilan perempuan, semakin kuat pengaruhnya dalam hal membuat kebijakan.", 13));
//        news_all.add(new News("Presiden Irak", "Politik", "\n" +
//                "Abdul Latif Rashid pada Kamis (13/10/2022) terpilih sebagai presiden baru Irak di tengah kebuntuan politik setelah pemilihan nasional pada Oktober tahun lalu. Rashid kemudian menunjuk Mohammed Shia al-Sudani sebagai perdana menteri." +
//                "Kepresidenan, yang secara tradisional diduduki oleh orang Kurdi, sebagian besar merupakan posisi seremonial. Tetapi pemungutan suara untuk Rashid adalah langkah kunci menuju pembentukan pemerintahan baru, yang gagal dilakukan oleh para politisi sejak pemilihan.  ", 15));
//        news_all.add(new News("Arab Saudi Pangkas Target Produksi Minyak", "Politik", "Arab Saudi membantah pernyataan Amerika Serikat yang menuding keputusan OPEC+ memangkas target produksi minyak karena faktor politik.\n" +
//                "Mengutip Reuters, Kamis (13/10), Kementerian Luar Negeri Arab Saudi menyatakan OPEC+ memangkas target produksi minyak untuk melindungi kepentingan konsumen dan produsen.", 17));
//        news_all.add(new News("KPU Kerja Sama dengan AMAN", "Politik", "Komisi Pemilihan Umum (KPU) mendukung pelaksanaan Kongres Masyarakat Adat Nusantara (KMAN) VI yang akan diselenggarakan di wilayah adat Tabi, Jayapura, Papua, pada Senin (24/10/2022) hingga Minggu (30/10/2022).\n" +
//                "Ketua KPU Hasyim Asy'ari mengatakan, dukungan dari KPU tersebut untuk menjalin kerja sama dengan Aliansi Masyarakat Adat Nusantara ( AMAN) dalam pemenuhan hak politik masyarakat adat di Pemilihan Umum (Pemilu) 2024 mendatang.", 18));
//        news_all.add(new News("Demokrasi dan Ruang Politik Masyarakat Sipil", "Politik", "Apa tantangan yang harus dihadapi dalam meningkatkan kualitas demokrasi Indonesia? Seberapa besar kemampuan mengelola masyarakat majemuk dalam membangun politik kewargaan? " +
//                "Apa agenda strategis yang perlu dirumuskan dan ditempuh?" + "Pertanyaan itu diajukan sebagai bahan refleksi tentang situasi demokrasi dan demokratisasi yang berlangsung di Indonesia, yang kira-kira telah berusia lebih dari dua dekade sejak reformasi 98 dicanangkan.", 19));
//        news_all.add(new News("Politik Dagang Sapi", "Politik", "Wakil Ketua Komisi II DPR Saan Mustopa mewanti-wanti praktik politik dagang sapi yang bisa muncul andai pemilihan kepala daerah (pilkada) dikembalikan ke DPRD." +
//                "Menurut Saan, dalih pilkada langsung yang saat ini diterapkan memunculkan praktik korupsi yang meluas di daerah belum bisa dibuktikan. Selain itu, pilkada lewat DPRD juga tak menjamin akan bebas dari transaksional.", 20));
//        news_all.add(new News("Hasil Kajian Soal Politik Identitas", "Politik", "Peserta Program Pendidikan Reguler Angkatan (PPRA) 63 dan 64 Lembaga Pertahanan Nasional (Lemhanas) RI bertemu dengan Presiden Jokowi di Istana Negara, Jakarta Pusat, Rabu, " +
//                "12 Oktober 2022. Gubernur Lemhanas RI Andi Widjajanto menyebut dalam pertemuan itu pihaknya " +
//                "menyerahkan hasil kajian beberapa isu kepada Jokowi. PPRA 63 dan 64 sama-sama memberikan hasil kajian mereka yang sudah diseminarkan, PPRA 63 memberikan kajian mengenai konsolidasi demokrasi.", 22));
//        news_all.add(new News("PAN Tak Ikut Campur Resuffle", "Politik", "PAN membatasi diri untuk tidak ikut mencampuri urusan reshuffle kabinet. Termasuk, PAN disebut tak mencampuri jika rencana reshuffle kali ini dipersepsikan sebagai sebab akibat Nasdem " +
//                "karena telah mendeklarasikan Anies Baswedan sebagai calon presiden (capres)." + "PAN membatasi diri tidak ikut campur soal reshufle kabinet, menjaga fatsun dan etika politik,", 21));
//
//        news_all.add(new News("Daftar Kosmetik Berbahaya Temuan BPOM", "Kecantikan", "BPOM RI menemukan produk kosmetik yang mengandung bahan berbahaya. Berikut ini Daftar Kosmetik Berbahaya Temuan BPOM : " +
//                "- Madame Gie Sweet Cheek Blushed 03\n" + "- Madame Gie Nail Shell 14\n" + "- Madame Gie Nail Sheil 10\n" + "- Casandra Lip Balm Care With Aloe Vera\n" + "- Casandra Lip Balm Magic\n" + "- LOVES ME Keep Color Trio Eyeshadow LM3044 04\n" + "- LOVES ME The Matte Eyeshadow LM3016 02\n" +
//                "- LOVES ME The Matte Eyeshadow LM3022 04", 19));
//        news_all.add(new News("Tanda Rambut Rontok Tdak Wajar", "Kecantikan", "Spesialis dermatologi Dr. Janet Allenby menjelaskan bahwa pada dasarnya rambut rontok suatu hal yang wajar. Namun, ada kala-kala tertentu wanita mengalami rambut rontok yang tidak wajar. Kenali tandanya, yaitu :\n" +
//                "- Banyak rambut di bantal di pagi hari\n" + "- Sisir rambut satu menit, terdapat lebih dari 10 helai rambut rontok\n" + "- Belahan rambut lebih lebar\n" + "- Kulit kepala makin nyata\n" + "- Ikatan rambut semakin tipis\n" + "- Kumpulan rambut di sisir sikat terlalu banyak\n" +
//                "- Rontok meski hanya disisir dengan jari", 12));
//        news_all.add(new News("Cara Menghilangkan Milia di Wajah", "Kecantikan", "Milia muncul di kulit wajah berupa bintik atau benjolan kecil-kecil berwarna putih. Biasanya benjolan ini muncul lebih dari satu dan tumbuh di sekitar hidung dan bawah mata. Berikut ini cara menghilangkan milia : \n" +
//                "- Retinoid topikal merupakan krim kulit yang mengandung vitamin A tinggi\n" + "- Pengelupasan kimia\n" + "- Krioterapi berupa nitrogen cair yang dapat membekukan milia", 18));
//        news_all.add(new News("Makanan Penambah Berat Badan", "Kecantikan", "Untuk meningkatkan kebutuhan kalori, seseorang perlu mengonsumsi makanan tinggi kalori yang bisa membantu menambah berat badan. Makanan tersebut, yaitu :\n" +
//                "- Nasi\n" + "- Kacang - kacangan dan selai kacang\n" + "- Daging Merah\n" + "- Pati\n" + "- Salmon\n" + "- Buah kering\n" + "- Roti gandum utuh\n" + "- Alpukat\n" + "- Telur\n" + "- Coklat hitam", 13));
//        news_all.add(new News("Surabaya Fashion Week 2022", "Kecantikan", "Produsen kecantikan \"KLT New\" mengajak masyarakat turut berperan menyukseskan kegiatan peragaan busana \"Surabaya Fashion Parade\" yang dijadwalkan berlangsung 5 - 9 Oktober 2022." +
//                "KLT New menjadi salah satu sponsor resmi dalam ajang peragaan busana bergengsi yang rutin digelar setiap tahun di Kota Pahlawan. KLT NEW secara eksklusif bekerja sama dengan Pakuwon Group siap merealisasikan seluruh rangkaian kegiatan Surabaya Fashion Parade " +
//                "yang dipusatkan di Tunjungan Plaza Surabaya.", 11));
//        news_all.add(new News("Perawatan Wajah untuk Remaja", "Kecantikan", "Perawatan kulit remaja tidak dapat disamakan dengan orang dewasa. Pasalnya, perubahan hormon saat menginjak usia remaja membuat kelenjar minyak pada kulit lebih aktif sehingga berisiko menimbulkan berbagai masalah kulit, " +
//                "seperti kulit berminyak, pori-pori besar, sampai jerawat. Perawatan remaja yang disarankan, yaitu :\n" +
//                "- Cuci muka secara rutin\n" + "- Hindari sabun cuci muka mengandung scrub\n" + "- Oleskan pelembap wajah\n" + "- Gunakan sunscreen atau tabir surya SPF 30", 15));
//        news_all.add(new News("Industri Kecantikan Tidak Terpengaruh Resesi", "Kecantikan", "Pelaku industri kecantikan optimistis tidak terpengaruh oleh ancaman resesi global yang diprediksi terjadi tahun 2023 mendatang. Terlebih, pangsa pasar yang besar membuat industri kecantikan juga " +
//                "terus bertumbuh. Beragam merek kosmetik, baik lokal maupun mancanegara, saat ini banyak ditemukan di pasaran. Selain karena kebutuhan tampil cantik, produk perawatan kecantikan yang terjangkau, juga menjadi kunci industri kecantikan tak terpengaruh resesi.", 17));
//        news_all.add(new News("Manfaat Infused Water Untuk Kecantikan", "Kecantikan", "Infused water adalah air mineral yang dicampurkan dengan buah-buahan segar, sayuran, hingga rempah-rempah. Manfaat dari Infused Water, yaitu :\n" +
//                "- Membantu Menurunkan Berat Badan\n" + "- Membuang Racun dalam Tubuh\n" + "- Mencegah Penuaan Dini", 19));
//        news_all.add(new News("Manfaat Niacinamide", "Kecantikan", "Niacinamide sendiri sebenarnya merupakan sebutan untuk salah satu bentuk vitamin B3. Manfaat dari Niacinamide, yaitu :\n" +
//                "- Melembabkan kulit\n" + "- Mengatasi noda hitam bekas jerawat\n" + "- Mengendalikan produksi minyak\n" + "- Mengecilkan pori-pori kulit\n" + "- Mengurangi kerutan dan garis halus\n" + "- Mengobati jerawat\n", 20));
//        news_all.add(new News("Indonesia berpotensi jadi kiblat kecantikan karena kekhasannya", "Kecantikan", "Perkumpulan Ahli Tata Rias Semipermanen Indonesia (Pertaspi) menilai Indonesia berpotensi untuk menjadi kiblat kecantikan dunia, mengikuti jejak negara-nagara Asia lainnya seperti Korea Selatan " +
//                "dan Jepang karena memiliki kekhasan sendiri. Hal ini dikarenakan tingkat artistry orang Indonesia itu sangan tinggi. Selain itu, tak ada perbedaan yang signifikan antara penampilan para selebritas dengan orang biasa di Indonesia. Sedangkan di negara-negara lain, selebritas dengan orang biasa " +
//                "sangat terlihat jauh berbeda.", 14));
//        news_all.add(new News("Skincara Exhibition Announcement", "Kecantikan", "Musik hingga makanan Korea banyak diminati oleh masyarakat Indonesia. Begitu pula denganskincare asal negeri Ginseng tersebut. Ada banyak alasan mengapa produk kecantikan dari atau yang populer di Korea, diminati di tanah Air. " +
//                "Nah, bagi para pecinta produk kecantikan merek Korea, bisa merapat di Skincara Exhibition Announcement di Main Atrium Lippo Mall Puri, Jakarta. Sebanyak 24 merek ditampilkan di pameran tersebut, di antaranya Celimax, Dr.G, ISOI, JMSolution dan Papa Recipe.", 16));
//        news_all.add(new News("Dolce & Gabbana akan hadirkan produk kecantikan", "Kecantikan", "CEO rumah mode Dolce & Gabbana, Alfonso Dolce mengatakan pihaknya akan mengembangkan produk kecantikan bersama tim baru di kantor pusat Milan. Pengembangan ini dipercaya dapat menciptakan hal " +
//                "kreatif dengan koleksi yang lebih transversal. Langkah ini diambil setelah adanya kesepakatan lisensi dengan Shiseido pada tahun lalu. Ini merupakan kesepakatan baru setelah Dolce & Gabbana meluncurkan wewangian 30 tahun lalu.", 21));
//        news_all.add(new News("Reseller Kecantikan", "Kecantikan", "Profesi reseller kian menjadi pilihan bagi banyak orang, baik sebagai pekerjaan tambahan maupun pekerjaan utama. Namun, menjalani peran reseller tidak sesederhana menjual ulang suatu produk. Kiat menjadi reseller dari Somethinc :\n" +
//                "- Memilih produk yang sejalan dengan keinginan atau hobi\n" + "- Maksimalkan program pendampingan yang disediakan\n" + "- Menjaga hubungan baik brand dan sesama reseller", 20));
//        news_all.add(new News("Tanam Benang di Wajah", "Kecantikan", "Wajah setiap orang selain faktor usia juga faktor lain adalah jumlah kolagen akan makin berkurang. Dengan faktor usia bertambah mengakibatkan tampilan wajah akan makin kendur atau faktor lainya itu bisa diusahakan dengan perawatan Threadlift. " +
//                "Tanam benang atau Threadlift di wajah yang sudah banyak diterapkan di Korea kini sedang tren dalam perawatan kecantikan di Indonesia. Threadlift banyak digunakan karena kualitasnya yang baik dan memuaskan.", 22));
//        news_all.add(new News("Inner Beauty", "Kecantikan", "Definisi cantik bisa diartikan dari berbagai macam sudut pandang dan berbagai pendapat yang berbeda. Pesona kecantikan fisik dan kecantikan dari dalam (inner beauty) pun bisa dimaknai dari berbagai macam perspektif. Ciri - ciri orang " +
//                "yang Inner beauty :\n" + "- Rendah hati\n" + "- Mencintai dengan tulus\n" + "- Pola pikir positif\n" + "- Nyaman menjadi diri sendiri", 13));

//        for(News a: news_all) {
//            if (a.getTag().equals(user_tag) && a.getMinAge() <= user_age) {
//                findViewById(R.id.none).setVisibility(View.GONE);
//                filter_news.add(a);
//            }
//            else{
//                TextView info = findViewById(R.id.none);
//                info.setText("Tidak ada berita yang sesuai dengan anda");
//            }
//        }
//    }
}