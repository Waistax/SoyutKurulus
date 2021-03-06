/**
 * başaşağıderebeyi.soyutkuruluş.işlem.ŞehirYapımı.java
 * 0.9 / 20 Eki 2020 / 07:35:06
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.işlem;

import başaşağıderebeyi.soyutkuruluş.dünya.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;

import java.util.*;

public class ŞehirYapımı extends Süreliİşlem {
	public final Ulus ulus;
	public final Köşe köşe;
	
	public ŞehirYapımı(final Ulus ulus, final Köşe köşe) {
		super(ulus.dünya, Calendar.DAY_OF_MONTH, 300);
		this.ulus = ulus;
		this.köşe = köşe;
		ulus.dünya.yapılanŞehirler.put(köşe, this);
	}

	@Override
	public void tamamlandı() {
		final Şehir şehir = new Şehir(ulus, köşe);
		ulus.dünya.şehirler.put(köşe, şehir);
		ulus.dünya.yapılanŞehirler.remove(köşe);
		şehir.üretimiHesapla();
	}
}
