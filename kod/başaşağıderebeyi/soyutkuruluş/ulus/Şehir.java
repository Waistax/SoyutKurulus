/**
 * başaşağıderebeyi.soyutkuruluş.ulus.Şehir.java
 * 0.4 / 19 Eki 2020 / 11:48:20
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.ulus;

import static başaşağıderebeyi.soyutkuruluş.dünya.Kaynak.*;

import başaşağıderebeyi.soyutkuruluş.dünya.*;

public class Şehir {
	public final Ulus ulus;
	public final Köşe köşe;
	public final int[] üretim;
	
	public int seviye;
	
	public Şehir(final Ulus ulus, final Köşe köşe) {
		this.ulus = ulus;
		this.köşe = köşe;
		üretim = new int[DEĞERLER.length];
		seviye = Dünya.SEVİYE_TABAN;
		ulus.şehirler.add(this);
	}
	
	public void üretimiHesapla() {
		for (final Bölge bölge : köşe.bölgeler)
			üretim[bölge.kaynak.sıra] += bölge.üretim * seviye;
		ulus.geliriHesapla();
	}
}
