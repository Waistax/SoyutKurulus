/**
 * başaşağıderebeyi.soyutkuruluş.dünya.Dünya.java
 * 0.2 / 18 Eki 2020 / 09:23:15
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.dünya;

import static başaşağıderebeyi.soyutkuruluş.dünya.Kaynak.*;

import başaşağıderebeyi.matematik.*;
import başaşağıderebeyi.soyutkuruluş.işlem.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Dünya {
	public static final float ASGARİ_MESAFE = 0.9F;
	public static final int SEVİYE_TAVAN = 3;
	public static final int SEVİYE_TABAN = 1;
	public static final int[] ŞEHİR_MALİYETİ = { 1, 1, 0, 1, 1 };
	public static final int[] YOL_MALİYETİ = { 0, 0, 0, 1, 1 };
	public static final int[] GELİŞTİRME_MALİYETİ = { 2, 0, 3, 0, 0 };
	
	public static boolean aynı(final Vektör2 fark, final Köşe k1, final Köşe k2) {
		return fark.çıkar(k1.konum, k2.konum).uzunluğunKaresi() < ASGARİ_MESAFE;
	}
	
	public final List<Bölge> bölgeler;
	public final List<Köşe> köşeler;
	public final List<Kenar> kenarlar;
	public final List<Ulus> uluslar;
	public final List<Takas> takaslar;
	public final List<Süreliİşlem> işlemler;
	public final Map<Köşe, Şehir> şehirler;
	public final Map<Kenar, Yol> yollar;
	public final Map<Köşe, ŞehirYapımı> yapılanŞehirler;
	public final Map<Kenar, YolYapımı> yapılanYollar;
	public final Calendar takvim;

	public Dünya() {
		bölgeler = new ArrayList<>();
		köşeler = new ArrayList<>();
		kenarlar = new ArrayList<>();
		uluslar = new ArrayList<>();
		takaslar = new ArrayList<>();
		işlemler = new ArrayList<>();
		şehirler = new HashMap<>();
		yollar = new HashMap<>();
		yapılanŞehirler = new HashMap<>();
		yapılanYollar = new HashMap<>();
		takvim = Calendar.getInstance();
		takvim.clear();
		takvim.set(0, 11, 31);
		takaslar.add(new Takas(null, 4));
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
	
	public boolean şehirVarMı(final Köşe köşe) {
		return şehirler.containsKey(köşe) || yapılanŞehirler.containsKey(köşe);
	}
	
	public boolean yolVarMı(final Kenar kenar) {
		return yollar.containsKey(kenar) || yapılanYollar.containsKey(kenar);
	}
	
	public void ulusOluştur(final Color renk) {
		final Ulus ulus = new Ulus(this, renk);
		ulus.takaslar.add(takaslar.get(0));
	}
	
	public boolean başlangıçŞehriOluşturabilirMi(final Ulus ulus, final Köşe köşe) {
		if (şehirVarMı(köşe))
			return false;
		for (final Kenar kenar : köşe.kenarlar.keySet())
			if (köşe.kenarlar.get(kenar)) {
				if (şehirVarMı(kenar.bitiş))
					return false;
			} else {
				if (şehirVarMı(kenar.başlangıç))
					return false;
			}
		return true;
	}
	
	public void başlangıçŞehriOluştur(final Ulus ulus, final Köşe köşe) {
		if (!başlangıçŞehriOluşturabilirMi(ulus, köşe))
			return;
		final Şehir şehir = new Şehir(ulus, köşe);
		ulus.dünya.şehirler.put(köşe, şehir);
		şehir.üretimiHesapla();
	}
	
	public boolean şehirOluşturabilirMi(final Ulus ulus, final Köşe köşe) {
		if (!başlangıçŞehriOluşturabilirMi(ulus, köşe))
			return false;
		for (final Yol yol : ulus.yollar) {
			if (yol.kenar.başlangıç == köşe || yol.kenar.bitiş == köşe)
				return true;
		}
		return false;
	}
	
	public void şehirOluştur(final Ulus ulus, final Köşe köşe) {
		if (!ulus.dene(ŞEHİR_MALİYETİ))
			return;
		if (!şehirOluşturabilirMi(ulus, köşe))
			return;
		ulus.çıkar(ŞEHİR_MALİYETİ);
		new ŞehirYapımı(ulus, köşe);
	}
	
	public boolean şehirGeliştirilebilirMi(final Şehir şehir) {
		if (şehir.seviye >= SEVİYE_TAVAN)
			return false;
		for (final Süreliİşlem işlem : işlemler)
			if (işlem instanceof ŞehirGeliştirmesi && ((ŞehirGeliştirmesi)işlem).şehir == şehir)
				return false;
		return true;
	}
	
	public void şehriGeliştir(final Şehir şehir) {
		if (!şehir.ulus.dene(GELİŞTİRME_MALİYETİ))
			return;
		if (!şehirGeliştirilebilirMi(şehir))
			return;
		şehir.ulus.çıkar(GELİŞTİRME_MALİYETİ);
		new ŞehirGeliştirmesi(şehir);
	}
	
	public boolean yolOluşturabilirMi(final Ulus ulus, final Kenar kenar) {
		if (yolVarMı(kenar))
			return false;
		for (final Süreliİşlem işlem : işlemler)
			if (işlem instanceof YolYapımı && ((YolYapımı)işlem).kenar == kenar)
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
		if (!ulus.dene(YOL_MALİYETİ))
			return;
		if (!yolOluşturabilirMi(ulus, kenar))
			return;
		ulus.çıkar(YOL_MALİYETİ);
		new YolYapımı(ulus, kenar);
	}
	
	public void günlük() {
		final int eskiAy = takvim.get(Calendar.MONTH);
		takvim.add(Calendar.DAY_OF_MONTH, 1);
		for (int i = işlemler.size() - 1; i > -1; i--) {
			final Süreliİşlem işlem = işlemler.get(i);
			if (takvim.compareTo(işlem.tamamlanmaZamanı) >= 0) {
				işlem.tamamlandı();
				işlemler.remove(i);
			}
		}
		for (final Ulus ulus : uluslar)
			ulus.zeka.günlük();
		if (eskiAy != takvim.get(Calendar.MONTH)) {
			aylık();
			if (eskiAy == Calendar.DECEMBER)
				yıllık();
		}
	}
	
	public void aylık() {
		for (final Ulus ulus : uluslar)
			ulus.zeka.aylık();
	}
	
	public void yıllık() {
		for (final Şehir şehir : şehirler.values())
			for (int i = 0; i < DEĞERLER.length; i++)
				şehir.ulus.envanter[i] += şehir.üretim[i];
		for (final Ulus ulus : uluslar)
			ulus.zeka.yıllık();
	}
}
