/**
 * başaşağıderebeyi.soyutkuruluş.ulus.Takas.java
 * 0.7 / 19 Eki 2020 / 16:31:42
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.ulus;

import başaşağıderebeyi.soyutkuruluş.dünya.*;

public class Takas {
	public final Kaynak alınan;
	public final Kaynak verilen;
	public final float oran;

	public Takas(final Kaynak alınan, final Kaynak verilen, final float oran) {
		this.alınan = alınan;
		this.verilen = verilen;
		this.oran = oran;
	}
	
	public void gerçekleştir(final Ulus ulus, final float miktar) {
		if (!ulus.dene(verilen, miktar))
			return;
		ulus.ekle(verilen, -miktar);
		ulus.ekle(alınan, miktar * oran);
	}
	
	public void olabildiğince(final Ulus ulus) {
		gerçekleştir(ulus, ulus.durum(verilen));
	}
	
	public void varanakadar(final Ulus ulus, final float hedef) {
		final float miktar = hedef - ulus.durum(alınan);
		final float ödenecek = miktar / oran;
		if (ulus.dene(verilen, ödenecek))
			gerçekleştir(ulus, ödenecek);
		else
			olabildiğince(ulus);
	}
}
