/**
 * başaşağıderebeyi.soyutkuruluş.zeka.BasitZeka.java
 * 0.7 / 19 Eki 2020 / 16:14:21
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.zeka;

import static başaşağıderebeyi.soyutkuruluş.dünya.Kaynak.*;

import başaşağıderebeyi.soyutkuruluş.dünya.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;

public class BasitZeka extends Zeka {
	public Yol sonYapılan;
	
	public BasitZeka(Ulus ulus) {
		super(ulus);
	}

	@Override
	public void günlük() {
		Takas alOdun = null;
		Takas satOdun = null;
		Takas alTuğla = null;
		Takas satTuğla = null;
		for (final Takas takas : ulus.takaslar) {
			if (takas.alınan == null) {
				if (takas.verilen == ODUN)
					satOdun = takas;
				else if (takas.verilen == TUĞLA)
					satTuğla = takas;
				else
					takas.gerçekleştir(ulus, ulus.durum(takas.verilen));
			} else if (takas.alınan == ODUN)
				alOdun = takas;
			else if (takas.alınan == TUĞLA)
				alTuğla = takas;
		}
		final float tuğlaOranı = ulus.durum(TUĞLA) / ulus.durum(ODUN);
		if (tuğlaOranı > 10.0F) {
			satTuğla.gerçekleştir(ulus, ulus.durum(TUĞLA) * 0.8F);
			alOdun.gerçekleştir(ulus, ulus.gümüş);
		} else if (tuğlaOranı > 1.1F) {
			alOdun.varanakadar(ulus, ulus.durum(TUĞLA));
		} else if (Math.abs(tuğlaOranı - 1.0F) <= 0.1F) {
			alOdun.gerçekleştir(ulus, ulus.gümüş * 0.4F);
			alTuğla.gerçekleştir(ulus, ulus.gümüş * 0.6F);
		} else if (tuğlaOranı >= 0.1F) {
			alTuğla.varanakadar(ulus, ulus.durum(ODUN));
		} else {
			satOdun.gerçekleştir(ulus, ulus.durum(ODUN) * 0.8F);
			alTuğla.gerçekleştir(ulus, ulus.gümüş);
		}
		for (final Yol yol : ulus.yollar) {
			for (final Kenar kenar : yol.kenar.kenarlar) {
				if (ulus.dünya.yolOluşturabilirMi(ulus, kenar)) {
					ulus.dünya.yolOluştur(ulus, kenar);
					return;
				}
			}
		}
		for (final Şehir şehir : ulus.şehirler) {
			for (final Kenar kenar : şehir.köşe.kenarlar.keySet()) {
				if (ulus.dünya.yolOluşturabilirMi(ulus, kenar)) {
					ulus.dünya.yolOluştur(ulus, kenar);
					return;
				}
			}
		}
	}

	@Override
	public void aylık() {
		
	}

	@Override
	public void yıllık() {
		
	}
}
