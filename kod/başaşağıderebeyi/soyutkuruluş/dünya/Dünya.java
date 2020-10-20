/**
 * başaşağıderebeyi.soyutkuruluş.dünya.Dünya.java
 * 0.2 / 18 Eki 2020 / 09:23:15
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.dünya;

import static başaşağıderebeyi.soyutkuruluş.dünya.Kaynak.*;

import başaşağıderebeyi.matematik.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Dünya {
	public static final float ASGARİ_MESAFE = 0.9F;
	
	public static boolean aynı(final Vektör2 fark, final Köşe k1, final Köşe k2) {
		return fark.çıkar(k1.konum, k2.konum).uzunluğunKaresi() < ASGARİ_MESAFE;
	}
	
	public final List<Bölge> bölgeler;
	public final List<Köşe> köşeler;
	public final List<Kenar> kenarlar;
	public final List<Ulus> uluslar;
	public final List<Takas> takaslar;
	public final Map<Köşe, Şehir> şehirler;
	public final Map<Kenar, Yol> yollar;
	public final Calendar takvim;

	public Dünya() {
		bölgeler = new ArrayList<>();
		köşeler = new ArrayList<>();
		kenarlar = new ArrayList<>();
		uluslar = new ArrayList<>();
		takaslar = new ArrayList<>();
		şehirler = new HashMap<>();
		yollar = new HashMap<>();
		takvim = Calendar.getInstance();
		takvim.clear();
		takvim.set(0, 11, 31);
		for (final Kaynak kaynak : DEĞERLER) {
			takaslar.add(new Takas(null, kaynak, 0.25F));
			takaslar.add(new Takas(kaynak, null, 1.0F));
		}
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
		for (final Kenar kenar : kenarlar) {
			kenar.kenarlar.addAll(kenar.başlangıç.kenarlar.keySet());
			kenar.kenarlar.addAll(kenar.bitiş.kenarlar.keySet());
		}
		System.gc();
	}
	
	public void ulusOluştur(final Color renk) {
		final Ulus ulus = new Ulus(this, renk);
		for (int i = 0; i < DEĞERLER.length * 2; i++)
			ulus.takaslar.add(takaslar.get(i));
	}
	
	public boolean şehirOluşturabilirMi(final Ulus ulus, final Köşe köşe) {
		if (şehirler.containsKey(köşe))
			return false;
		for (final Kenar kenar : köşe.kenarlar.keySet())
			if (köşe.kenarlar.get(kenar)) {
				if (şehirler.containsKey(kenar.bitiş))
					return false;
			} else {
				if (şehirler.containsKey(kenar.başlangıç))
					return false;
			}
		return true;
	}
	
	public void şehirOluştur(final Ulus ulus, final Köşe köşe) {
		if (!(
				ulus.dene(BUĞDAY, 1.0F) &&
				ulus.dene(KOYUN, 1.0F) &&
				ulus.dene(ODUN, 1.0F) &&
				ulus.dene(TUĞLA, 1.0F)))
			return;
		if (!şehirOluşturabilirMi(ulus, köşe))
			return;
		ulus.ekle(BUĞDAY, -1.0F);
		ulus.ekle(KOYUN, -1.0F);
		ulus.ekle(ODUN, -1.0F);
		ulus.ekle(TUĞLA, -1.0F);
		final Şehir şehir = new Şehir(ulus, köşe);
		şehirler.put(köşe, şehir);
	}
	
	public boolean yolOluşturabilirMi(final Ulus ulus, final Kenar kenar) {
		if (yollar.containsKey(kenar))
			return false;
		final Şehir başlangıç = şehirler.get(kenar.başlangıç);
		if (başlangıç != null && başlangıç.ulus == ulus)
			return true;
		final Şehir bitiş = şehirler.get(kenar.bitiş);
		if (bitiş != null && bitiş.ulus == ulus)
			return true;
		for (final Kenar diğerKenar : kenar.kenarlar) {
			final Yol bağlantı = yollar.get(diğerKenar);
			if (bağlantı != null && bağlantı.ulus == ulus)
				return true;
		}
		return false;
	}
	
	public void yolOluştur(final Ulus ulus, final Kenar kenar) {
		if (!(
				ulus.dene(ODUN, 1.0F) &&
				ulus.dene(TUĞLA, 1.0F)))
			return;
		if (!yolOluşturabilirMi(ulus, kenar))
			return;
		ulus.ekle(ODUN, -1.0F);
		ulus.ekle(TUĞLA, -1.0F);
		final Yol yol = new Yol(ulus, kenar);
		yollar.put(kenar, yol);
	}
	
	public void günlük() {
		for (final Yol yol : yollar.values())
			yol.topla();
		for (final Ulus ulus : uluslar)
			ulus.zeka.günlük();
	}
	
	public void aylık() {
		for (final Şehir şehir : şehirler.values())
			şehir.topla();
		for (final Ulus ulus : uluslar)
			ulus.zeka.aylık();
	}
	
	public void yıllık() {
		for (final Ulus ulus : uluslar)
			ulus.zeka.yıllık();
	}
}
