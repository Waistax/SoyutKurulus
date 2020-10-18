/**
 * başaşağıderebeyi.soyutkuruluş.dünya.Kenar.java
 * 0.2 / 18 Eki 2020 / 09:36:36
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.dünya;

import başaşağıderebeyi.matematik.*;

public class Kenar {
	public final Bölge altıgen;
	public final Vektör2 başlangıç;
	public final Vektör2 bitiş;

	public Kenar(final Bölge altıgen, final Vektör2 başlangıç, final Vektör2 bitiş) {
		this.altıgen = altıgen;
		this.başlangıç = başlangıç;
		this.bitiş = bitiş;
	}
}
