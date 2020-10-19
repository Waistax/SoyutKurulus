/**
 * başaşağıderebeyi.soyutkuruluş.dünya.Dünya.java
 * 0.2 / 18 Eki 2020 / 09:23:15
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.dünya;

import başaşağıderebeyi.matematik.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;

import java.util.*;

public class Dünya {
	public static final float ASGARİ_MESAFE = 0.9F;
	public static final Vektör2 FARK = new Vektör2();
	
	public static synchronized boolean aynı(Vektör2 v1, Vektör2 v2) {
		return FARK.çıkar(v1, v2).uzunluğunKaresi() < ASGARİ_MESAFE;
	}
	
	public final List<Bölge> bölgeler;
	public final List<Vektör2> köşeler;
	public final List<Kenar> kenarlar;
	public final List<Ulus> uluslar;
	public final Map<Vektör2, Şehir> şehirler;
	public final Map<Kenar, Yol> yollar;

	public Dünya() {
		bölgeler = new ArrayList<>();
		köşeler = new ArrayList<>();
		kenarlar = new ArrayList<>();
		uluslar = new ArrayList<>();
		şehirler = new HashMap<>();
		yollar = new HashMap<>();
	}
	
	public void şekliGüncelle() {
		köşeler.clear();
		kenarlar.clear();
		for (final Bölge bölge : bölgeler) {
			köşeler:for (final Vektör2 köşe : bölge.köşeler) {
				for (final Vektör2 diğerKöşe : köşeler)
					if (aynı(köşe, diğerKöşe))
						continue köşeler;
				köşeler.add(köşe);
			}
			kenarlar:for (final Kenar kenar : bölge.kenarlar) {
				for (final Kenar diğerKenar : kenarlar)
					if (
							(aynı(diğerKenar.başlangıç, kenar.başlangıç) && aynı(diğerKenar.bitiş, kenar.bitiş)) ||
							(aynı(diğerKenar.bitiş, kenar.başlangıç) && aynı(diğerKenar.başlangıç, kenar.bitiş)))
						continue kenarlar;
				kenarlar.add(kenar);
			}
		}
	}
	
	public void şehirOluştur(final Ulus ulus, final Vektör2 köşe) {
		if (şehirler.containsKey(köşe))
			return;
		for (final Kenar kenar : kenarlar) {
			if (köşe == kenar.başlangıç) {
				if (şehirler.containsKey(kenar.bitiş))
					return;
			} else if (köşe == kenar.bitiş) {
				if (şehirler.containsKey(kenar.başlangıç))
					return;
			}
		}
		final Şehir şehir = new Şehir(ulus, köşe);
		şehirler.put(köşe, şehir);
	}
	
	public void yolOluştur(final Ulus ulus, final Kenar kenar) {
		if (yollar.containsKey(kenar))
			return;
		final Yol yol = new Yol(ulus, kenar);
		yollar.put(kenar, yol);
	}
}
