/**
 * başaşağıderebeyi.soyutkuruluş.ulus.Yol.java
 * 0.4 / 19 Eki 2020 / 11:48:26
 * Cem GEÇGEL (BaşAşağıDerebeyi)
 */
package başaşağıderebeyi.soyutkuruluş.ulus;

import başaşağıderebeyi.soyutkuruluş.dünya.*;

public class Yol {
	public final Ulus ulus;
	public final Kenar kenar;
	
	public Yol(final Ulus ulus, final Kenar kenar) {
		this.ulus = ulus;
		this.kenar = kenar;
		ulus.yollar.add(this);
	}
	
	public void topla() {
		ulus.ekle(null, 0.01F);
	}
}
