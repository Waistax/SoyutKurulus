/**
 * başaşağıderebeyi.soyutkuruluş.ulus.Şehir.java
 * 0.4 / 19 Eki 2020 / 11:48:20
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.ulus;

import başaşağıderebeyi.soyutkuruluş.dünya.*;

public class Şehir {
	public final Ulus ulus;
	public final Köşe köşe;
	
	public int seviye;
	
	public Şehir(final Ulus ulus, final Köşe köşe) {
		this.ulus = ulus;
		this.köşe = köşe;
		seviye = Dünya.SEVİYE_TABAN;
		ulus.şehirler.add(this);
	}
	
	public void topla() {
		for (final Bölge bölge : köşe.bölgeler)
			ulus.ekle(bölge.kaynak, bölge.üretim * seviye);
	}
}
