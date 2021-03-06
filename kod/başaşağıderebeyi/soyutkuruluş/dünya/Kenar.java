/**
 * başaşağıderebeyi.soyutkuruluş.dünya.Kenar.java
 * 0.2 / 18 Eki 2020 / 09:36:36
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.dünya;

import java.util.*;

public class Kenar {
	public final Bölge bölge;
	public final Köşe başlangıç;
	public final Köşe bitiş;
	public final List<Kenar> kenarlar;

	public Kenar(final Bölge bölge, final Köşe başlangıç, final Köşe bitiş) {
		this.bölge = bölge;
		this.başlangıç = başlangıç;
		this.bitiş = bitiş;
		kenarlar = new ArrayList<>(6);
	}
}
