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
	
	public static boolean aynı(final Vektör2 fark, final Köşe k1, final Köşe k2) {
		return fark.çıkar(k1.konum, k2.konum).uzunluğunKaresi() < ASGARİ_MESAFE;
	}
	
	public final List<Bölge> bölgeler;
	public final List<Köşe> köşeler;
	public final List<Kenar> kenarlar;
	public final List<Ulus> uluslar;
	public final Map<Köşe, Şehir> şehirler;
	public final Map<Kenar, Yol> yollar;
	public final Calendar takvim;

	public Dünya() {
		bölgeler = new ArrayList<>();
		köşeler = new ArrayList<>();
		kenarlar = new ArrayList<>();
		uluslar = new ArrayList<>();
		şehirler = new HashMap<>();
		yollar = new HashMap<>();
		takvim = Calendar.getInstance();
		takvim.clear();
		takvim.set(0, 11, 31);
	}
	
	public void şekliGüncelle() {
		köşeler.clear();
		kenarlar.clear();
		final Vektör2 fark = new Vektör2();
		for (final Bölge bölge : bölgeler) {
			köşeler:for (int i = 0; i < Bölge.KENAR_SAYISI; i++) {
				for (final Köşe köşe : köşeler)
					if (aynı(fark, köşe, bölge.köşeler[i])) {
						bölge.köşeler[i] = köşe;
						köşe.bölgeler.add(bölge);
						continue köşeler;
					}
				köşeler.add(bölge.köşeler[i]);
				bölge.köşeler[i].bölgeler.add(bölge);
			}
			kenarlar:for (int i = 0; i < Bölge.KENAR_SAYISI; i++) {
				for (final Kenar kenar : kenarlar)
					if ((
							aynı(fark, bölge.kenarlar[i].başlangıç, kenar.başlangıç) &&
							aynı(fark, bölge.kenarlar[i].bitiş, kenar.bitiş)) || (
							aynı(fark, bölge.kenarlar[i].başlangıç, kenar.bitiş) &&
							aynı(fark, bölge.kenarlar[i].bitiş, kenar.başlangıç))) {
						bölge.kenarlar[i] = kenar;
						continue kenarlar;
					}
				kenarlar.add(bölge.kenarlar[i]);
			}
		}
		for (int i = 0; i < kenarlar.size(); i++) {
			final Kenar kenar = kenarlar.get(i);
			Köşe yeniBaşlangıç = null;
			Köşe yeniBitiş = null;
			for (final Köşe köşe : köşeler) {
				if (aynı(fark, kenar.başlangıç, köşe) && kenar.başlangıç != köşe) {
					yeniBaşlangıç = köşe;
					if (yeniBitiş != null)
						break;
				}
				if (aynı(fark, kenar.bitiş, köşe) && kenar.bitiş != köşe) {
					yeniBitiş = köşe;
					if (yeniBaşlangıç != null)
						break;
				}
			}
			if (yeniBaşlangıç == null && yeniBitiş == null)
				continue;
			kenarlar.remove(i);
			kenarlar.add(i, new Kenar(kenar.bölge,
					yeniBaşlangıç != null ? yeniBaşlangıç : kenar.başlangıç,
							yeniBitiş != null ? yeniBitiş : kenar.bitiş));
		}
		for (final Köşe köşe : köşeler) {
			for (final Kenar kenar : kenarlar) {
				if (kenar.başlangıç == köşe)
					köşe.kenarlar.put(kenar, true);
				else if (kenar.bitiş == köşe)
					köşe.kenarlar.put(kenar, false);
			}
		}
		System.gc();
	}
	
	public void şehirOluştur(final Ulus ulus, final Köşe köşe) {
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
