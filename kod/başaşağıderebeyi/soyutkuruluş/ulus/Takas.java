/**
 * başaşağıderebeyi.soyutkuruluş.ulus.Takas.java
 * 0.7 / 19 Eki 2020 / 16:31:42
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.ulus;

import başaşağıderebeyi.soyutkuruluş.dünya.*;

public class Takas {
	public final Kaynak alınan;
	public final int miktar;

	public Takas(Kaynak alınan, int miktar) {
		this.alınan = alınan;
		this.miktar = miktar;
	}
	
	public boolean kabulEdiliyorMu(final Kaynak ödeme) {
		return alınan == null || alınan == ödeme;
	}
	
	public boolean yapabilirMi(final Ulus ulus, final Kaynak ödeme) {
		return kabulEdiliyorMu(ödeme) && ulus.dene(ödeme, miktar);
	}
	
	public void takas(final Ulus ulus, final Kaynak ödeme, final Kaynak istenen) {
		ulus.çıkar(ödeme, miktar);
		ulus.ekle(istenen);
	}

	public void gerçekleştir(final Ulus ulus, final Kaynak ödeme, final Kaynak istenen) {
		if (yapabilirMi(ulus, ödeme))
			takas(ulus, ödeme, istenen);
	}
	
	public void olabildiğince(final Ulus ulus, final Kaynak ödeme, final Kaynak istenen) {
		while (yapabilirMi(ulus, ödeme))
			takas(ulus, ödeme, istenen);
	}
	
	public void varanakadar(final Ulus ulus, final Kaynak ödeme, final Kaynak istenen, final int hedef) {
		while (ulus.durum(istenen) < hedef)
			if (yapabilirMi(ulus, ödeme))
				takas(ulus, ödeme, istenen);
			else
				return;
	}
}
