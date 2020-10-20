/**
 * başaşağıderebeyi.soyutkuruluş.işlem.ŞehirGeliştirmesi.java
 * 0.10 / 20 Eki 2020 / 08:23:08
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.işlem;

import başaşağıderebeyi.soyutkuruluş.ulus.*;

import java.util.*;

public class ŞehirGeliştirmesi extends Süreliİşlem {
	public final Şehir şehir;
	
	public ŞehirGeliştirmesi(final Şehir şehir) {
		super(şehir.ulus.dünya, Calendar.MONTH, şehir.seviye);
		this.şehir = şehir;
	}

	@Override
	public void tamamlandı() {
		şehir.seviye++;
		şehir.ulus.geliriHesapla();
	}
}
