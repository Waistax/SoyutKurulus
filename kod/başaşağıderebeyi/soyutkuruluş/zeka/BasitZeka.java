/**
 * başaşağıderebeyi.soyutkuruluş.zeka.BasitZeka.java
 * 0.7 / 19 Eki 2020 / 16:14:21
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.zeka;

import static başaşağıderebeyi.soyutkuruluş.dünya.Kaynak.*;

import başaşağıderebeyi.soyutkuruluş.dünya.*;
import başaşağıderebeyi.soyutkuruluş.işlem.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;

import java.util.*;

public class BasitZeka extends Zeka {
	public static final int YOL_YAP = 0;
	public static final int ŞEHİR_YAP = 1;
	public static final int GELİŞTİR = 2;
	public static final float[][] ODAKLI_KITLIK = {
			{ 0.0F, 0.0F, 0.0F, 50.0F, 50.0F },
			{ 25.0F, 25.0F, 0.0F, 25.0F, 25.0F },
			{ 40.0F, 0.0F, 60.0F, 0.0F, 0.0F }
	};
	
	public final List<Köşe> şehirYapılacaklar;
	public final List<Kenar> yolYapılacaklar;
	public final List<Şehir> geliştirilecekler;
	
	public int hedef;
	
	public BasitZeka(Ulus ulus) {
		super(ulus);
		şehirYapılacaklar = new ArrayList<>();
		yolYapılacaklar = new ArrayList<>();
		geliştirilecekler = new ArrayList<>();
		hedef = ŞEHİR_YAP;
	}

	@Override
	public void günlük() {
		şehirYapılacaklar.clear();
		yolYapılacaklar.clear();
		geliştirilecekler.clear();
		for (final Yol yol : ulus.yollar) {
			if (ulus.dünya.şehirOluşturabilirMi(ulus, yol.kenar.başlangıç))
				şehirYapılacaklar.add(yol.kenar.başlangıç);
			if (ulus.dünya.şehirOluşturabilirMi(ulus, yol.kenar.bitiş))
				şehirYapılacaklar.add(yol.kenar.bitiş);
			for (final Kenar kenar : yol.kenar.kenarlar)
				if (ulus.dünya.yolOluşturabilirMi(ulus, kenar))
					yolYapılacaklar.add(kenar);
		}
		for (final Şehir şehir : ulus.şehirler) {
			for (final Kenar kenar : şehir.köşe.kenarlar.keySet())
				if (ulus.dünya.yolOluşturabilirMi(ulus, kenar))
					yolYapılacaklar.add(kenar);
			if (ulus.dünya.şehirGeliştirilebilirMi(şehir))
				geliştirilecekler.add(şehir);
		}
		if (yolYapılacaklar.isEmpty())
			hedef = GELİŞTİR;
		else if (şehirYapılacaklar.isEmpty())
			hedef = YOL_YAP;
		else
			hedef = ŞEHİR_YAP;
		final float[] kıtlık = new float[DEĞERLER.length];
		for (int i = 0; i < DEĞERLER.length; i++)
			kıtlık[i] = (hedef == YOL_YAP ? Dünya.YOL_MALİYETİ : hedef == ŞEHİR_YAP ? Dünya.ŞEHİR_MALİYETİ : Dünya.GELİŞTİRME_MALİYETİ)[i] / (ulus.durum(DEĞERLER[i]) + ulus.gelir(DEĞERLER[i]) + 1.0F);
		final int[] önemSırası = new int[DEĞERLER.length];
		for (int i = 0; i < DEĞERLER.length; i++)
			önemSırası[i] = i;
		for (int i = 0; i < DEĞERLER.length - 1; i++) {
			int tampon = -1;
			for (int j = 0; j < DEĞERLER.length - 1 - i; j++) {
				final float k1 = kıtlık[önemSırası[j]];
				final float k2 = kıtlık[önemSırası[j + 1]];
				if (k1 < k2 || (k1 == k2 && ulus.durum(DEĞERLER[önemSırası[j + 1]]) < ulus.durum(DEĞERLER[önemSırası[j]]))) {
					tampon = önemSırası[j];
					önemSırası[j] = önemSırası[j + 1];
					önemSırası[j + 1] = tampon;
				}
			}
			if (tampon < 0)
				break;
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Ulus: " + ulus.renk);
		for (int i = 0; i < DEĞERLER.length - 1; i++) {
			System.out.println("I: " + DEĞERLER[önemSırası[i]] + " D" + ulus.envanter[önemSırası[i]] + " G" + ulus.gelir[önemSırası[i]]);
			for (int j = DEĞERLER.length - 1; j > i; j--) {
				System.out.println("J: " + DEĞERLER[önemSırası[j]] + " D" + ulus.envanter[önemSırası[j]] + " G" + ulus.gelir[önemSırası[j]]);
				System.out.println("D1 " + (ulus.gelir(DEĞERLER[önemSırası[j]]) > 0));
				System.out.println("D2 " + (ulus.gelir(DEĞERLER[önemSırası[i]]) == 0));
				System.out.println("D3 " + (ulus.durum(DEĞERLER[önemSırası[i]]) * 4 <= ulus.durum(DEĞERLER[önemSırası[j]])));
				if (ulus.gelir(DEĞERLER[önemSırası[j]]) > 0 && (
						ulus.gelir(DEĞERLER[önemSırası[i]]) == 0 ||
						ulus.durum(DEĞERLER[önemSırası[i]]) * 4 <= ulus.durum(DEĞERLER[önemSırası[j]])))
					ulus.takaslar.get(0).varanakadar(ulus, DEĞERLER[önemSırası[j]], DEĞERLER[önemSırası[i]], (int)Math.floor(ulus.durum(DEĞERLER[önemSırası[j]]) / 4.0F));
			}
		}
		switch (hedef) {
		case YOL_YAP:
			Kenar enAzBağlantılı = null;
			int bağlantıSayısı = 1000;
			for (final Kenar kenar : yolYapılacaklar) {
				int mevcutBağlantıSayısı = 0;
				for (final Kenar diğer : kenar.kenarlar) {
					final Yol yol = ulus.dünya.yollar.get(diğer);
					final YolYapımı yolYapımı = ulus.dünya.yapılanYollar.get(diğer);
					if (
							(yol != null && yol.ulus == ulus) ||
							(yolYapımı != null && yolYapımı.ulus == ulus))
						mevcutBağlantıSayısı++;
				}
				if (bağlantıSayısı > mevcutBağlantıSayısı) {
					enAzBağlantılı = kenar;
					bağlantıSayısı = mevcutBağlantıSayısı;
				}
			}
			if (enAzBağlantılı != null)
				ulus.dünya.yolOluştur(ulus, enAzBağlantılı);
			return;
		case ŞEHİR_YAP:
			Köşe enÇokÜreten = null;
			int üretim = 0;
			for (final Köşe köşe : şehirYapılacaklar) {
				int mevcutÜretim = 0;
				for (final Bölge bölge : köşe.bölgeler)
					mevcutÜretim += bölge.üretim;
				if (üretim < mevcutÜretim) {
					enÇokÜreten = köşe;
					üretim = mevcutÜretim;
				}
			}
			if (enÇokÜreten != null)
				ulus.dünya.şehirOluştur(ulus, enÇokÜreten);
			return;
		case GELİŞTİR:
			Şehir gelişimiDestekleyen = null;
			int destek = 0;
			for (final Şehir şehir : geliştirilecekler) {
				int mevcutDestek = 0;
				for (final Bölge bölge : şehir.köşe.bölgeler) {
					final float geliştirmeMaliyeti = Dünya.GELİŞTİRME_MALİYETİ[bölge.kaynak.sıra];
					mevcutDestek += bölge.üretim * (geliştirmeMaliyeti == 0 ? 1 : 4 * geliştirmeMaliyeti);
				}
				if ((
						destek < mevcutDestek) || (
						gelişimiDestekleyen != null && destek == mevcutDestek &&
						gelişimiDestekleyen.seviye > şehir.seviye)) {
					gelişimiDestekleyen = şehir;
					destek = mevcutDestek;
				}
			}
			if (gelişimiDestekleyen != null)
				ulus.dünya.şehriGeliştir(gelişimiDestekleyen);
			return;
		}
	}

	@Override
	public void aylık() {
	}

	@Override
	public void yıllık() {
	}
}
