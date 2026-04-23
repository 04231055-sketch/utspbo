// 1. CLASS ANGGOTA
class Anggota(val nama: String, val idAnggota: String) {
    // Data Hiding: Atribut sensitif diproteksi
    var jumlahPinjam: Int = 0
        private set

    var statusDendaBelumDibayar: Boolean = false
        private set

    // Jalur resmi untuk memperbarui status denda (Simulasi)
    fun setDenda(status: Boolean) {
        statusDendaBelumDibayar = status
    }

    // Validasi aturan bisnis: Maksimal meminjam 3 buku
    fun tambahPinjaman(): Boolean {
        return if (jumlahPinjam < 3) {
            jumlahPinjam++
            true
        } else {
            false
        }
    }

    fun kembalikanBuku() {
        if (jumlahPinjam > 0) jumlahPinjam--
    }
}

// 2. CLASS BUKU
class Buku(val judul: String, stokAwal: Int) {
    var stok: Int = stokAwal
        private set

    fun kurangiStok(): Boolean {
        return if (stok > 0) {
            stok--
            true
        } else {
            false
        }
    }

    fun tambahStok() {
        stok++
    }
}

// 3. CLASS PUSTAKAWAN
class Pustakawan(val namaPustakawan: String) {

    fun prosesPeminjaman(anggota: Anggota, buku: Buku) {
        println(">>> PROSES PEMINJAMAN: ${buku.judul} oleh ${anggota.nama}")

        // Aturan Bisnis: Cek Denda, Cek Stok, dan Cek Batas Pinjam
        if (anggota.statusDendaBelumDibayar) {
            println("❌ DITOLAK: ${anggota.nama} masih memiliki denda yang belum dibayar.")
        } else if (buku.stok <= 0) {
            println("❌ DITOLAK: Stok buku '${buku.judul}' sedang kosong.")
        } else if (anggota.jumlahPinjam >= 3) {
            println("❌ DITOLAK: ${anggota.nama} sudah mencapai batas maksimal peminjaman (3 buku).")
        } else {
            // Jika semua syarat terpenuhi
            anggota.tambahPinjaman()
            buku.kurangiStok()
            println("✅ BERHASIL: Peminjaman disetujui. Sisa stok: ${buku.stok}. Total pinjaman ${anggota.nama}: ${anggota.jumlahPinjam}")
        }
        println("--------------------------------------------------")
    }
}

// =======================================================
// SIMULASI MAIN FUNCTION
// =======================================================
fun main() {
    val pustakawan = Pustakawan("Irfan Noor")
    val bukuPBO = Buku("Pemrograman Kotlin", 1) // Stok hanya 1
    val anggota1 = Anggota("Jhosua", "04231043")
    val anggota2 = Anggota("George", "04231044")

    println("=== SIMULASI ITK-LIBRARY DIMULAI ===\n")

    // 1. SIMULASI SUKSES
    pustakawan.prosesPeminjaman(anggota1, bukuPBO)

    // 2. SIMULASI GAGAL: Stok Kosong
    pustakawan.prosesPeminjaman(anggota2, bukuPBO)

    // 3. SIMULASI GAGAL: Status Denda
    val anggota3 = Anggota("Kamagi", "04231045")
    anggota3.setDenda(true) // Set denda aktif
    pustakawan.prosesPeminjaman(anggota3, bukuPBO)

    // 4. SIMULASI GAGAL: Maksimal Pinjam
    val bukuLain = Buku("Struktur Data", 10)
    anggota1.tambahPinjaman() // Pinjam ke-2
    anggota1.tambahPinjaman() // Pinjam ke-3
    pustakawan.prosesPeminjaman(anggota1, bukuLain) // Mencoba pinjam ke-4
}