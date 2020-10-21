/**
 * başaşağıderebeyi.soyutkuruluş.işlem.YolYapımı.java
 * 0.9 / 20 Eki 2020 / 07:32:02
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.işlem;

import başaşağıderebeyi.soyutkuruluş.dünya.*;
import başaşağıderebeyi.soyutkuruluş.ulus.*;

import java.util.*;

public class YolYapımı extends Süreliİşlem {
	public final Ulus ulus;
	public final Kenar kenar;
	
	public YolYapımı(final Ulus ulus, final Kenar kenar) {
		super(ulus.dünya, Calendar.DAY_OF_MONTH, 120);
		this.ulus = ulus;
		this.kenar = kenar;
		ulus.dünya.yapılanYollar.put(kenar, this);
	}

	@Override
	public void tamamlandı() {
		ulus.dünya.yollar.put(kenar, new Yol(ulus, kenar));
		ulus.dünya.yapılanYollar.remove(kenar);
	}
}
