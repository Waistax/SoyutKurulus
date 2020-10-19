/**
 * başaşağıderebeyi.soyutkuruluş.dünya.Köşe.java
 * 0.6 / 19 Eki 2020 / 14:36:51
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.dünya;

import başaşağıderebeyi.matematik.*;

import java.util.*;

public class Köşe {
	public final Vektör2 konum;
	public final List<Bölge> bölgeler;
	public final Map<Kenar, Boolean> kenarlar;

	public Köşe(final Vektör2 konum) {
		this.konum = konum;
		bölgeler = new ArrayList<>(3);
		kenarlar = new HashMap<>(3);
	}
}
