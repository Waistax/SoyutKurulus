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
			ulus.dünya.şehriGeliştir(şehir);
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
					takas.olabildiğince(ulus);
			} else if (takas.alınan == BUĞDAY)
				alBuğday = takas;
			else if (takas.alınan == CEVHER)
				alCevher = takas;
		}
		final float cevherOranı = ulus.dene(BUĞDAY) ? (float)ulus.durum(CEVHER) / ulus.durum(BUĞDAY) : Float.MAX_VALUE;
		if (cevherOranı > 15.0F) {
			satCevher.gerçekleştir(ulus);
			alBuğday.olabildiğince(ulus);
		} else if (cevherOranı > 1.6F) {
			alBuğday.olabildiğince(ulus);
		} else if (Math.abs(cevherOranı - 1.5F) <= 0.1F) {
			while (ulus.dene(null, 300)) {
				alBuğday.gerçekleştir(ulus);
				alCevher.gerçekleştir(ulus);
				alCevher.gerçekleştir(ulus);
			}
		} else if (cevherOranı >= 0.15F) {
			alCevher.olabildiğince(ulus);
		} else {
			satBuğday.gerçekleştir(ulus);
			alCevher.olabildiğince(ulus);
		}
	}

	@Override
	public void yıllık() {
	}
}
