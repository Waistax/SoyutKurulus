/**
 * başaşağıderebeyi.soyutkuruluş.ulus.Şehir.java
 * 0.4 / 19 Eki 2020 / 11:48:20
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.ulus;

import başaşağıderebeyi.soyutkuruluş.dünya.*;

import java.util.*;

public class Şehir {
	public static final float SEVİYE_TAVAN = 9.99F;
	public static final float SEVİYE_TABAN = 0.01F;
	
	public final Ulus ulus;
	public final Köşe köşe;
	public final List<Bölge> komşuBölgeler;
	
	public float seviye;
	
	public Şehir(final Ulus ulus, final Köşe köşe) {
		this.ulus = ulus;
		this.köşe = köşe;
		komşuBölgeler = new ArrayList<>(3);
		seviye = SEVİYE_TABAN;
		ulus.şehirler.add(this);
	}
	
	public void geliştir() {
		if (seviye == SEVİYE_TAVAN)
			return;
		final float buğdayGereksinimi = seviye * seviye * 0.2F;
		final float cevherGereksinimi = buğdayGereksinimi * 1.5F;
		if (ulus.dene(Kaynak.BUĞDAY, buğdayGereksinimi) && ulus.dene(Kaynak.CEVHER, cevherGereksinimi)) {
			ulus.ekle(Kaynak.BUĞDAY, -buğdayGereksinimi);
			ulus.ekle(Kaynak.CEVHER, -cevherGereksinimi);
			seviye += 0.01F;
		}
	}
	
	public void topla() {
		for (final Bölge bölge : köşe.bölgeler)
			ulus.ekle(bölge.kaynak, bölge.üretim * seviye);
	}
}
