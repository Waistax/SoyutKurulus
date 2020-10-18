/**
 * başaşağıderebeyi.soyutkuruluş.dünya.Dünya.java
 * 0.2 / 18 Eki 2020 / 09:23:15
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.dünya;

import başaşağıderebeyi.matematik.*;

import java.util.*;

public class Dünya {
	public final List<Bölge> bölgeler;
	public final List<Vektör2> köşeler;
	public final List<Kenar> kenarlar;
	public final int satırSayısı;

	public Dünya(final int satırSayısı) {
		bölgeler = new ArrayList<>();
		köşeler = new ArrayList<>();
		kenarlar = new ArrayList<>();
		this.satırSayısı = satırSayısı;
		final int satırYüksekliği = satırSayısı;
		int satırSaçılması = satırSayısı + 1;
		for (int i = -satırYüksekliği; i <= satırYüksekliği; i++) {
			final int satırGenişliği = satırSaçılması / 2;
			yatay:for (int j = -satırGenişliği; j <= satırGenişliği; j++) {
				final Vektör2 merkez = new Vektör2(j * Bölge.KENAR_SİNÜS * 2.0F, i * Bölge.KENAR_KOSİNÜS * 3.0F);
				if (satırSaçılması % 2 == 0) {
					if (j == 0)
						continue yatay;
					merkez.x += Bölge.KENAR_SİNÜS * (j < 0 ? 1.0F : -1.0F);
				}
				final Bölge bölge = new Bölge(this, merkez);
				bölgeler.add(bölge);
				köşeler:for (final Vektör2 köşe : bölge.köşeler) {
					for (final Vektör2 diğerKöşe : köşeler)
						if (diğerKöşe.eşittir(köşe))
							continue köşeler;
					köşeler.add(köşe);
				}
				kenarlar:for (final Kenar kenar : bölge.kenarlar) {
					for (final Kenar diğerKenar : kenarlar)
						if (
								(diğerKenar.başlangıç.eşittir(kenar.başlangıç) && diğerKenar.bitiş.eşittir(kenar.bitiş)) ||
								(diğerKenar.başlangıç.eşittir(kenar.bitiş) && diğerKenar.bitiş.eşittir(kenar.başlangıç)))
							continue kenarlar;
					kenarlar.add(kenar);
				}
			}
			if (i < 0)
				satırSaçılması++;
			else
				satırSaçılması--;
		}
	}
}
