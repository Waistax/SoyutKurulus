/**
 * başaşağıderebeyi.soyutkuruluş.zeka.BasitZeka.java
 * 0.7 / 19 Eki 2020 / 16:14:21
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.zeka;

import başaşağıderebeyi.soyutkuruluş.ulus.*;

public class BasitZeka extends Zeka {
	public BasitZeka(Ulus ulus) {
		super(ulus);
	}

	@Override
	public void günlük() {
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
