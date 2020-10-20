/**
 * başaşağıderebeyi.soyutkuruluş.ulus.Takas.java
 * 0.7 / 19 Eki 2020 / 16:31:42
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.ulus;

import başaşağıderebeyi.soyutkuruluş.dünya.*;

public class Takas {
	public final Kaynak alınan;
	public final int alınacak;
	public final Kaynak verilen;
	public final int verilecek;

	public Takas(Kaynak alınan, int alınacak, Kaynak verilen, int verilecek) {
		this.alınan = alınan;
		this.alınacak = alınacak;
		this.verilen = verilen;
		this.verilecek = verilecek;
	}

	public void gerçekleştir(final Ulus ulus) {
		if (!ulus.dene(verilen, verilecek))
			return;
		ulus.çıkar(verilen, verilecek);
		ulus.ekle(alınan, alınacak);
	}
	
	public void olabildiğince(final Ulus ulus) {
		while (ulus.dene(verilen, verilecek))
			gerçekleştir(ulus);
	}
	
	public void varanakadar(final Ulus ulus, final int hedef) {
		while (ulus.durum(alınan) < hedef)
			if (ulus.dene(verilen, verilecek))
				gerçekleştir(ulus);
			else
				return;
	}
}
