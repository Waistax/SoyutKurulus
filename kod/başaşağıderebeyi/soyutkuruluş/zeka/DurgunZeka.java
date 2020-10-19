/**
 * başaşağıderebeyi.soyutkuruluş.zeka.DurgunZeka.java
 * 0.7 / 19 Eki 2020 / 16:47:38
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.zeka;

import static başaşağıderebeyi.soyutkuruluş.dünya.Kaynak.*;

import başaşağıderebeyi.soyutkuruluş.ulus.*;

public class DurgunZeka extends Zeka {
	public DurgunZeka(Ulus ulus) {
		super(ulus);
	}

	@Override
	public void günlük() {
		Takas iyiBuğday = null;
		Takas iyiCevher = null;
		for (final Takas takas : ulus.takaslar) {
			if (takas.alınan == null && takas.verilen != BUĞDAY && takas.verilen != CEVHER)
				takas.gerçekleştir(ulus, ulus.durum(takas.verilen));
			else if (takas.alınan == BUĞDAY)
				iyiBuğday = takas;
			else if (takas.alınan == CEVHER)
				iyiCevher = takas;
		}
		final float cevherOranı = ulus.durum(CEVHER) / ulus.durum(BUĞDAY);
		if (cevherOranı > 1.5F) {
			iyiBuğday.varanakadar(ulus, ulus.durum(CEVHER) / 1.5F);
		} else if (cevherOranı < 1.5F) {
			iyiCevher.varanakadar(ulus, ulus.durum(BUĞDAY) * 1.5F);
		}
		if (ulus.gümüş > 0.0F) {
			iyiBuğday.gerçekleştir(ulus, ulus.gümüş * 0.4F);
			iyiCevher.gerçekleştir(ulus, ulus.gümüş * 0.6F);
		}
		for (final Şehir şehir : ulus.şehirler)
			şehir.geliştir();
	}

	@Override
	public void aylık() {
	}

	@Override
	public void yıllık() {
	}
}
