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
		for (final Şehir şehir : ulus.şehirler)
			şehir.geliştir();
	}

	@Override
	public void aylık() {
		Takas alBuğday = null;
		Takas satBuğday = null;
		Takas alCevher = null;
		Takas satCevher = null;
		for (final Takas takas : ulus.takaslar) {
			if (takas.alınan == null) {
				if (takas.verilen == BUĞDAY)
					satBuğday = takas;
				else if (takas.verilen == CEVHER)
					satCevher = takas;
				else
					takas.gerçekleştir(ulus, ulus.durum(takas.verilen));
			} else if (takas.alınan == BUĞDAY)
				alBuğday = takas;
			else if (takas.alınan == CEVHER)
				alCevher = takas;
		}
		final float cevherOranı = ulus.durum(CEVHER) / ulus.durum(BUĞDAY);
		if (cevherOranı > 15.0F) {
			satCevher.gerçekleştir(ulus, ulus.durum(CEVHER) * 0.73F);
			alBuğday.gerçekleştir(ulus, ulus.gümüş);
		} else if (cevherOranı > 1.6F) {
			alBuğday.varanakadar(ulus, ulus.durum(CEVHER) / 1.5F);
		} else if (Math.abs(cevherOranı - 1.5F) <= 0.1F) {
			alBuğday.gerçekleştir(ulus, ulus.gümüş * 0.4F);
			alCevher.gerçekleştir(ulus, ulus.gümüş * 0.6F);
		} else if (cevherOranı >= 0.15F) {
			alCevher.varanakadar(ulus, ulus.durum(BUĞDAY) * 1.5F);
		} else {
			satBuğday.gerçekleştir(ulus, ulus.durum(BUĞDAY) * 0.86F);
			alCevher.gerçekleştir(ulus, ulus.gümüş);
		}
	}

	@Override
	public void yıllık() {
	}
}
