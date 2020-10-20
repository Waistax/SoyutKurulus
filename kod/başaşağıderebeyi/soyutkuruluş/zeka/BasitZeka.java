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
	
	public final Map<Kaynak, Takas> al;
	public final Map<Kaynak, Takas> sat;
	
	public int hedef;
	
	public BasitZeka(Ulus ulus) {
		super(ulus);
		al = new HashMap<>();
		sat = new HashMap<>();
		for (final Takas takas : ulus.takaslar)
			if (takas.alınan != null)
				al.put(takas.alınan, takas);
			else
				sat.put(takas.verilen, takas);
	}

	@Override
	public void günlük() {
		switch (hedef) {
		case YOL_YAP:
			for (final Köşe köşe : ulus.dünya.köşeler)
				if (ulus.dünya.şehirOluşturabilirMi(ulus, köşe)) {
					hedef = ŞEHİR_YAP;
					return;
				}
			for (final Yol yol : ulus.yollar)
				for (final Kenar kenar : yol.kenar.kenarlar)
					if (ulus.dünya.yolOluşturabilirMi(ulus, kenar)) {
						ulus.dünya.yolOluştur(ulus, kenar);
						return;
					}
			for (final Şehir şehir : ulus.şehirler)
				for (final Kenar kenar : şehir.köşe.kenarlar.keySet())
					if (ulus.dünya.yolOluşturabilirMi(ulus, kenar)) {
						ulus.dünya.yolOluştur(ulus, kenar);
						return;
					}
			for (final Süreliİşlem işlem : ulus.dünya.işlemler)
				if (işlem instanceof YolYapımı && ((YolYapımı)işlem).ulus == ulus)
					return;
			hedef = GELİŞTİR;
			return;
		case ŞEHİR_YAP:
			for (final Köşe köşe : ulus.dünya.köşeler)
				if (ulus.dünya.şehirOluşturabilirMi(ulus, köşe)) {
					ulus.dünya.şehirOluştur(ulus, köşe);
					return;
				}
			hedef = YOL_YAP;
			return;
		case GELİŞTİR:
			for (final Şehir şehir : ulus.şehirler)
				ulus.dünya.şehriGeliştir(şehir);
			return;
		}
	}

	@Override
	public void aylık() {
		final float[] kıtlık = new float[DEĞERLER.length];
		for (int i = 0; i < DEĞERLER.length; i++)
			kıtlık[i] = ODAKLI_KITLIK[hedef][i] / (ulus.durum(DEĞERLER[i]) + ulus.gelir(DEĞERLER[i]) + 1.0F);
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
		for (int i = 0; i < DEĞERLER.length - 1; i++) {
			sat.get(DEĞERLER[önemSırası[DEĞERLER.length - 1]]).gerçekleştir(ulus);
			al.get(DEĞERLER[önemSırası[i]]).gerçekleştir(ulus);
		}
//		switch (hedef) {
//		case YOL_YAP:
//			if (odunYok) {
//				if (tuğlaYok) {
//					sat.get(BUĞDAY).gerçekleştir(ulus);
//					sat.get(KOYUN).gerçekleştir(ulus);
//					sat.get(CEVHER).gerçekleştir(ulus);
//					al.get(ulus.durum(ODUN) < ulus.durum(TUĞLA) ? ODUN : TUĞLA).gerçekleştir(ulus);
//				} else {
//					sat.get(BUĞDAY).gerçekleştir(ulus);
//					sat.get(KOYUN).gerçekleştir(ulus);
//					sat.get(CEVHER).gerçekleştir(ulus);
//					al.get(ODUN).gerçekleştir(ulus);
//				}
//			} else if (tuğlaYok) {
//				sat.get(BUĞDAY).gerçekleştir(ulus);
//				sat.get(KOYUN).gerçekleştir(ulus);
//				sat.get(CEVHER).gerçekleştir(ulus);
//				al.get(TUĞLA).gerçekleştir(ulus);
//			}
//			return;
//		case ŞEHİR_YAP:
//			return;
//		case GELİŞTİR:
//			if (ulus.gelir(BUĞDAY) == 0)
//				al.get(BUĞDAY).gerçekleştir(ulus);
//			else
//				sat.get(BUĞDAY).gerçekleştir(ulus);
//			sat.get(KOYUN).gerçekleştir(ulus);
//			if (ulus.gelir(CEVHER) == 0)
//				al.get(CEVHER).gerçekleştir(ulus);
//			else
//				sat.get(CEVHER).gerçekleştir(ulus);
//			sat.get(ODUN).gerçekleştir(ulus);
//			sat.get(TUĞLA).gerçekleştir(ulus);
//			return;
//		}
	}

	@Override
	public void yıllık() {
	}
}
