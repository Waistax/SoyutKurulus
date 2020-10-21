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
	
	public final List<Kenar> yolYapılacaklar;
	
	public int hedef;
	
	public BasitZeka(Ulus ulus) {
		super(ulus);
		yolYapılacaklar = new ArrayList<>();
		hedef = ŞEHİR_YAP;
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
			yolYapılacaklar.clear();
			for (final Yol yol : ulus.yollar)
				for (final Kenar kenar : yol.kenar.kenarlar)
					if (ulus.dünya.yolOluşturabilirMi(ulus, kenar))
						yolYapılacaklar.add(kenar);
			for (final Şehir şehir : ulus.şehirler)
				for (final Kenar kenar : şehir.köşe.kenarlar.keySet())
					if (ulus.dünya.yolOluşturabilirMi(ulus, kenar))
						yolYapılacaklar.add(kenar);
			if (yolYapılacaklar.isEmpty()) {
				for (final YolYapımı yapım : ulus.dünya.yapılanYollar.values())
					if (yapım.ulus == ulus)
						return;
				hedef = GELİŞTİR;
			} else {
				Kenar enAzBağlantılı = null;
				int bağlantıSayısı = 1000;
				for (final Kenar kenar : yolYapılacaklar) {
					int mevcutBağlantıSayısı = 0;
					for (final Kenar diğer : kenar.kenarlar) {
						final Yol yol = ulus.dünya.yollar.get(diğer);
						if (yol != null && yol.ulus == ulus)
							mevcutBağlantıSayısı++;
					}
					if (bağlantıSayısı > mevcutBağlantıSayısı) {
						enAzBağlantılı = kenar;
						bağlantıSayısı = mevcutBağlantıSayısı;
					}
				}
				if (enAzBağlantılı != null)
					ulus.dünya.yolOluştur(ulus, enAzBağlantılı);
			}
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
		for (int i = 0; i < DEĞERLER.length - 1; i++)
			for (int j = DEĞERLER.length - 1; j > i; j--)
				if (ulus.gelir(DEĞERLER[önemSırası[j]]) > 0 && (
						ulus.gelir(DEĞERLER[önemSırası[i]]) == 0 ||
						ulus.durum(DEĞERLER[önemSırası[i]]) * 4 <= ulus.durum(DEĞERLER[önemSırası[j]])
						))
					ulus.takaslar.get(0).varanakadar(ulus, DEĞERLER[önemSırası[j]], DEĞERLER[önemSırası[i]], ulus.durum(DEĞERLER[önemSırası[j]]) / 5);
	}

	@Override
	public void yıllık() {
	}
}
